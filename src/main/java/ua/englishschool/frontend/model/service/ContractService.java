package ua.englishschool.frontend.model.service;

import ua.englishschool.frontend.entity.Contract;
import ua.englishschool.frontend.entity.dto.ContractDto;
import ua.englishschool.frontend.entity.dto.CreateContractDto;

import java.util.Optional;

public interface ContractService extends GenericService<Contract> {

    Optional<ContractDto> findByPhone(String phone);

    Optional<Long> createContract(CreateContractDto createContractDto);
}
