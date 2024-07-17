package com.alura.ForoHub_Challenge.dto;

import com.alura.ForoHub_Challenge.model.Curso;
import com.alura.ForoHub_Challenge.model.Topico;

import java.time.LocalDateTime;

//Clase record para mostrar un topico al cliente solo con los campos requeridos
public record DatosDetalleTopico(
        Long id,
        Curso nombreCurso,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String estado
) {

    //Constructor
    public DatosDetalleTopico(Topico topico){
        this(
                topico.getId(),
                topico.getNombreCurso(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado() ? "Abierto" : "Cerrado"
        );
    }

}
