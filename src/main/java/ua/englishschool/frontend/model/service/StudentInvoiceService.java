package ua.englishschool.frontend.model.service;

import ua.englishschool.frontend.entity.dto.StudentInvoiceDto;

import java.util.List;

public interface StudentInvoiceService {

    List<StudentInvoiceDto> getUnpaidInvoicesDto();

    boolean payment(long invoiceId);

    List<StudentInvoiceDto> getAllUnpaidByPhone(String phone);
}
