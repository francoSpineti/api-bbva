package ar.com.bbva.crud;

import ar.com.bbva.crud.dto.ClientRequestDTO;
import ar.com.bbva.crud.dto.ClientResponseDTO;
import ar.com.bbva.crud.exception.ClientNotFoundException;
import ar.com.bbva.crud.mapper.ClientMapper;
import ar.com.bbva.crud.model.Client;
import ar.com.bbva.crud.model.Product;
import ar.com.bbva.crud.model.ProductCode;
import ar.com.bbva.crud.repository.ClientRepository;
import ar.com.bbva.crud.repository.ProductRepository;
import ar.com.bbva.crud.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private Product product;
    private ClientResponseDTO responseDTO;
    private ClientRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        client = new Client(1L, 30000123, "Juan", "Garcia", "Alsina", 2258, 1234, null, "11 45645645");
        product = new Product(1L, ProductCode.CHEQ, "Cheque");
        responseDTO = new ClientResponseDTO(1L, 30000123, "Juan", "Garcia", null, "11 45645645", List.of(ProductCode.CHEQ));
        requestDTO = new ClientRequestDTO(30000123, "Juan", "Garcia", "Alsina", 2258, 1234, null, "11 45645645", List.of(ProductCode.CHEQ));
    }

    @Test
    void getAllClients_ShouldReturnPageOfClients() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Client> clientPage = new PageImpl<>(List.of(client));
        
        when(clientRepository.findAll(pageable)).thenReturn(clientPage);
        when(clientMapper.entityToDTO(client)).thenReturn(responseDTO);

        Page<ClientResponseDTO> result = clientService.getAllClients(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(responseDTO, result.getContent().get(0));
        verify(clientRepository).findAll(pageable);
    }

    @Test
    void getClientByID_WhenClientExists_ShouldReturnClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.entityToDTO(client)).thenReturn(responseDTO);

        ClientResponseDTO result = clientService.getClientByID(1L);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(clientRepository).findById(1L);
    }

    @Test
    void getClientByID_WhenClientDoesNotExist_ShouldThrowException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientByID(1L));
        verify(clientRepository).findById(1L);
    }

    @Test
    void saveClient_WithValidRequest_ShouldSaveAndReturnClient() {
        when(clientRepository.existsByDni(requestDTO.dni())).thenReturn(false);
        when(productRepository.findByCode(ProductCode.CHEQ)).thenReturn(Optional.of(product));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.entityToDTO(any(Client.class))).thenReturn(responseDTO);

        ClientResponseDTO result = clientService.saveClient(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(clientRepository).existsByDni(requestDTO.dni());
        verify(productRepository).findByCode(ProductCode.CHEQ);
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void saveClient_WithDuplicateDni_ShouldThrowException() {
        when(clientRepository.existsByDni(requestDTO.dni())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(requestDTO));
        verify(clientRepository).existsByDni(requestDTO.dni());
        verifyNoInteractions(productRepository);
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void saveClient_WithEmptyProductCodes_ShouldThrowException() {
        ClientRequestDTO invalidRequest = new ClientRequestDTO(30000123, "Juan", "Garcia", "Alsina", 2258, 1234, null, "11 45645645", Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(invalidRequest));
        verifyNoInteractions(productRepository, clientRepository);
    }

    @Test
    void updateClientByPhone_WhenClientExists_ShouldUpdateAndReturnClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.entityToDTO(client)).thenReturn(responseDTO);

        ClientResponseDTO result = clientService.updateClientByPhone(1L, "123456");

        assertNotNull(result);
        assertEquals("123456", client.getPhone());
        verify(clientRepository).findById(1L);
        verify(clientRepository).save(client);
    }

    @Test
    void getClientsByProduct_ShouldReturnMatchingClients() {
        when(clientRepository.findClientsByProductCode(ProductCode.CHEQ)).thenReturn(List.of(client));
        when(clientMapper.entityToDTO(client)).thenReturn(responseDTO);

        List<ClientResponseDTO> result = clientService.getClientsByProduct(ProductCode.CHEQ);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(clientRepository).findClientsByProductCode(ProductCode.CHEQ);
    }
}
