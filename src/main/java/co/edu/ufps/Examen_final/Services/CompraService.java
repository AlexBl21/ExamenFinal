package co.edu.ufps.Examen_final.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.Examen_final.Dtos.CompraRequest;
import co.edu.ufps.Examen_final.Dtos.CompraResponse;
import co.edu.ufps.Examen_final.Dtos.ConsultarFacturaRequest;
import co.edu.ufps.Examen_final.Dtos.ConsultarFacturaResponse;
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
import co.edu.ufps.Examen_final.entities.Producto;
import co.edu.ufps.Examen_final.entities.Tienda;
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

        // Guardar la compra primero para obtener el ID
        compra = compraRepository.save(compra);

        double total = 0;

        for (ProductoRequest productoRequest : compraRequest.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia());

            if (producto == null) {
                throw new RuntimeException("Producto no encontrado con referencia: " + productoRequest.getReferencia());
            }

            double precioConDescuento = producto.getPrecio() * (1 - productoRequest.getDescuento() / 100);
            total += precioConDescuento * productoRequest.getCantidad();

            DetallesCompra detalle = new DetallesCompra();
            detalle.setCompra(compra);  // Aquí asociamos la compra con los detalles
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

        // Actualizar la compra con el total final
        compraRepository.save(compra);

        // Crear la respuesta
        CompraResponse response = new CompraResponse();
        response.setStatus("success");
        response.setMessage("La factura se ha creado correctamente.");
        response.setData(new CompraResponse.CompraResponseData(String.valueOf(compra.getId()), String.format("%.2f", total), LocalDate.now().toString()));

        return response;
    }

    @Transactional
    public ConsultarFacturaResponse consultarFactura(String tiendaId, ConsultarFacturaRequest consultaRequest) {
        // Validar el cajero por token
        Cajero cajero = cajeroRepository.findByToken(consultaRequest.getToken());
        if (cajero == null) {
            throw new RuntimeException("Cajero no autorizado para realizar esta consulta.");
        }

        // Validar el cliente
        Cliente cliente = clienteRepository.findByDocumento(consultaRequest.getCliente());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado.");
        }

        // Validar la tienda
        Compra compra = compraRepository.findById(consultaRequest.getFactura())
            .orElseThrow(() -> new RuntimeException("Factura no encontrada."));

        if (!compra.getTienda().getUuid().equals(tiendaId)) {
            throw new RuntimeException("La factura no pertenece a la tienda especificada.");
        }

        // Construir la respuesta
        ConsultarFacturaResponse response = new ConsultarFacturaResponse();
        response.setTotal(compra.getTotal());
        response.setImpuestos(compra.getImpuestos());

        // Cliente
        ConsultarFacturaResponse.ClienteData clienteData = new ConsultarFacturaResponse.ClienteData();
        clienteData.setDocumento(cliente.getDocumento());
        clienteData.setNombre(cliente.getNombre());
        clienteData.setTipoDocumento(cliente.getTipoDocumento().getNombre());
        response.setCliente(clienteData);

        // Productos
        List<ConsultarFacturaResponse.ProductoData> productos = new ArrayList<>();
        List<DetallesCompra> detalles = detallesCompraRepository.findByCompra(compra);
        for (DetallesCompra detalle : detalles) {
            Producto producto = detalle.getProducto();
            ConsultarFacturaResponse.ProductoData productoData = new ConsultarFacturaResponse.ProductoData();
            productoData.setReferencia(producto.getNombre());
            productoData.setNombre(producto.getNombre());
            productoData.setCantidad(detalle.getCantidad());
            productoData.setPrecio(producto.getPrecio());
            productoData.setDescuento(detalle.getDescuento());
            productoData.setSubtotal(detalle.getPrecio() * detalle.getCantidad());
            productos.add(productoData);
        }
        response.setProductos(productos);

        // Cajero
        ConsultarFacturaResponse.CajeroData cajeroData = new ConsultarFacturaResponse.CajeroData();
        cajeroData.setDocumento(cajero.getDocumento());
        cajeroData.setNombre(cajero.getNombre());
        response.setCajero(cajeroData);

        return response;
    }
}
