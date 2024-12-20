package com.challenge.LiterAlura.repositorio;

import com.challenge.LiterAlura.models.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILibrosRepository extends JpaRepository<Libros, Long> {
    Libros findByTitulo(String titulo);

    List<Libros> findByLenguajeContaining(String lenguaje);
}