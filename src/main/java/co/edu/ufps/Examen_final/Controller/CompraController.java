package co.edu.ufps.Examen_final.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.Examen_final.Dtos.CompraRequest;
import co.edu.ufps.Examen_final.Dtos.CompraResponse;
import co.edu.ufps.Examen_final.Services.CompraService;

@RestController
@RequestMapping("/crear")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/{tiendaId}")
    public ResponseEntity<CompraResponse> crearFactura(@PathVariable String tiendaId, @RequestBody CompraRequest compraRequest) {
        try {
            CompraResponse compraResponse = compraService.procesarCompra(tiendaId, compraRequest);
            return new ResponseEntity<>(compraResponse, HttpStatus.OK);
        } catch (Exception e) {
            CompraResponse response = new CompraResponse();
            response.setStatus("error");
            response.setMessage("Error procesando la compra: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}


