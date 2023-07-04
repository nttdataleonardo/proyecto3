package com.bcp.proyecto3.serviceimpl;

 
import com.bcp.proyecto3.exceptions.ConflictException;
import com.bcp.proyecto3.exceptions.NotFoundException;
import com.bcp.proyecto3.model.AuthorizedSigner;
import com.bcp.proyecto3.model.Client;
import com.bcp.proyecto3.model.Holder;
import com.bcp.proyecto3.repository.HolderRepository;
import com.bcp.proyecto3.service.HolderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HolderServiceImpl implements HolderService {
    private final HolderRepository holderRepository;

    @Autowired
    public HolderServiceImpl(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }

    @Override
    public Flux<Holder> getAllHolders() {
        return this.holderRepository.findAll().map(Holder::toHolder);
    }

    @Override
    public Mono<Holder> createHolder(Holder holder) {
        return holderRepository.findById(holder.getId())
                .flatMap(holderEntity ->
                        Mono.error(new ConflictException("Holder Code already exists: " + holder.getId()))
                ).then(Mono.just(holder.toHolder()))
                .flatMap(this.holderRepository::save)
                .map(Holder::toHolder);
    }

    @Override
    public Mono<Holder> updateHolder(String id, Holder updatedHolder) {
        return this.holderRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Holder: " + id)))
                .flatMap(holderEntity -> {
                    BeanUtils.copyProperties(holderRepository, holderEntity, "created", "isDeleted");
                    return Mono.just(holderEntity);
                })
                .flatMap(this.holderRepository::save)
                .map(Holder::toHolder);
    }

    @Override
    public Mono<Holder> getHolderById(String id) {
        return this.holderRepository
                .findById(id)
                .switchIfEmpty(Mono.error( new ConflictException("Holder does not exist : " + id)))
                .map(Holder::toHolder);
    }

    @Override
    public Mono<Holder> deleteHolderById(String id) {
        return holderRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Client: " + id)))
                .flatMap(holderEntity -> {
                    return holderRepository.deleteById(holderEntity.getId())
                            .thenReturn(holderEntity.toHolder());
                });
    }
}
