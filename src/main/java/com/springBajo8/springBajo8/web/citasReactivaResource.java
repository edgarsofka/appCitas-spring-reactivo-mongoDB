package com.springBajo8.springBajo8.web;


import com.springBajo8.springBajo8.domain.citasDTOReactiva;
import com.springBajo8.springBajo8.service.IcitasReactivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class citasReactivaResource {

    @Autowired
    private IcitasReactivaService icitasReactivaService;

    @PostMapping("/citasReactivas")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<citasDTOReactiva> save(@RequestBody citasDTOReactiva citasDTOReactiva) {
        return this.icitasReactivaService.save(citasDTOReactiva);
    }

    @DeleteMapping("/citasReactivas/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> delete(@PathVariable("id") String id) {
        return this.icitasReactivaService.delete(id)
                .flatMap(citasDTOReactiva -> Mono.just(ResponseEntity.ok(citasDTOReactiva)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @PutMapping("/citasReactivas/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> update(@PathVariable("id") String id, @RequestBody citasDTOReactiva citasDTOReactiva) {
        return this.icitasReactivaService.update(id, citasDTOReactiva)
                .flatMap(citasDTOReactiva1 -> Mono.just(ResponseEntity.ok(citasDTOReactiva1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping("/citasReactivas/{idPaciente}/byidPaciente")
    private Flux<citasDTOReactiva> findAllByidPaciente(@PathVariable("idPaciente") String idPaciente) {
        return this.icitasReactivaService.findByIdPaciente(idPaciente);
    }


    @GetMapping("/citasReactivas/{id}/byMedico")
    private Mono<citasDTOReactiva> findMedico(@PathVariable("id") String id){
        return  this.icitasReactivaService.findMedico(id);
    }

    @GetMapping("/citasReactivas/{idPaciente}/byPadecimiento")
    private Flux<citasDTOReactiva> findPadecimiento(@PathVariable("idPaciente") String idPaciente){
        return  this.icitasReactivaService.findPadecimiento(idPaciente);
    }

    @GetMapping(value = "/citasReactivas")
    private Flux<citasDTOReactiva> findAll() {
        return this.icitasReactivaService.findAll();
    }

    @GetMapping("/citasReactivas/{fecha}/byfecha")
    private Flux<citasDTOReactiva> findByFecha(@PathVariable(value = "fecha") String fecha){
        return this.icitasReactivaService.findByFecha(fecha);
    }

    @GetMapping("/citasReactivas/{hora}/byhora")
    private Flux<citasDTOReactiva> findByhora(@PathVariable(value = "hora") String hora){
        return this.icitasReactivaService.findByHora(hora);
    }

    @PutMapping("/citasReactivas/cancelar/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> cancelar(@PathVariable("id") String id) {
        return this.icitasReactivaService.findById(id).flatMap(citasDTOReactiva -> {
            citasDTOReactiva.setEstado(false);
            citasDTOReactiva.setEstadoReservaCita("Cancelada");
            return this.icitasReactivaService.save(citasDTOReactiva);
        }).flatMap(citasDTOReactiva -> Mono.just(ResponseEntity.ok(citasDTOReactiva))).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
