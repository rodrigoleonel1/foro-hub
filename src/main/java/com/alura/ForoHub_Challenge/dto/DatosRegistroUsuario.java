package com.alura.ForoHub_Challenge.dto;

import jakarta.validation.constraints.NotBlank;

//Clase record para recibir el json del cliente para registrar un usuario nuevo
public record DatosRegistroUsuario(
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
