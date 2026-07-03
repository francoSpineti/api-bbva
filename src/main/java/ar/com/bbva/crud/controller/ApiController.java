package ar.com.bbva.crud.controller;

import ar.com.bbva.crud.dto.ClientRequestDTO;
import ar.com.bbva.crud.dto.ClientResponseDTO;
import ar.com.bbva.crud.model.ProductCode;
import ar.com.bbva.crud.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ApiController {

    private final ClientService clientService;

    public ApiController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ClientResponseDTO>> getAllClients(@PageableDefault(page = 0, size = 20) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients(pageable));
    }

    @GetMapping
    public ResponseEntity<ClientResponseDTO> getClientById(@RequestParam Long id){
        ClientResponseDTO response = clientService.getClientByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> save(@Valid @RequestBody ClientRequestDTO requestDTO){
        ClientResponseDTO responseDTO = clientService.saveClient(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClientByPhone(@PathVariable Long id, @RequestParam String phone){
        ClientResponseDTO response = clientService.updateClientByPhone(id, phone);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/by-product")
    public ResponseEntity<List<ClientResponseDTO>> getClientsByProduct(@RequestParam ProductCode code) {
        List<ClientResponseDTO> response = clientService.getClientsByProduct(code);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
