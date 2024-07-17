package com.alura.ForoHub_Challenge.dto;

import com.alura.ForoHub_Challenge.model.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long idUsuario,
        String titulo,
        String mensaje
) {
}
