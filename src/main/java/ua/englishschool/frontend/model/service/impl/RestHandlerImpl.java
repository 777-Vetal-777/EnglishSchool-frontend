package ua.englishschool.frontend.model.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ua.englishschool.frontend.model.service.RestHandler;

import java.util.Optional;

@Component
public class RestHandlerImpl implements RestHandler {

    private static final String NOT_NULL_ENDPOINT_MESSAGE = "URL endpoint must not be null";

    private static final String NOT_NULL_OBJECT_MESSAGE = "Object must not be null";

    private static final String REQUEST_FAILED_ERROR_MESSAGE = "Request failed. Response status code '%s' ";

    @Value("${backend.domain}")
    private String backendAppDomain;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Logger logger;


    @Override
    public <T> Optional<ResponseEntity> doPost(String endpoint, T object) {
        Assert.notNull(endpoint, NOT_NULL_ENDPOINT_MESSAGE);
        Assert.notNull(object, NOT_NULL_OBJECT_MESSAGE);

        String url = backendAppDomain + endpoint;
        HttpEntity<T> requestEntity = new HttpEntity<>(object, createHttpHeader());
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            logger.error(String.format(REQUEST_FAILED_ERROR_MESSAGE, e.getStatusCode()));
            return Optional.empty();
        }
        return Optional.ofNullable(responseEntity);

    }

    @Override
    public Optional<ResponseEntity> doGet(String endpoint) {
        Assert.notNull(endpoint, NOT_NULL_ENDPOINT_MESSAGE);


        String url = backendAppDomain + endpoint;
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            logger.error(String.format(REQUEST_FAILED_ERROR_MESSAGE, e.getStatusCode()));
            return Optional.empty();
        }
        return Optional.ofNullable(responseEntity);

    }

    public boolean doPut(String endpoint) {
        Assert.notNull(endpoint, NOT_NULL_ENDPOINT_MESSAGE);


        String url = backendAppDomain + endpoint;
        HttpEntity requestEntity = new HttpEntity(createHttpHeader());

        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class);
        } catch (HttpClientErrorException e) {
            logger.error(String.format(REQUEST_FAILED_ERROR_MESSAGE, e.getStatusCode()));
            return false;
        }
        return true;
    }

    @Override
    public boolean doPut(String endpoint, Object object) {
        return false;
    }

    @Override
    public boolean doDeleteToken(String endpoint, Object object) {
        return false;
    }

    protected HttpHeaders createHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
