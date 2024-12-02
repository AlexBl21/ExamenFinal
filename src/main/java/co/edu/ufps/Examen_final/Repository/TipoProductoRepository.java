package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.TipoProducto;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
    // Métodos personalizados si es necesario
}
