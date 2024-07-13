package literAllura.vargas.api.model.principal;

import literAllura.vargas.api.model.Autor;
import literAllura.vargas.api.model.DatosAutor;
import literAllura.vargas.api.model.DatosLibros;
import literAllura.vargas.api.model.Libro;
import literAllura.vargas.api.repository.IAutorRepository;
import literAllura.vargas.api.repository.ILibroRepository;
import literAllura.vargas.api.service.ConsumoAPI;
import literAllura.vargas.api.service.ConvierteDatos;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private final Scanner teclado = new Scanner(System.in);
    private final ILibroRepository libroRepository;
    private final IAutorRepository autorRepository;

    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println(getMenu());
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine();
                manejarOpcion(opcion);
            } else {
                System.out.println("Opción no válida");
                teclado.next();
            }
        }
        teclado.close();
    }

    private String getMenu() {
        return """
                ---------------------------------------------
                1. Agregar libro
                2. Lista de libros registrados
                3. Lista de autores registrados
                4. Lista de autores vivos en un determinado año
                5. Lista libros por idioma
                0- Salir
                ---------------------------------------------
                Selecciona una opcion para continuar
                """;
    }

    private void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> buscarLibro();
            case 2 -> listaLibrosRegistrados();
            case 3 -> listaAutorsRegistrados();
            case 4 -> listaAutoresVivos();
            case 5 -> listaLibrosPorIdioma();
            case 0 -> System.out.println("Cerrando la aplicación");
            default -> System.out.println("Opción no válida");
        }
    }

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea agregar:");
        String tituloLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.replace(" ", "+"));
        guardarDatos(json);
    }

    private void guardarDatos(String json) {
        try {
            DatosAutor datosAutor = convierteDatos.obtenerDatos(json, DatosAutor.class);
            DatosLibros datosLibro = convierteDatos.obtenerDatos(json, DatosLibros.class);

            Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                    .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

            if (libroRepository.findByTitulo(datosLibro.titulo()).isEmpty()) {
                Libro libro = new Libro(datosLibro);
                libro.setAutor(autor);
                libroRepository.save(libro);
                System.out.println(libro);
                System.out.println("Libro agregado con éxito");
            } else {
                System.out.println("El libro ya se encuentra registrado");
            }
        } catch (Exception e) {
            System.out.println("Libro no encontrado: " + e.getMessage());
        }
    }

    private void listaLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listaAutorsRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listaAutoresVivos() {
        System.out.println("Indica el año límite: ");
        int fecha = teclado.nextInt();
        List<Autor> autores = autorRepository.autoresPorFechaDeMuerte(LocalDate.ofEpochDay(fecha));
        autores.forEach(System.out::println);
    }

    public void listaLibrosPorIdioma() {
        List<String> idiomas = libroRepository.idiomasLibros();
        System.out.println("------------------IDIOMAS--------------------");
        idiomas.forEach(System.out::println);
        System.out.println("---------------------------------------------");
        System.out.println("Ingresa el idioma por el que deseas buscar: ");
        String idiomaSeleccionado = teclado.nextLine().toLowerCase();
        List<Libro> libros = libroRepository.librosPorIdioma(idiomaSeleccionado);

        if (libros.isEmpty()) {
            System.out.println("Opción no válida");
        } else {
            libros.forEach(System.out::println);
        }
    }
}



