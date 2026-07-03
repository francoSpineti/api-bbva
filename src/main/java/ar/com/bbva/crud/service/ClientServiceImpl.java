package ar.com.bbva.crud.service;

import ar.com.bbva.crud.exception.ClientNotFoundException;
import ar.com.bbva.crud.dto.ClientRequestDTO;
import ar.com.bbva.crud.dto.ClientResponseDTO;
import ar.com.bbva.crud.factory.ClientFactory;
import ar.com.bbva.crud.mapper.ClientMapper;
import ar.com.bbva.crud.model.Client;
import ar.com.bbva.crud.model.ClientProduct;
import ar.com.bbva.crud.model.Product;
import ar.com.bbva.crud.model.ProductCode;
import ar.com.bbva.crud.repository.ClientRepository;
import ar.com.bbva.crud.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ProductRepository productRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::entityToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO getClientByID(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));

      return clientMapper.entityToDTO(client);
    }

    @Override
    @Transactional
    public ClientResponseDTO saveClient(ClientRequestDTO requestDTO) {
        if (requestDTO.productCodes() == null || requestDTO.productCodes().isEmpty()) {
            throw new IllegalArgumentException("The client must have at least one banking product.");
        }

        if (clientRepository.existsByDni(requestDTO.dni())) {
            throw new IllegalArgumentException("DNI " + requestDTO.dni() + " it is already registered.");
        }

        Client client = ClientFactory.create(requestDTO);

        for (ProductCode code : requestDTO.productCodes()) {
            Product product = productRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid banking product code: " + code));
            ClientProduct clientProduct = new ClientProduct(client, product);
            client.getClientProducts().add(clientProduct);
        }

        Client savedClient = clientRepository.save(client);
        return clientMapper.entityToDTO(savedClient);
    }

    @Override
    @Transactional
    public ClientResponseDTO updateClientByPhone(Long id, String phone) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));

        client.setPhone(phone);
        clientRepository.save(client);

        return clientMapper.entityToDTO(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getClientsByProduct(ProductCode productCode) {
        return clientRepository.findClientsByProductCode(productCode).stream()
                .map(clientMapper::entityToDTO)
                .toList();
    }

}
