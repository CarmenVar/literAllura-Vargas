package literAllura.vargas.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fechaDeNacimiento,
        @JsonAlias("death_year") String fechaDeMuerte
) {
    public LocalDate getFechaDeNacimiento() {
        return LocalDate.parse(fechaDeNacimiento);
    }

    public LocalDate getFechaDeMuerte() {
        return fechaDeMuerte != null ? LocalDate.parse(fechaDeMuerte) : null;
    }
}
