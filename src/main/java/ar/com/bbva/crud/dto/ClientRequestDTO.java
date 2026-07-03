package ar.com.bbva.crud.dto;

import ar.com.bbva.crud.model.ProductCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.util.List;

public record ClientRequestDTO(

        @NotNull(message = "dni required")
        @Min(value = 1000000, message = "The ID number must have at least 7 digits.")
        @Max(value = 99999999, message = "The national ID number must have a maximum of 8 digits.")
        Integer dni,

        @JsonProperty("nombre")
        @NotBlank(message = "name is required")
        String name,

        @JsonProperty("apellido")
        @NotBlank(message = "lastName is required ")
        String lastName,

        @JsonProperty("calle")
        @Size(max = 100, message = "The street name must not exceed 100 characters.")
        String street,

        @JsonProperty("numero")
        @Positive(message = "The street number must be positive.")
        Integer streetNumber,

        @JsonProperty("codigoPostal")
        @Positive(message = "The zip code must be positive.")
        Integer zipCode,

        @JsonProperty("telefono")
        String phone,

        @JsonProperty("celular")
        @NotBlank(message = "cellphone is required.")
        String cellPhone,

        @JsonProperty("productoBancario")
        @NotEmpty(message = "The client must have at least one banking product.")
        List<ProductCode> productCodes
) {
}
