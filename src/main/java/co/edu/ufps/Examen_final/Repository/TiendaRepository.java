package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.Tienda;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {
	Tienda findByUuid(String uuid);
}
