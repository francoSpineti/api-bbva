package ar.com.bbva.crud.config;

import ar.com.bbva.crud.model.Product;
import ar.com.bbva.crud.model.ProductCode;
import ar.com.bbva.crud.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DatabaseSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (ProductCode code : ProductCode.values()) {
            if (!productRepository.existsByCode(code)) {
                Product product = new Product();
                product.setCode(code);
                product.setName(getProductName(code));
                productRepository.save(product);
            }
        }
    }

    private String getProductName(ProductCode code) {
        return switch (code) {
            case PZOF -> "Plazo Fijo";
            case CHEQ -> "Cheque";
            case CA -> "Caja de Ahorro";
            case CC -> "Cuenta Corriente";
            case TJCREDITO -> "Tarjeta de Crédito";
            case TJDEBITO -> "Tarjeta de Débito";
        };
    }
}