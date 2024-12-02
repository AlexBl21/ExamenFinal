package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Métodos personalizados si es necesario
}
