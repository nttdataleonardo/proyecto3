package com.bcp.proyecto3.controller;

import com.bcp.proyecto3.model.AuthorizedSigner;
import com.bcp.proyecto3.service.AuthorizedSignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/autorizedsigners")
public class AuthorizedSignerController {
    @Autowired
    private AuthorizedSignerService authorizedsignerService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AuthorizedSigner> getAllUsers() {
        return authorizedsignerService.getAllAuthorizedSigners();
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorizedSigner> createauthorizedsigner(@RequestBody AuthorizedSigner authorizedsigner) {
        return authorizedsignerService.createAuthorizedSigner(authorizedsigner);
    }

    @PutMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthorizedSigner> updateauthorizedsigner(@PathVariable String id,@RequestBody AuthorizedSigner updatedauthorizedsigner) {
        return authorizedsignerService.updateAuthorizedSigner(id, updatedauthorizedsigner);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthorizedSigner> getauthorizedsignerById(@PathVariable String id) {
        return authorizedsignerService.getAuthorizedSignerById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthorizedSigner> deleteauthorizedsignerById(@PathVariable String id) {
        return authorizedsignerService.deleteAuthorizedSignerById(id);
    }

}
