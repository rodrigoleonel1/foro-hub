package com.alura.ForoHub_Challenge.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Clase para tratar de manera globla los errores y no en cada metodo en especifico
//RestControllerAdvice actua como una especie de proxy para todos los controllers,
//para interceptar las llamadas en caso que suceda un tipo de excepcion
@RestControllerAdvice
public class TratadorDeErrores {


    //Metodo para tratar error 404 que sucede cuando se busca un objeto que no existe en la BD
    //ExceptionHandler en esta anotacion se le dice que tipo de excepcion se quiere tratar, seguido de .class
    //Como se sabe que ResponseEntity es la entidad que tiene mapeados los tipos de error dentro y hace un
    //wrapper de las respuestas y las retorna al cliente, en el controller, entonces se crea un
    //metodo que devuelve ResponseEntity, no se pone dato en el generic <> porque ahora si es generico y no
    //se necesita especificar un objeto a tratar. Retorna ResponseEntity.notFound que significa error 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }


    //Metodo para retornar un error mas entendible al cliente cuando se quiere crear un medico y hay
    //campos vacios que deben ser obligatorios (error 400)
    //Al metodo enviamos un parametro del mismo tipo de la excepcion que se esta tratando para obtener
    //los mensajes de errores de los campos faltantes y mostrarlos al cliente
    //Se declara variable errores que es un List<FieldError> para almacenar los errores que se obtienen de la excepcion
    //Al obtener los errores de la excepcion, se vuelve a mostrar toda la informacion detallada al cliente,
    //entonces para solo mostrar lo que deseamos, se hace uso de una clase record DTO
    //Por lo que se aplica stream a la lista de errores, se mapea creando un nuevo objeto del record DatosErrorValidacion
    //y todos los errores obtenidos se crean en otra lista con toList
    //Retorna un badRequest con un body y dentro los errores obtenidos de la excepcion
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }



    //Clase record para mostrar los errores al cliente. Como esta clase solo se usara aqui, se crea aqui mismo
    //Se crean solo las variables del campo faltante y el error
    private record DatosErrorValidacion(String campo, String error){

        //constructor que recibe un objeto del tipo FieldError. Recibe informacion del map para mapear o crear los objetos
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}