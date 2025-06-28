package com.alexribeiro.agendadortarefas.business.mapper;


import com.alexribeiro.agendadortarefas.business.dto.TarefasDTO;
import com.alexribeiro.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);
}
