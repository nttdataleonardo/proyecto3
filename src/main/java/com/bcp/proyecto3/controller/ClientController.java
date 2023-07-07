package com.bcp.proyecto3.controller;

import com.bcp.proyecto3.dto.ClientSummaryDto;
import com.bcp.proyecto3.model.Client;
import com.bcp.proyecto3.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PutMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Client> updateClient(@PathVariable String id, @RequestBody Client updatedClient) {
        return clientService.updateClient(id, updatedClient);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Client> getClientById(@PathVariable String id) {
        return clientService.getClientById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Client> deleteClientById(@PathVariable String id) {
        return clientService.deleteClientById(id);
    }

    @GetMapping(value = "/generateSummary/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientSummaryDto> generateSummary(@PathVariable String id) {
        return clientService.generateSummary(id);
    }

    @GetMapping(value = "/checkMainAccountBalance/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Client> checkMainAccountBalance(@PathVariable String id) {
        return clientService.getClientById(id);
    }
}
