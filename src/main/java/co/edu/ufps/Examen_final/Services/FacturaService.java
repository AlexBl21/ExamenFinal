package co.edu.ufps.Examen_final.Services;

import co.edu.ufps.Examen_final.entities.Compra;
import co.edu.ufps.Examen_final.Repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private CompraRepository compraRepository;

    public Compra crearFactura(Compra compra) {
        return compraRepository.save(compra);
    }

    public List<Compra> obtenerFacturas() {
        return compraRepository.findAll();
    }

    public Optional<Compra> obtenerFacturaPorId(Long id) {
        return compraRepository.findById(id);
    }

    public void eliminarFactura(Long id) {
        compraRepository.deleteById(id);
    }
}
