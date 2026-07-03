package ar.com.bbva.crud.repository;

import ar.com.bbva.crud.model.Client;
import ar.com.bbva.crud.model.ProductCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT DISTINCT c FROM Client c JOIN c.clientProducts cp WHERE cp.product.code = :productCode")
    List<Client> findClientsByProductCode(@Param("productCode") ProductCode productCode);

    boolean existsByDni(Integer dni);
}
