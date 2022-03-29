package com.springBajo8.springBajo8.service.impl;

//import com.yoandypv.reactivestack.messages.domain.Message;
//import com.yoandypv.reactivestack.messages.repository.MessageRepository;
//import com.yoandypv.reactivestack.messages.service.MessageService;
import com.springBajo8.springBajo8.domain.DiagnosticoDTO;
import com.springBajo8.springBajo8.domain.citasDTOReactiva;
import com.springBajo8.springBajo8.repository.IcitasReactivaRepository;
import com.springBajo8.springBajo8.service.IcitasReactivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
    public Mono<citasDTOReactiva> updateCancelarCita(String id) {
        return this.IcitasReactivaRepository.findById(id)
                .flatMap(cita -> {
                    cita.setEstadoReservaCita("cancelado");
                    return save(cita);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<citasDTOReactiva> findByFechaYHora(String fecha, String hora) {
        return this.IcitasReactivaRepository.findByFechaReservaCita(fecha)
                .filter(cita -> Objects.equals(cita.getHoraReservaCita(), hora));
    }

    @Override
    public Mono<citasDTOReactiva> findByNombreMedico(String nombre, String apellidos) {
        return Objects.nonNull(nombre) ?
                this.IcitasReactivaRepository.findByNombreMedico(nombre)
                        .filter(cita -> Objects.equals(cita.getApellidosMedico(), apellidos))
                        .next()
                        .switchIfEmpty(Mono.empty()) :
                this.IcitasReactivaRepository.findAll()
                        .filter(cita -> Objects.equals(cita.getApellidosMedico(), apellidos))
                        .next()
                        .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<DiagnosticoDTO> findPadecimientosByIdPaciente(String idPaciente) {
        return this.IcitasReactivaRepository.findByIdPaciente(idPaciente)
                .flatMap(cita -> {
                    DiagnosticoDTO diagnosticoDTO = new DiagnosticoDTO();
                    diagnosticoDTO.setIdCita(cita.getId());
                    diagnosticoDTO.setPadecimientos(cita.getDiagnostico().getPadecimientos());
                    diagnosticoDTO.setTratamientos(cita.getDiagnostico().getTratamientos());
                    return Mono.just(diagnosticoDTO);
                })
                .switchIfEmpty(Mono.empty());
    }
}
