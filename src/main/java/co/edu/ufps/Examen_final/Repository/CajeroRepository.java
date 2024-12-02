package co.edu.ufps.Examen_final.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.Examen_final.entities.Cajero;

public interface CajeroRepository extends JpaRepository<Cajero, Long> {
    // Métodos personalizados, si es necesario
}
