package com.challenge.LiterAlura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "escritores")
public class Escritores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int anoNatalicio;
    private int anoDefuncion;

    @OneToMany(mappedBy = "escritor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros = new ArrayList<>();

    public Escritores(DatosAutor datosAutor) {
        this.name = datosAutor.nombreAutor();
        this.anoNatalicio = datosAutor.anoNatalicio();
        this.anoDefuncion = datosAutor.anoDefuncion();
    }

    public Escritores() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnoNatalicio() {
        return anoNatalicio;
    }

    public void setAnoNatalicio(int anoNatalicio) {
        this.anoNatalicio = anoNatalicio;
    }

    public int getAnoDefuncion() {
        return anoDefuncion;
    }

    public void setAnoDefuncion(int anoDefuncion) {
        this.anoDefuncion = anoDefuncion;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }
    @Override
    //Mostrar titulo de libros
    public String toString() {
        StringBuilder librosTitulos = new StringBuilder();
        for (Libros libro : libros) {
            librosTitulos.append(libro.getTitulo()).append(", ");
        }

        // Eliminar coma y espacio
        if (librosTitulos.length() > 0) {
            librosTitulos.setLength(librosTitulos.length() - 2);
        }
        return "*** E S C R I T O R ***" + "\n" +
                "Autor: " + name + "\n" +
                "Natalicio: " + anoNatalicio + "\n" +
                "Defunción: " + anoDefuncion + "\n" +
                "Libros: " + librosTitulos + "\n";
    }
}
