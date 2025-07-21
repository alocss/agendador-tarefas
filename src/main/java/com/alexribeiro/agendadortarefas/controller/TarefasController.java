package com.alexribeiro.agendadortarefas.controller;


import com.alexribeiro.agendadortarefas.business.TarefasService;
import com.alexribeiro.agendadortarefas.business.dto.TarefasDTORecord;
import com.alexribeiro.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor

public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTORecord> gravarTarefas(@RequestBody TarefasDTORecord dto,
                                                          @RequestHeader("Authorization")String token){
        return  ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }


    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTORecord>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal){

        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity <List<TarefasDTORecord>> buscaTarefasPorEmail (@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id){

        tarefasService.deletaTarefaPorId(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasDTORecord> alteraStatusNotificacao(@RequestParam("status")StatusNotificacaoEnum  status, @RequestParam("id")String id){
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefasDTORecord> atualizarTarefa(@PathVariable String id,
                                                            @RequestBody TarefasDTORecord dtoFinal) {
        return ResponseEntity.ok(tarefasService.updateTarefas(dtoFinal, id));
    }


}
