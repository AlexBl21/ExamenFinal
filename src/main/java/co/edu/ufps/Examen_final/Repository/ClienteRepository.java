package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.Examen_final.entities.Cliente;
import co.edu.ufps.Examen_final.entities.Producto;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	 Cliente findByDocumento(String documento);
}
