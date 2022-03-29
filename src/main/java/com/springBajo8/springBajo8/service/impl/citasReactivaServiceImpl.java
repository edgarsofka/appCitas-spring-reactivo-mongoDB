package com.springBajo8.springBajo8.service.impl;

//import com.yoandypv.reactivestack.messages.domain.Message;
//import com.yoandypv.reactivestack.messages.repository.MessageRepository;
//import com.yoandypv.reactivestack.messages.service.MessageService;
import com.springBajo8.springBajo8.domain.citasDTOReactiva;
import com.springBajo8.springBajo8.repository.IcitasReactivaRepository;
import com.springBajo8.springBajo8.service.IcitasReactivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class citasReactivaServiceImpl implements IcitasReactivaService {

    @Autowired
    private IcitasReactivaRepository IcitasReactivaRepository;

    @Override
    public Mono<citasDTOReactiva> save(citasDTOReactiva citasDTOReactiva) {
        return this.IcitasReactivaRepository.save(citasDTOReactiva);
    }

    

    @Override
    public Mono<citasDTOReactiva> delete(String id) {
        return this.IcitasReactivaRepository
                .findById(id)
                .flatMap(p -> this.IcitasReactivaRepository.deleteById(p.getId()).thenReturn(p));

    }

    @Override
    public Mono<citasDTOReactiva> findMedico(String id) {
        return this.IcitasReactivaRepository
                .findById(id)
                .flatMap(p -> {
                    citasDTOReactiva medico = new citasDTOReactiva();
                    medico.setNombreMedico(p.getNombreMedico());
                    medico.setApellidosMedico(p.getApellidosMedico());
                    return Mono.just(medico);
                });
    }

    @Override
    public Flux<citasDTOReactiva> findPadecimiento(String idPaciente) {
        return this.IcitasReactivaRepository
                .findByIdPaciente(idPaciente)
                .flatMap(p -> {
                    citasDTOReactiva paciente = new citasDTOReactiva();
                    paciente.setIdPaciente(p.getIdPaciente());
                    paciente.setPadecimiento(p.getPadecimiento());
                    paciente.setTratamiento(p.getTratamiento());
                    return Flux.just(paciente);
                });
    }

    @Override
    public Mono<citasDTOReactiva> update(String id, citasDTOReactiva citasDTOReactiva) {
        return this.IcitasReactivaRepository.findById(id)
                .flatMap(citasDTOReactiva1 -> {
                    citasDTOReactiva.setId(id);
                    return save(citasDTOReactiva);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<citasDTOReactiva> findByIdPaciente(String idPaciente) {
        return this.IcitasReactivaRepository.findByIdPaciente(idPaciente);
    }


    @Override
    public Flux<citasDTOReactiva> findAll() {
        return this.IcitasReactivaRepository.findAll();
    }

    @Override
    public Mono<citasDTOReactiva> findById(String id) {
        return this.IcitasReactivaRepository.findById(id);
    }

    @Override
    public Flux<citasDTOReactiva> findByFecha(String fecha) {
        return this.IcitasReactivaRepository.findByFechaReservaCita(fecha);
    }

    @Override
    public Flux<citasDTOReactiva> findByHora(String hora) {
        return this.IcitasReactivaRepository.findByHoraReservaCita(hora);
    }



}
