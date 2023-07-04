package com.bcp.proyecto3.service;

import com.bcp.proyecto3.dto.ClientSummaryDto;
import com.bcp.proyecto3.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<Client> getAllClients();
    Mono<Client> createClient(Client client);
    Mono<Client> updateClient(String id, Client updatedClient);
    Mono<Client> getClientById(String id);
    Mono<Client> deleteClientById(String id);

    Mono<ClientSummaryDto> generateSummary(String id);

}
