package ar.com.bbva.crud.dto;

import ar.com.bbva.crud.model.ProductCode;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ClientResponseDTO(

        Long id,
        Integer dni,
        @JsonProperty("nombre") String name,
        @JsonProperty("apellido") String lastName,
        @JsonProperty("telefono") String phone,
        @JsonProperty("celular") String cellPhone,
        @JsonProperty("productoBancario") List<ProductCode> productCodes
) {
}
