package com.alura.ForoHub_Challenge.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//Las clases anotadas como Configuration son cargadas primero al correr la app
//EnableWebSecurity habilita el modulo WebSecurity con el que indico que el metodo que se crea esta siendo
//implementado para sobreescribir el comportamiento de autenticacion que se quiere
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;


    //Metodo para implementar una autenticacion Stateless (tipo de autenticacion donde el servidor no sabe
    //quien se encuentra logeado) y se retorna un objeto SecurityFilterChain y recibe un objeto del tipo HttpSecurity.
    //csrf es para evitar suplantacion de identidad pero como no se usa autenticacion Statefull no se
    //necesita y se deshabilita. En sessionCreationPolicy se le indica que la politica de creacion de la
    //sesion sea stateless.
    //y cada request que haga match con los request HTTP de tipo POST que empiecen con el patron "/login" se les
    //permita todo. Esto es para que el endpoint de /login este abierto a todos los usuarios para obtener el token
    //Cualquier otro request se tiene que autenticar
    //addFilterBefore, carga una instancia del filtro personalizado "securityFilter" antes del filtro de Spring, junto
    //con el tipo de autenticacion UsernamePasswordAuthenticationFilter que valida que el usuario que esta
    //iniciando sesion existe y que ya esta autenticado.
    //Finalmente con esto el cliente tiene nuevamente acceso a los recursos de la app
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }*/
    //Metodo generado y mejorado con ChatGPT ya que el anterior aparece como Deprecated
    //Se ha utilizado AbstractHttpConfigurer::disable para desactivar la protección CSRF de una manera más limpia y moderna
    //La configuración de la política de creación de sesiones sigue siendo la misma, utilizando sessionManagement para establecer SessionCreationPolicy.STATELESS.
    //Se ha actualizado la configuración de autorización utilizando authorizeHttpRequests() en lugar de authorizeRequests().
    //La nueva API con authorizeHttpRequests se utiliza para configurar las reglas de autorización.
    //.requestMatchers autoriza los HTTP Request de tipo POST que hacen match con "/usuarios" para el endpoint de registro de nuevo usuario
    //El filtro personalizado SecurityFilter se agrega antes del filtro UsernamePasswordAuthenticationFilter usando addFilterBefore.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    //Creamos el objeto AuthenticationManager que Spring necesita en el AutenticacionController
    //Anotamos con Bean para que Spring lo tenga en su contexto
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //BCryptPasswordEncoder es una clase proporcionada por Spring Security que implementa el
    //algoritmo de hashing bcrypt.
    //Al definir este Bean se puede inyectar BCryptPasswordEncoder en cualquier componente de
    //la app que necesite manejar contraseñas
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}