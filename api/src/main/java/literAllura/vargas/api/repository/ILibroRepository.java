package literAllura.vargas.api.repository;

import literAllura.vargas.api.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT DISTINCT l.idioma FROM Libro l")
    List<String> idiomasLibros();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> librosPorIdioma(String idioma); // Changed to librosPorIdioma
}
