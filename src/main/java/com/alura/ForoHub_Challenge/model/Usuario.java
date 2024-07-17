package com.alura.ForoHub_Challenge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//Clase para mapear en la entidad Usuario
//mappedBy, se mapea a la clase Topico por medio del atributo autor
//JoinColumn da el nombre al campo que se creara en la tabla Usuario de la BD que relacionara con
//el topico

//Posteriormente implementamos de la interface UserDetails e implementamos sus metodos. Esto para
//que a Spring se le indique que en esta entidad se encuentra el campo con el username y el password
@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String password;
    //@OneToMany(fetch = FetchType.LAZY)//, mappedBy = "autor")
    //@JoinColumn(name = "id_topico")
    //private List<Topico> topicos;


    //Constructor
    public Usuario(String nombre, String email, String password){
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }


    //Metodos implementados de la interface

    //getAuthorities es para especificar el rol que va a tener el usuario dentro de la app
    //Si no tiene rol asignado, Spring le bloquea el acceso
    //Se retorna una lista SimpleGrantedAuthority de ROLE_USER con lo que se indica que el
    //rol de usuario sera por defecto los usuarios que esten en la lista
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    //se indica cual campo es la contraseña
    @Override
    public String getPassword() {
        return password;
    }

    //Se indica cual campo es el username
    @Override
    public String getUsername() {
        return nombre;
    }

    //Cuenta no expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Cuenta no bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Credencial no expirada
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //usuario habilitado
    @Override
    public boolean isEnabled() {
        return true;
    }
}
