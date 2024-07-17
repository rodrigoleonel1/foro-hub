package com.alura.ForoHub_Challenge.infra.security;


import com.alura.ForoHub_Challenge.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


//Clase Service para generar los token JWT (Json Web Token) que es un estandar para la
//definicion de tokens con un algoritmo de encriptacion
//Se usara la dependencia o biblioteca de JWT, llamada Auth0 la cual obtenemos en
//https://github.com/auth0/java-jwt
//Buscamos donde esta el codigo para agregar la dependencia via Maven y usamos una version
//anterior a la que se muestre en el codigo ya que normalmente las versiones actuales
//presentan algunos bug que son solucionados en las siguientes versiones, p.e.
//si en el codigo aparece la <version>4.3.0, sobreescribimos a 4.2.0
@Service
public class TokenService {

    //Value inicializa el String indicando que el valor esta en una variable de
    //entorno declarada en application.properties
    @Value("${api.security.secret}")
    private String apiSecret;


    //Create JWT. Metodo para generar token JWT para el usuario que recibe por parametro.
    //Tomamos el codigo del repositorio de github y los modificamos a nuestro contexto
    //Usamos un algoritmo HMAC256 que pide un String o arreglo de bytes, que viene a ser el
    //secret, que es la contraseña para validar la firma
    //Se retorna un String: JWT lo crea indicando quien lo emite (withIssuer) y va dirijido a (withSubject),
    //se muestra el id (withClaim). Y con withExpiresAt que recibe un instant mediante el
    //metodo generarFechaExpiracion, se pone un tiempo de validez
    //hacemos throw new RuntimeException(); para no dejar vacio el catch
    //Una vez que el JWT se mostro al cliente, se puede entrar a la pagina jwt.io para validarlo en
    //el apartado de Encoded, pegamos el token y lo muestra decodificado. Si aparece como Invalid Signature,
    //ponemos la contraseña del usuario en el apartado VERIFY SIGNATURE y sustituimos el texto:
    //your-256-bit-secret! y la firma aparecera como verificada
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("topicos_challeng")
                    .withSubject(usuario.getNombre())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }


    //Metodo para validar el JWT token, que llega del usuario para
    //saber si no esta expirado y fue asignado al usuario que inicio sesion
    //Se toma el codigo del repositorio y se modifica a como necesitemos
    //Enviamos el tipo de algoritmo que estamos usando con el apiSecret
    //se retorna el nombre del usuario que esta logeado
    public String getSubject(String token){
        if (token == null){
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma
            verifier = JWT.require(algorithm)
                    .withIssuer("topicos_challeng") //valida que el emisor sea topicos_challeng
                    .build() //hace build del objeto verifier
                    .verify(token); //verifica el token
            verifier.getSubject(); //verifica y obtiene el username del usuario logeado
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null){
            throw new RuntimeException("verifier inválido");
        }
        return verifier.getSubject(); //una vez que se valido un subject no nulo, se retorna
    }


    //Retorna un Instant. .now es a partir de ahora, .plusHours(2) se suman 2 horas que sera el
    //tiempo de expiracion, toInstant para agregar la zona horaria
    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }


}
