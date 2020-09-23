package ua.englishschool.frontend.model.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.englishschool.frontend.entity.Contract;
import ua.englishschool.frontend.entity.dto.ContractDto;
import ua.englishschool.frontend.entity.dto.CreateContractDto;
import ua.englishschool.frontend.model.service.ContractService;
import ua.englishschool.frontend.model.service.RestHandler;

import java.util.List;
import java.util.Optional;

@Component
public class ContractRestServiceImpl implements ContractService {

    private static final String URL_FIND_CONTRACT_DTO_BY_PHONE = "/contracts/find-by-phone/";

    private static final String URL_CREATE_CONTRACT = "/contracts/create-contract";

    private static final String RESPONSE_ERROR_MESSAGE = "Response is null or has no body.";

    private RestHandler restHandler;

    private Logger logger;

    @Autowired
    public ContractRestServiceImpl(RestHandler restHandler, Logger logger) {
        this.restHandler = restHandler;
        this.logger = logger;
    }

    @Override
    public Optional<ContractDto> findByPhone(String phone) {
        logger.debug("Find ContractDto by phone has been executed :" + phone);
        String URL = URL_FIND_CONTRACT_DTO_BY_PHONE + phone;
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Find ContractDto by phone is failed. " + RESPONSE_ERROR_MESSAGE);
            return Optional.empty();
        }
        Optional<ContractDto> contractDto = getContractDtoFromResponseBody(responseEntity.get());
        logger.debug("Find ContractDto by phone is finished successfully.");
        return contractDto;

    }

    @Override
    public Optional<Long> createContract(CreateContractDto createContractDto) {
        logger.debug("Create Contract has been executed for : " + createContractDto.toString());
        Optional<ResponseEntity> responseEntity = restHandler.doPost(URL_CREATE_CONTRACT, createContractDto);

        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Create Contract is failed." + RESPONSE_ERROR_MESSAGE);
            return Optional.empty();
        }

        Long contractId = Long.valueOf(responseEntity.get().getBody().toString());
        logger.debug("Create Contract is finished successfully with id: " + contractId);
        return Optional.ofNullable(contractId);
    }

    private Optional<ContractDto> getContractDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        ContractDto contractDto;
        try {
            contractDto = mapper.readValue(body, ContractDto.class);
        } catch (JsonProcessingException e) {
            logger.error("Get Student from ResponseBody is failed " + e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(contractDto);
    }
}

