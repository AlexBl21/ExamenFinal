package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Métodos personalizados
}
