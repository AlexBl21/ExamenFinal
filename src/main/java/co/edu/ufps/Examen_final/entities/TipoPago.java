package co.edu.ufps.Examen_final.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
}
