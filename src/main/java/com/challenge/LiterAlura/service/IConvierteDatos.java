package com.challenge.LiterAlura.service;

public interface IConvierteDatos {
    <T> T buscarLibros(String json, Class<T> clase);
}
