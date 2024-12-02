package co.edu.ufps.Examen_final.Dtos;

import java.util.List;

import lombok.Data;

@Data
public class FacturaRequestDTO {
    private double impuesto;

    private ClienteDTO cliente;

    private List<ProductoDTO> productos;

    private List<MedioPagoDTO> mediosPago;

    private VendedorDTO vendedor;

    private CajeroDTO cajero;

    @Data
    public static class ClienteDTO {
        private String documento;
        private String nombre;
        private String tipoDocumento;
    }

    @Data
    public static class ProductoDTO {
        private String referencia;
        private int cantidad;
        private double descuento;
    }

    @Data
    public static class MedioPagoDTO {
        private String tipoPago;
        private String tipoTarjeta;
        private int cuotas;
        private double valor;
    }

    @Data
    public static class VendedorDTO {
        private String documento;
    }

    @Data
    public static class CajeroDTO {
        private String token;
    }
}

