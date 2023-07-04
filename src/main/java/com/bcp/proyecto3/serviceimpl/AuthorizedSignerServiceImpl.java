package com.bcp.proyecto3.serviceimpl;

import com.bcp.proyecto3.exceptions.ConflictException;
import com.bcp.proyecto3.exceptions.NotFoundException;
import com.bcp.proyecto3.model.AuthorizedSigner;
import com.bcp.proyecto3.model.Product;
import com.bcp.proyecto3.repository.AuthorizedSignerRepository;
import com.bcp.proyecto3.service.AuthorizedSignerService;
import com.bcp.proyecto3.utils.AppUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AuthorizedSignerServiceImpl implements AuthorizedSignerService {
    private final AuthorizedSignerRepository authorizedSignerRepository;

    @Autowired
    public AuthorizedSignerServiceImpl(AuthorizedSignerRepository authorizedSignerRepository) {
        this.authorizedSignerRepository = authorizedSignerRepository;
    }

    @Override
    public Flux<AuthorizedSigner> getAllAuthorizedSigners() {
        return this.authorizedSignerRepository.findAll().map(AuthorizedSigner::toAuthorizedSigner);
    }

    @Override
    public Mono<AuthorizedSigner> createAuthorizedSigner(AuthorizedSigner authorizedSigner) {
        return authorizedSignerRepository.findById(authorizedSigner.getId())
                .flatMap(authorizedSignerEntity ->
                        Mono.error(new ConflictException("AuthorizedSigner Code already exists: " + authorizedSignerEntity.getId()))
                ).cast(AuthorizedSigner.class)
                .switchIfEmpty(authorizedSignerRepository.save(authorizedSigner));
    }

    private Mono<AuthorizedSigner> saveAuthorizedSigner(AuthorizedSigner authorizedSigner){
        return authorizedSignerRepository.save(authorizedSigner);
    }

    @Override
    public Mono<AuthorizedSigner> updateAuthorizedSigner(String id, AuthorizedSigner updatedAuthorizedSigner) {
        return this.authorizedSignerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent AuthorizedSigner: " + id)))
                .flatMap(authorizedSignerEntity -> {
                    BeanUtils.copyProperties(updatedAuthorizedSigner, authorizedSignerEntity, "created", "isDeleted");
                    return Mono.just(authorizedSignerEntity);
                })
                .flatMap(this.authorizedSignerRepository::save)
                .map(AuthorizedSigner::toAuthorizedSigner);
    }

    @Override
    public Mono<AuthorizedSigner> getAuthorizedSignerById(String id) {
        return this.authorizedSignerRepository.findById(id);
    }

    @Override
    public Mono<AuthorizedSigner> deleteAuthorizedSignerById(String id) {
        return authorizedSignerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent AuthorizedSigner: " + id)))
                .flatMap(authorizedSignerEntity -> {
                    return authorizedSignerRepository.deleteById(authorizedSignerEntity.getId())
                            .thenReturn(authorizedSignerEntity.toAuthorizedSigner());
                });
    }
}
