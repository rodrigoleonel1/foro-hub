package com.alura.ForoHub_Challenge.infra.security;

import com.alura.ForoHub_Challenge.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Clase intermedia service para ejecutar logica y trabaje con el UsuarioRepositoy
//Las configuraciones que realizamos no son a nivel de archivo application.properties, sino
//implementamos interfaces propias de Spring como UserDetailsService que sirve para efectuar login y
//de esta manera personalizar el comportamiento por defecto que Spring Security tiene
@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    //Implementamos el metodo de la interface al cual se le tiene que indicar de que forma se va a
    //cargar el usuario y de donde. Esto se indica en el retorno y usando un metodo del repositorio
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNombre(username);
    }
}
