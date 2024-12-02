package co.edu.ufps.Examen_final.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.Examen_final.Dtos.FacturaRequestDTO;
import co.edu.ufps.Examen_final.Services.FacturaService;

@RestController
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/crear/{tiendaUuid}")
    public ResponseEntity<?> crearFactura(@RequestBody FacturaRequestDTO facturaRequest,
                                          @PathVariable String tiendaUuid) {
        String mensaje = facturaService.procesarFactura(facturaRequest, tiendaUuid);

        return ResponseEntity.ok().body(
                new ResponseDTO("success", mensaje, null)
        );
    }
}