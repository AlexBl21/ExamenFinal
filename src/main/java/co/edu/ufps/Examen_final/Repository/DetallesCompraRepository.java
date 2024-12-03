package co.edu.ufps.Examen_final.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.Examen_final.entities.Compra;
import co.edu.ufps.Examen_final.entities.DetallesCompra;

public interface DetallesCompraRepository extends JpaRepository<DetallesCompra, Long> {
	 List<DetallesCompra> findByCompra(Compra compra);
}
