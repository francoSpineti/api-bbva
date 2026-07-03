package ar.com.bbva.crud.service;

import ar.com.bbva.crud.dto.ClientRequestDTO;
import ar.com.bbva.crud.dto.ClientResponseDTO;
import ar.com.bbva.crud.model.ProductCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ClientService {

    Page<ClientResponseDTO> getAllClients(Pageable pageable);
    ClientResponseDTO getClientByID(Long id);
    ClientResponseDTO saveClient(ClientRequestDTO requestDTO);
    ClientResponseDTO updateClientByPhone(Long id, String phone);
    List<ClientResponseDTO> getClientsByProduct(ProductCode productCode);
}
