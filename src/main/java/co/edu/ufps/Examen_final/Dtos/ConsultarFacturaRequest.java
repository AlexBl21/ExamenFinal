package co.edu.ufps.Examen_final.Dtos;

import lombok.Data;

@Data
public class ConsultarFacturaRequest {
	private String token;
    private String cliente;
    private Integer factura;
}
