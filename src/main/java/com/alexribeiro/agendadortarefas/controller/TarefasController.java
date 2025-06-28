package com.alexribeiro.agendadortarefas.controller;


import com.alexribeiro.agendadortarefas.business.TarefasService;
import com.alexribeiro.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor

public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestHeader("Authorization")String token){
        return  ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }


}
