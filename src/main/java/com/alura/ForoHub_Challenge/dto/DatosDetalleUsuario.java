package com.alura.ForoHub_Challenge.dto;

import com.alura.ForoHub_Challenge.model.Curso;
import com.alura.ForoHub_Challenge.model.Topico;

import java.time.LocalDateTime;

//Clase record para mostrar un usuario al cliente solo con los campos requeridos
public record DatosDetalleUsuario(
        String nombre,
        String password
) {


}