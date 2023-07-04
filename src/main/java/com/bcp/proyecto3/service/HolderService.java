package com.bcp.proyecto3.service;

import com.bcp.proyecto3.model.Holder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HolderService {
    Flux<Holder> getAllHolders();
    Mono<Holder> createHolder(Holder Holder);
    Mono<Holder> updateHolder(String id, Holder updatedHolder);
    Mono<Holder> getHolderById(String id);
    Mono<Holder> deleteHolderById(String id);
}
