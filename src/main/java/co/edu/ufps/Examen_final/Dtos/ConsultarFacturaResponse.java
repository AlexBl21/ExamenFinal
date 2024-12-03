package co.edu.ufps.Examen_final.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class ConsultarFacturaResponse {
    private double total;
    private double impuestos;
    private ClienteData cliente;
    private List<ProductoData> productos;
    private CajeroData cajero;

    @Data
    public static class ClienteData {
        private String documento;
        private String nombre;
        private String tipoDocumento;
    }

    @Data
    public static class ProductoData {
        private String referencia;
        private String nombre;
        private int cantidad;
        private double precio;
        private double descuento;
        private double subtotal;
    }

    @Data
    public static class CajeroData {
        private String documento;
        private String nombre;
    }
}
