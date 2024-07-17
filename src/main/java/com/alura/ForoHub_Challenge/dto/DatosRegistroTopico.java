package com.alura.ForoHub_Challenge.dto;

import com.alura.ForoHub_Challenge.model.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Clase record para tratar los datos que vienen en json del cliente
//Para validaciones, agregamos al pom la dependencia "validation"
//NotBlank y NotNull no es lo mismo, pero internamente NotBlank tambien hace lo mismo que NotNull
//NotBlank sirve para String, NotNull para cualquier otro objeto
public record DatosRegistroTopico(
        @NotNull
        Long idUsuario,
        @NotNull
        Curso nombreCurso,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje
) {
}
