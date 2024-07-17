package com.alura.ForoHub_Challenge.controller;

import com.alura.ForoHub_Challenge.dto.DatosAutenticarUsuario;
import com.alura.ForoHub_Challenge.infra.security.DatosJWTToken;
import com.alura.ForoHub_Challenge.infra.security.TokenService;
import com.alura.ForoHub_Challenge.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//AuthenticationManager se necesita inyectar pero Spring no lo tiene en su contexto, por lo
//que se crea en la clase SecurityConfigurations
@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    //Metodo para login. Recibe como parametro un body json del cliente con username y contraseña
    //Como authToken es una interface, se tiene que usar una implementacion de la interface que es
    //UsernamePasswordAuthenticationToken y se le envia el username y contraseña del usuario
    //AuthenticationManager es la clase que dispara el proceso de autenticacion en Spring, la cual
    //se inyecta para poder usar el metodo authenticate que recibe un objeto del tipo
    //Authentication que es un token que se tiene aqui en la clase y contiene el nombre de usuario y
    //su contraseña, a partir de esto obtenemos el usuario autenticado en una variable
    //Se declara variable JWTtoken para hacer uso de TokenService y el metodo generarToken, le enviamos
    //usuarioAutenticado.getPrincipal que es el objeto usuario que ya fue autenticado en el sistema y
    //lo casteamos a Usuario
    //Finalmente se retorna el token al cliente en formato JWT, el cual se puede validar en jwt.io.
    //Pero como en la app se reciben DTO's y se retornan DTO's, como buena practica se retorna el token
    //en un DTO DatosJWTToken
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.nombre(),
                datosAutenticarUsuario.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
