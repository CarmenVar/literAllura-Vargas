package literAllura.vargas.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fechaDeNacimiento;

    @Column(nullable = true) // Allow null for living authors
    private LocalDate fechaDeMuerte;

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = LocalDate.parse(datosAutor.fechaDeNacimiento());
        try {
            this.fechaDeMuerte = datosAutor.fechaDeMuerte() != null ? LocalDate.parse(datosAutor.fechaDeMuerte()) : null;
        } catch (DateTimeParseException e) {
            this.fechaDeMuerte = null;
        }
    }
    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public LocalDate getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(LocalDate fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", fechaDeMuerte=" + fechaDeMuerte +
                '}';
    }

    public static Autor getPrimerAutor(DatosLibros datosLibros) {
        if (datosLibros != null && datosLibros.idioma() != null && !datosLibros.idioma().isEmpty()) {

        }
        return null;
    }
}



