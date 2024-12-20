package com.challenge.LiterAlura.repositorio;

import com.challenge.LiterAlura.models.Escritores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEscritoresRepository extends JpaRepository<Escritores, Long> {
    Escritores findByNameIgnoreCase(String nombre);

    List<Escritores> findByAnoNatalicioLessThanEqualAndAnoDefuncionGreaterThanEqual(int anoInicial, int anoFinal);
}
