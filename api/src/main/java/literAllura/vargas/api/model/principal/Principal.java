package literAllura.vargas.api.model.principal;

import literAllura.vargas.api.model.*;
import literAllura.vargas.api.repository.IAutorRepository;
import literAllura.vargas.api.repository.ILibroRepository;
import literAllura.vargas.api.service.ConsumoAPI;
import literAllura.vargas.api.service.ConvierteDatos;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;
    private List<String> idiomas;

    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion = 1;
        while (opcion != 0) {
            var menu = """
                    ---------------------------------------------
                    1. Buscar libro
                    2. Lista de libros registrados
                    3. Lista de autores registrados
                    4. Lista de autores vivos en un determinado año
                    5. Lista libros por idioma
                    0- Salir
                    ---------------------------------------------
                    Selecciona una opcion para continuar
                    """;
            System.out.println(menu);
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine(); // consume newline

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        listaLibrosRegistrados();
                        break;
                    case 3:
                        listaAutoresRegistrados();
                        break;
                    case 4:
                        listaAutoresVivos();
                        break;
                    case 5:
                        listaLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicacion");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } else {
                System.out.println("Opción no válida");
                teclado.next(); // consume the invalid input
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro);
        guardarDatos(json);
    }

    private void guardarDatos(String json) {
        try {
            DatosBusqueda datosBusqueda = convierteDatos.obtenerDatos(json, DatosBusqueda.class);
            if (datosBusqueda.resultadoLibros() == null || datosBusqueda.resultadoLibros().isEmpty()) {
                System.out.println("Libro no encontrado");
                return;
            }

            for (DatosLibros datosLibro : datosBusqueda.resultadoLibros()) {
                DatosAutor datosAutor = convierteDatos.obtenerDatos(json, DatosAutor.class);

                // Verifica si el autor ya existe
                Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                        .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

                // Verifica si el libro ya existe
                if (libroRepository.findByTitulo(datosLibro.titulo()).isEmpty()) {
                    Libro libro = new Libro(datosLibro);
                    libro.setAutor(autor);
                    libroRepository.save(libro);
                    System.out.println(libro);
                    System.out.println("Libro agregado con exito");
                } else {
                    System.out.println("---------------------------------------------");
                    System.out.println("El libro ya se encuentra registrado");
                }
            }
        } catch (Exception e) {
            System.out.println("---------------------------------------------");
            System.out.println("Error procesando los datos: " + e.getMessage());
        }
    }

    private void listaLibrosRegistrados() {
        libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listaAutoresRegistrados() {
        autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listaAutoresVivos() {
        System.out.println("Indica el año límite: ");
        int year = teclado.nextInt();
        teclado.nextLine(); // consume newline
        LocalDate fecha = LocalDate.of(year, 12, 31); // usa el último día del año como límite
        autores = autorRepository.autoresPorFechaDeMuerte(fecha);
        autores.forEach(System.out::println);
    }

    public void listaLibrosPorIdioma() {
        idiomas = libroRepository.idiomasLibros();
        System.out.println("------------------IDIOMAS--------------------");
        idiomas.forEach(System.out::println);
        System.out.println("---------------------------------------------");
        System.out.println("Ingresa el idioma a buscar: ");
        var idiomaSeleccionado = teclado.nextLine().toLowerCase();
        libros = libroRepository.librosPorIdioma(idiomaSeleccionado);
        if (libros.isEmpty()) {
            System.out.println("Opción no válida");
        } else {
            libros.forEach(System.out::println);
        }
    }
}



