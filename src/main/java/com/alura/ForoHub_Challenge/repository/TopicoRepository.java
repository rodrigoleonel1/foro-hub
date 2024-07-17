package com.alura.ForoHub_Challenge.repository;

import com.alura.ForoHub_Challenge.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository para hacer todo el proceso de gestion con la BD a nivel del CRUD
//JPARepository necesita dos parametros genericos, el primero es el tipo de objeto que se va a
//guardar (Entidad Medico). El segundo es el tipo de objeto del id (Long)
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
