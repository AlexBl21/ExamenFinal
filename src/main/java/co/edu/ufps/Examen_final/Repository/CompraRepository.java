package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    // Métodos adicionales según las necesidades
}