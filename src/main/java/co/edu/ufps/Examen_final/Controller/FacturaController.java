package co.edu.ufps.Examen_final.Controller;

import co.edu.ufps.Examen_final.entities.Compra;
import co.edu.ufps.Examen_final.Services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<Compra> crearFactura(@RequestBody Compra compra) {
        Compra nuevaFactura = facturaService.crearFactura(compra);
        return ResponseEntity.ok(nuevaFactura);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> obtenerFacturas() {
        return ResponseEntity.ok(facturaService.obtenerFacturas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Compra>> obtenerFacturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
