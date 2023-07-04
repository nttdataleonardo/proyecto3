package com.bcp.proyecto3.service;

import com.bcp.proyecto3.model.AuthorizedSigner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorizedSignerService {
    Flux<AuthorizedSigner> getAllAuthorizedSigners();
    Mono<AuthorizedSigner> createAuthorizedSigner(AuthorizedSigner AuthorizedSigner);
    Mono<AuthorizedSigner> updateAuthorizedSigner(String id, AuthorizedSigner updatedAuthorizedSigner);
    Mono<AuthorizedSigner> getAuthorizedSignerById(String id);
    Mono<AuthorizedSigner> deleteAuthorizedSignerById(String id);
}
