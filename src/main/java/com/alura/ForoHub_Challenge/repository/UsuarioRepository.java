package com.alura.ForoHub_Challenge.repository;

import com.alura.ForoHub_Challenge.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    //el metodo findByNombre retorna UserDetails, diferente al que se declaro: JpaRepository<Usuario, y
    //no provoca error puesto que la clase Usuario esta implementando de UserDetails
    UserDetails findByNombre(String username);
}
