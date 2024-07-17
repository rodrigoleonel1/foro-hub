package com.alura.ForoHub_Challenge.controller;

import com.alura.ForoHub_Challenge.dto.DatosDetalleTopico;
import com.alura.ForoHub_Challenge.dto.DatosDetalleUsuario;
import com.alura.ForoHub_Challenge.dto.DatosRegistroUsuario;
import com.alura.ForoHub_Challenge.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    //Metodo para registrar un nuevo usuario
    //passwEncript, encripta el password
    //Creamos el nuevo usuario en la BD con el password encriptado
    //Retornamos el nuevo usuario al cliente
    @PostMapping
    public ResponseEntity<DatosDetalleUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                                              UriComponentsBuilder uriComponentsBuilder){
        var passwEncript = usuarioService.encriptarPassword(datosRegistroUsuario.password());
        var usuario = usuarioService.crerUsuario(datosRegistroUsuario.nombre(), datosRegistroUsuario.email(), passwEncript);
        DatosDetalleUsuario datosDetalleUsuario = new DatosDetalleUsuario(usuario.getNombre(), usuario.getEmail());
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosDetalleUsuario);
    }


}
