package co.edu.ufps.Examen_final.Dtos;

import java.util.List;

public class CompraRequest {
    private double impuesto;
    private ClienteRequest cliente;
    private List<ProductoRequest> productos;
    private List<MedioPagoRequest> medios_pago;
    private String vendedorDocumento;  // Documento del vendedor
    private String cajeroToken;  // Token del cajero

    // Getters y Setters
    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public ClienteRequest getCliente() {
        return cliente;
    }

    public void setCliente(ClienteRequest cliente) {
        this.cliente = cliente;
    }

    public List<ProductoRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoRequest> productos) {
        this.productos = productos;
    }

    public List<MedioPagoRequest> getMedios_pago() {
        return medios_pago;
    }

    public void setMedios_pago(List<MedioPagoRequest> medios_pago) {
        this.medios_pago = medios_pago;
    }

    public String getVendedorDocumento() {
        return vendedorDocumento;
    }

    public void setVendedorDocumento(String vendedorDocumento) {
        this.vendedorDocumento = vendedorDocumento;
    }

    public String getCajeroToken() {
        return cajeroToken;
    }

    public void setCajeroToken(String cajeroToken) {
        this.cajeroToken = cajeroToken;
    }
}
