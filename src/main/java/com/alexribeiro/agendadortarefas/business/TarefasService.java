package com.alexribeiro.agendadortarefas.business;

import com.alexribeiro.agendadortarefas.business.dto.TarefasDTO;
import com.alexribeiro.agendadortarefas.business.mapper.TarefasConverter;
import com.alexribeiro.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.alexribeiro.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.alexribeiro.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.alexribeiro.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwUtil;


    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);


        return tarefaConverter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }
}
