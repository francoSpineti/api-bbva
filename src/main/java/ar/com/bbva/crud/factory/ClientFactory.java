package ar.com.bbva.crud.factory;

import ar.com.bbva.crud.dto.ClientRequestDTO;
import ar.com.bbva.crud.model.Client;

public final class ClientFactory {

    private ClientFactory(){}

    public static Client create(ClientRequestDTO request){

        Client client = new Client();

        client.setDni(request.dni());
        client.setName(request.name());
        client.setLastName(request.lastName());
        client.setStreet(request.street());
        client.setStreetNumber(request.streetNumber());
        client.setZipCode(request.zipCode());
        client.setPhone(request.phone());
        client.setCellPhone(request.cellPhone());
        return client;
    }

}
