package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    // Métodos personalizados según la necesidad
}
