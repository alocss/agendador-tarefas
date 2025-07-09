package com.alexribeiro.agendadortarefas.business;

import com.alexribeiro.agendadortarefas.business.dto.TarefasDTO;
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


    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);


        return tarefaConverter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){

        return tarefaConverter.paraListaTarefasDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));

    }


    public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity>listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefasDTO(listaTarefas);
    }
    public void  deletaTarefaPorId(String id){
        try{
        tarefasRepository.deleteById(id);
    } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente " + id,
                    e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + id));

            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(entity));

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }

    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
             return tarefaConverter.paraTarefasDTO(tarefasRepository.save(entity));

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }

}
