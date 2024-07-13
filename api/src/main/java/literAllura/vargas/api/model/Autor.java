package literAllura.vargas.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fechaDeNacimiento;

    @Column(nullable = false)
    private LocalDate fechaDeMuerte;

    public Autor(DatosAutor datosAutor) {
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
}


