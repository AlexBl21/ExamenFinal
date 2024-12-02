package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.TipoPago;

public interface TipoPagoRepository extends JpaRepository<TipoPago, Long> {
    // Métodos personalizados si se requieren
}
