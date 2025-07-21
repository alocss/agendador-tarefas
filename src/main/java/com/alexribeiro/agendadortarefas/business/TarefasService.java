package com.alexribeiro.agendadortarefas.business;

import com.alexribeiro.agendadortarefas.business.dto.TarefasDTORecord;
import com.alexribeiro.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.alexribeiro.agendadortarefas.business.mapper.TarefasConverter;
import com.alexribeiro.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.alexribeiro.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.alexribeiro.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.alexribeiro.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.alexribeiro.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;


    public TarefasDTORecord gravarTarefa(String token, TarefasDTORecord dto){
        String email = jwUtil.extrairEmailToken(token.substring(7));

        TarefasDTORecord dtoFinal = new TarefasDTORecord(
                null,
                dto.nomeTarefa(),
                dto.descricao(),
                LocalDateTime.now(),
                dto.dataEvento(),
                email,
                null,
                StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dtoFinal);


        return tarefaConverter.paraTarefasDTORecord(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTORecord> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){

        return tarefaConverter.paraListaTarefasDTORecord(
                tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE));

    }


    public List<TarefasDTORecord> buscaTarefasPorEmail(String token){
        String email = jwUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity>listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefasDTORecord(listaTarefas);
    }
    public void  deletaTarefaPorId(String id){
        try{
        tarefasRepository.deleteById(id);
    } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente " + id,
                    e.getCause());
        }
    }

    public TarefasDTORecord alteraStatus(StatusNotificacaoEnum status, String id) {
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + id));

            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefasDTORecord(tarefasRepository.save(entity));

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }

    }

    public TarefasDTORecord updateTarefas(TarefasDTORecord dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
             return tarefaConverter.paraTarefasDTORecord(tarefasRepository.save(entity));

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }

}
