package com.challenge.LiterAlura.principal;

import com.challenge.LiterAlura.models.*;
import com.challenge.LiterAlura.repositorio.IEscritoresRepository;
import com.challenge.LiterAlura.repositorio.ILibrosRepository;
import com.challenge.LiterAlura.service.ConsultaAPIGutendex;
import com.challenge.LiterAlura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsultaAPIGutendex consultaApiGutendex = new ConsultaAPIGutendex();
    private final static String URL_BASE ="https://gutendex.com/books/?search=";
    private ConvierteDatos conversion = new ConvierteDatos();

    private ILibrosRepository librosRepository;
    private IEscritoresRepository escritoresRepository;

    public Principal(IEscritoresRepository autoresRepository, ILibrosRepository librosRepository) {
        this.escritoresRepository = autoresRepository;
        this.librosRepository = librosRepository;
    }

    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Selecciona una de las siguientes opciones:
                    
                    1. Buscar libro X título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en año dado
                    5. Listar libros x idioma
                    
                    0. Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    escritoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosXIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo! Bye! bye!");
                    break;
                default:
                    System.out.println("Opción no valida");
            }
        }
    }
    private Datos getDatosLibro() {
            var tituloLibro = teclado.nextLine();
            var json = consultaApiGutendex.buscarLibros(URL_BASE + tituloLibro.replace(" ", "+"));
            Datos datosLibro = conversion.buscarLibros(json, Datos.class);
            return datosLibro;
        }

    private Libros crearLibro(DatosLibro datosLibros, Escritores autor) {
        if (autor != null) {
            return new Libros(datosLibros, autor);
        } else {
            System.out.println("El autor es null, no se puede crear el libro");
            return null;
        }
    }

    private void buscarLibros() {
        System.out.println("Título de libro a buscar: ");
        Datos datos = getDatosLibro();
        if (!datos.resultados().isEmpty()) {
            DatosLibro datosLibro = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Libros libro = null;
            Libros libroRepositorio = librosRepository.findByTitulo(datosLibro.titulo());
            if (libroRepositorio != null) {
                System.out.println("El libro ya se encuenta en la BDD");
                System.out.println(libroRepositorio.toString());
            }else{
                Escritores escritorRepositorio = escritoresRepository.findByNameIgnoreCase(datosLibro.autor().get(0).nombreAutor());
                if (escritorRepositorio != null) {
                    libro = crearLibro(datosLibro, escritorRepositorio);
                    librosRepository.save(libro);
                    System.out.println("* * * LIBRO ADICIONADO * * *\n");
                    System.out.println(libro);
                } else {
                    Escritores autor = new Escritores(datosAutor);
                    autor = escritoresRepository.save(autor);
                    libro = crearLibro(datosLibro, autor);
                    librosRepository.save(libro);
                    System.out.println("* * * LIBRO ADICIONADO * * *\n");
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("Libro inexistente en Gutendex, intenta otro");
        }
    }
    private void librosRegistrados() {
        List<Libros> libros = librosRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("Aun NO existen libros registrados");
            return;
        }
        System.out.println("* * * LIBROS REGISTRADOS: * * *\n");
        libros.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);
    }

    private void escritoresRegistrados() {
        List<Escritores> escritores = escritoresRepository.findAll();
        if (escritores.isEmpty()) {
            System.out.println("No hay escritores registrados");
            return;
        }
        System.out.println("* * * AUTORES REGISTRADOS: * * *\n");
        escritores.stream()
                .sorted(Comparator.comparing(Escritores::getName))
                .forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.println("Año que deseas buscar: ");
        var ano = teclado.nextInt();
        teclado.nextLine();
        List<Escritores> escritoresPorAno = escritoresRepository.findByAnoNatalicioLessThanEqualAndAnoDefuncionGreaterThanEqual(ano, ano);
        if (escritoresPorAno.isEmpty()) {
            System.out.println("No existen aun escritores registrados en ese año");
            return;
        }
        System.out.println("* * * AUTORES VIVOS REGISTRADOS EN AÑO " + ano + " SON: * * *\n");
        escritoresPorAno.stream()
                .sorted(Comparator.comparing(Escritores::getName))
                .forEach(System.out::println);
    }

    private void listarLibrosXIdioma() {
        System.out.println("Idioma a buscar: ");
        String menu = """
                es - Español
                pt - Portugués
                fr - Francés
                en - Inglés
                """;
        System.out.println(menu);
        var idioma = teclado.nextLine();
        if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
            System.out.println("Idioma inválido, intenta de nuevo");
            return;
        }
        List<Libros> librosPorIdioma = librosRepository.findByLenguajeContaining(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No existen libros registrados en ese idioma");
            return;
        }
        System.out.println("* * * LIBROS REGISTRADOS EN IDIOMA SELECCIONADO: * * *\n");
        librosPorIdioma.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);
    }
}
