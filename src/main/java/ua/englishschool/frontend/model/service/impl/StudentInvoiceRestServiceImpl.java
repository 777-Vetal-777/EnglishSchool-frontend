package ua.englishschool.frontend.model.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.englishschool.frontend.entity.dto.StudentInvoiceDto;
import ua.englishschool.frontend.model.service.RestHandler;
import ua.englishschool.frontend.model.service.StudentInvoiceService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentInvoiceRestServiceImpl implements StudentInvoiceService {

    private static final String RESPONSE_ERROR_MESSAGE = "Response is null or has no body.";

    private static final String URL_WAIT_STUDENTS_INVOICES_DTO = "/invoices/wait";

    private static final String URL_PAYMENT = "/invoices/payment/";

    private static final String URL_GET_ALL_UNPAID_BY_PHONE = "/invoices/unpaid-by-phone/";

    @Autowired
    private RestHandler restHandler;

    @Autowired
    private Logger logger;

    @Override
    public List<StudentInvoiceDto> getUnpaidInvoicesDto() {
        logger.debug("Get all StudentInvoices has been executed");
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL_WAIT_STUDENTS_INVOICES_DTO);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get all StudentInvoices is failed. " + RESPONSE_ERROR_MESSAGE);
            return Collections.emptyList();
        }
        List<StudentInvoiceDto> listInvoicesDto = getStudentInvoicesDtoFromResponseBody(responseEntity.get());
        logger.debug("Get all StudentInvoices  is finished successfully.");
        return listInvoicesDto;
    }

    @Override
    public boolean payment(long invoiceId) {
        logger.debug("Change status invoice for student has been executed with id: " + invoiceId);
        String URL = URL_PAYMENT + invoiceId;
        boolean paid = restHandler.doPut(URL);
        if (!paid) {
            logger.error("Change status invoice for student was failed with id: " + invoiceId);
            return false;
        }
        logger.debug("Change status invoice for student is finished successfully with id: " + invoiceId);
        return true;
    }

    @Override
    public List<StudentInvoiceDto> getAllUnpaidByPhone(String phone) {
        logger.debug("Get all unpaid invoices by phone has been executed with phone: " + phone);
        String URL = URL_GET_ALL_UNPAID_BY_PHONE + phone;
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get all unpaid invoices by phone is failed with phone: " + phone);
            return Collections.emptyList();
        }
        List<StudentInvoiceDto> invoiceDtoList = getStudentInvoicesDtoFromResponseBody(responseEntity.get());
        logger.debug("Get all unpaid invoices by phone is finished successfully with phone: " + phone);
        return invoiceDtoList;
    }

    private List<StudentInvoiceDto> getStudentInvoicesDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        List<StudentInvoiceDto> listInvoices;
        try {
            listInvoices = mapper.readValue(body, new TypeReference<List<StudentInvoiceDto>>() {
            });
        } catch (IOException e) {
            logger.error(String.format("Get list of StudentsInvoicesDto from body failed. '%s' ", e.getMessage()));
            return Collections.emptyList();
        }
        return listInvoices;
    }
}
