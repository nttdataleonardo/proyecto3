package com.bcp.proyecto3.controller;
 
import com.bcp.proyecto3.model.Holder;
import com.bcp.proyecto3.service.HolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/holders")
public class HolderController {
    @Autowired
    private HolderService holderService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Holder> getAllUsers() {
        return holderService.getAllHolders();
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Holder> createholder(@RequestBody Holder holder) {
        return holderService.createHolder(holder);
    }

    @PutMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Holder> updateholder(@PathVariable String id,@RequestBody Holder updatedholder) {
        return holderService.updateHolder(id,updatedholder);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Holder> getholderById(@PathVariable String id) {
        return holderService.getHolderById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Holder> deleteholderById(@PathVariable String id) {
        return holderService.deleteHolderById(id);
    }

}
