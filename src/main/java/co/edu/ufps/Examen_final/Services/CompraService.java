package co.edu.ufps.Examen_final.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.Examen_final.Dtos.CompraRequest;
import co.edu.ufps.Examen_final.Dtos.CompraResponse;
import co.edu.ufps.Examen_final.Dtos.ProductoRequest;
import co.edu.ufps.Examen_final.Repository.CajeroRepository;
import co.edu.ufps.Examen_final.Repository.ClienteRepository;
import co.edu.ufps.Examen_final.Repository.ProductoRepository;
import co.edu.ufps.Examen_final.Repository.TiendaRepository;
import co.edu.ufps.Examen_final.Repository.VendedorRepository;
import co.edu.ufps.Examen_final.entities.Cajero;
import co.edu.ufps.Examen_final.entities.Cliente;
import co.edu.ufps.Examen_final.entities.Producto;
import co.edu.ufps.Examen_final.entities.Vendedor;
import jakarta.transaction.Transactional;

@Service
public class CompraService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CajeroRepository cajeroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public CompraResponse procesarCompra(String tiendaId, CompraRequest compraRequest) {

        Cliente cliente = clienteRepository.findByDocumento(compraRequest.getCliente().getDocumento());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado.");
        }

        Vendedor vendedor = vendedorRepository.findByDocumento(compraRequest.getVendedorDocumento());
        if (vendedor == null) {
            throw new RuntimeException("Vendedor no encontrado.");
        }

        Cajero cajero = cajeroRepository.findByToken(compraRequest.getCajeroToken());
        if (cajero == null) {
            throw new RuntimeException("Cajero no encontrado.");
        }

 
        double total = 0;
        for (ProductoRequest productoRequest : compraRequest.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia());
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado con referencia: " + productoRequest.getReferencia());
            }

            double precioConDescuento = producto.getPrecio() * (1 - productoRequest.getDescuento() / 100);
            total += precioConDescuento * productoRequest.getCantidad();
        }

 
        total += total * (compraRequest.getImpuesto() / 100);


        String numeroFactura = generarNumeroFactura(); 
        CompraResponse response = new CompraResponse();
        response.setStatus("success");
        response.setMessage("La compra se ha procesado correctamente con el número: " + numeroFactura);
        response.setData(new CompraResponseData(numeroFactura, total, "2024-01-01"));

        return response;
    }

    private String generarNumeroFactura() {
 
        return "12";
    }
}
