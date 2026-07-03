package ar.com.bbva.crud.mapper;

import ar.com.bbva.crud.dto.ClientResponseDTO;
import ar.com.bbva.crud.model.Client;
import ar.com.bbva.crud.model.ClientProduct;
import ar.com.bbva.crud.model.ProductCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "productCodes", source = "clientProducts")
    ClientResponseDTO entityToDTO(Client client);

    default List<ProductCode> mapClientProducts(List<ClientProduct> clientProducts) {
        if (clientProducts == null) {
            return null;
        }
        return clientProducts.stream()
                .map(cp -> cp.getProduct().getCode())
                .toList();
    }
}