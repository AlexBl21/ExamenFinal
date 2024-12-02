package co.edu.ufps.Examen_final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ufps.Examen_final.entities.TipoDocumentoE;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoE, Long> {
    // Puedes agregar métodos específicos aquí
}
