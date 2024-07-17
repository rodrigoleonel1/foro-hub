package com.alura.ForoHub_Challenge.dto;

//Clase record para recibir el json del cliente con los datos para autenticar
//El cliente teclea su password en texto plano, pero en la BD, el password estara almacenado con
//un formato encriptado o hasheado, usaremos el formato de BCrypt. Para saber cual es el hash en BCrypt
//de cualquier texto como contraseña, entramos a https://www.browserling.com/tools/bcrypt para obtenerlo.
public record DatosAutenticarUsuario(
        String nombre,
        String password
) {
}
