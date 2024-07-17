package com.alura.ForoHub_Challenge.dto;

import com.alura.ForoHub_Challenge.model.Curso;
import com.alura.ForoHub_Challenge.model.Topico;
import com.alura.ForoHub_Challenge.model.Usuario;

import java.time.LocalDateTime;

//Clase record para listar al cliente solo los campos requeridos
public record DatosListadoTopico(
        Long id,
        Curso nombreCurso,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor,
        String estado
) {

    //Constructor que recibe objeto de tipo Topico para mapear esta clase record
    //La propiedad "estado" viene como booleano y en la clase record esta como String, por lo que
    //aplicamos logica para devolver un String con la cadena Abierto o Cerrado
    public DatosListadoTopico(Topico topico){
        this(
                topico.getId(),
                topico.getNombreCurso(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getAutor().getNombre(),
                topico.getEstado() ? "Abierto" : "Cerrado"
        );
    }

}