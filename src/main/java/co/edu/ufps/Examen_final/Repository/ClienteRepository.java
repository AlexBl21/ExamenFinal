package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.Examen_final.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
   
}
