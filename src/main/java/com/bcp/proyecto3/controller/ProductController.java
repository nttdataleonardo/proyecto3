package com.bcp.proyecto3.controller;

import com.bcp.proyecto3.model.Product;
import com.bcp.proyecto3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getAllUsers() {
        return productService.getAllProducts();
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product Product) {
        return productService.createProduct(Product);
    }

    @PutMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> deleteProductById(@PathVariable String id) {
        return productService.deleteProductById(id);
    }

    @PutMapping(value = "/deposit/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> deposit(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productService.depositProduct(id, updatedProduct);
    }

    @PutMapping(value = "/withdrawal/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> withdrawal(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productService.withdrawal(id, updatedProduct);
    }

    @PutMapping(value = "/paycredit/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> paycredit(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productService.paycredit(id, updatedProduct);
    }

    @PutMapping(value = "/chargeconsumptions/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> chargeconsumptions(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productService.chargeconsumptions(id, updatedProduct);
    }

    @GetMapping(value = "/availablebalances/{idClient}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Product> availablebalances(@PathVariable String idClient) {
        return productService.availablebalances(idClient);
    }

    @PutMapping(value = "/transferBetweenAccounts/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> transferBetweenAccounts(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productService.transferBetweenAccounts(id, updatedProduct);
    }
}
