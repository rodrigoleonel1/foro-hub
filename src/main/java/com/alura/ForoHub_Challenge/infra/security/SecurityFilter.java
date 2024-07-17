package com.alura.ForoHub_Challenge.infra.security;


import com.alura.ForoHub_Challenge.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//Clase que crea un filtro para interceptar los request antes de que lleguen al controller
//e impedir el acceso a los recursos de la app sin antes hacer la validacion del usuario
//Component es el estereotipo mas generico de Spring para definir un componente e incluirlo en
//su contexto. Extendemos de clase abstracta de Spring, OncePerRequestFilter
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    //Implementamos el metodo de la clase abstracta
    //var authHeader obtiene el token que llega del cliente en el header usando request.getHeader
    //Por estandar el token llega en un header llamado Authorization
    //if el token es diferente de nulo, este llegara por estandar con un prefijo "Bearer " que
    //quitamos para obtener unicamente el Bearer Token
    //En la variable nombreUsuario almacenamos el username del usuario logeado
    //if nombreUsuario no es nulo, es porque el token es valido
    //obtenemos el usuario de la BD.
    //var authentication almacena un objeto usuario convertido en objeto authentication
    //Invocamos SecurityContextHolder que es una clase de Spring
    //Finalmente, filterChain (cadena de filtros) llama al siguiente filtro. doFilter aplica el
    //filtro y despues envia el request y response que estan llegando.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null){
            authHeader = authHeader.replace("Bearer ", "");
            System.out.println(authHeader);
            System.out.println(tokenService.getSubject(authHeader));
            var nombreUsuario = tokenService.getSubject(authHeader);
            if (nombreUsuario != null){
                var usuario = usuarioRepository.findByNombre(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); //forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication); //seteamos manualmente la autenticacion
            }
        }
        filterChain.doFilter(request,response);
    }
}




