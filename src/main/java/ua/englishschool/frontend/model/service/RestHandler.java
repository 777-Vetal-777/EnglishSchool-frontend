package ua.englishschool.frontend.model.service;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RestHandler {

    <T> Optional<ResponseEntity> doPost(String endpoint, T object);

    Optional<ResponseEntity> doGet(String endpoint);

    boolean doPut(String endpoint, Object object);

    boolean doDeleteToken(String endpoint, Object object);

    boolean doPut(String endpoint);


}
