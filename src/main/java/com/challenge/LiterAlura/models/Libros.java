package com.challenge.LiterAlura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Escritores escritor;

    @Column(name = "nombre_autor")
    private String nombreAutor;

    @Column(name = "lenguaje")
    private String lenguaje;
    private double cantidadDescargas;

    public Libros(){}

    public Libros(DatosLibro datosLibro, Escritores escritor){
        this.titulo = datosLibro.titulo();
        setLenguaje(datosLibro.languages());
        this.cantidadDescargas = datosLibro.cantidadDescargas();
        this.nombreAutor = escritor.getName();
        this.escritor = escritor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Escritores getEscritor() {
        return escritor;
    }

    public void setEscritor(Escritores escritor) {
        this.escritor = escritor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(List<String> lenguaje) {
        this.lenguaje = String.join(",", lenguaje);
    }

    public double getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(double cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    @Override
    public String toString() {
        return "* * *** L I B R O *** * *" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + nombreAutor + "\n" +
                "Idioma: " + lenguaje + "\n" +
                "Número de descargas: " + cantidadDescargas + "\n" +
                "*******************************************" + "\n";
    }
}
