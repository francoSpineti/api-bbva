package ar.com.bbva.crud.repository;

import ar.com.bbva.crud.model.Product;
import ar.com.bbva.crud.model.ProductCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(ProductCode code);
    boolean existsByCode(ProductCode code);
}
