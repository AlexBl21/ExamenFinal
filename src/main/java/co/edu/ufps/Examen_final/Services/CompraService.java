package co.edu.ufps.Examen_final.Services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.Examen_final.Dtos.CompraRequest;
import co.edu.ufps.Examen_final.Dtos.CompraResponse;
import co.edu.ufps.Examen_final.Dtos.ProductoRequest;
import co.edu.ufps.Examen_final.Repository.CajeroRepository;
import co.edu.ufps.Examen_final.Repository.ClienteRepository;
import co.edu.ufps.Examen_final.Repository.CompraRepository;
import co.edu.ufps.Examen_final.Repository.DetallesCompraRepository;
import co.edu.ufps.Examen_final.Repository.ProductoRepository;
import co.edu.ufps.Examen_final.Repository.TiendaRepository;
import co.edu.ufps.Examen_final.Repository.VendedorRepository;
import co.edu.ufps.Examen_final.entities.Cajero;
import co.edu.ufps.Examen_final.entities.Cliente;
import co.edu.ufps.Examen_final.entities.Compra;
import co.edu.ufps.Examen_final.entities.DetallesCompra;
import co.edu.ufps.Examen_final.entities.Tienda;
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

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private DetallesCompraRepository detallesCompraRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Transactional
    public CompraResponse procesarCompra(String tiendaId, CompraRequest compraRequest) {

        // Validar cliente, vendedor, cajero, tienda (lo has hecho correctamente)

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

        Tienda tienda = tiendaRepository.findByUuid(tiendaId);
        if (tienda == null) {
            throw new RuntimeException("Tienda no encontrada.");
        }

        // Crear la compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setTotal(0);
        compra.setFecha(LocalDate.now());

        double total = 0;

        for (ProductoRequest productoRequest : compraRequest.getProductos()) {
            // Buscar el producto por nombre (cambia referencia por nombre)
            Producto producto = productoRepository.findByNombre(productoRequest.getReferencia());
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado con nombre: " + productoRequest.getReferencia());
            }

            // Calcular el precio con descuento
            double precioConDescuento = producto.getPrecio() * (1 - productoRequest.getDescuento() / 100);
            total += precioConDescuento * productoRequest.getCantidad();

            // Crear los detalles de la compra
            DetallesCompra detalle = new DetallesCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(productoRequest.getCantidad());
            detalle.setPrecio(precioConDescuento);
            detalle.setDescuento(productoRequest.getDescuento());

            // Guardar el detalle de la compra
            detallesCompraRepository.save(detalle);
        }

        // Aplicar impuesto
        total += total * (compraRequest.getImpuesto() / 100);

        // Establecer el total en la compra
        compra.setTotal(total);

        // Guardar la compra
        compraRepository.save(compra);

        // Crear la respuesta
        String numeroFactura = generarNumeroFactura();
        CompraResponse response = new CompraResponse();
        response.setStatus("success");
        response.setMessage("La factura se ha creado correctamente con el número: " + numeroFactura);
        response.setData(new CompraResponse.CompraResponseData(numeroFactura, String.format("%.2f", total), LocalDate.now().toString()));

        return response;
    }

    private String generarNumeroFactura() {
        // Generar un número de factura único (simulado)
        return "12";  // Ejemplo de número de factura
    }
}


