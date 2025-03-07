package co.edu.ufps.Examen_final.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id")
    private TipoPago tipoPago;

    private String tarjetaTipo; 
    private int cuotas;       
    private double valor;
}
