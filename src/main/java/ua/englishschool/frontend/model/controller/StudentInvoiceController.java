package ua.englishschool.frontend.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.englishschool.frontend.entity.dto.StudentInvoiceDto;
import ua.englishschool.frontend.model.service.StudentInvoiceService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/invoices")
public class StudentInvoiceController {

    private static final String ERROR_MESSAGES_ATTRIBUTE_NAME = "errorMessage";

    private static final String SUCCESS_MESSAGES_ATTRIBUTE_NAME = "successMessage";

    private static final String URL_GET_WAIT_INVOICES = "/wait";

    private static final String URL_GET_UNPAID_INVOICES_BY_PHONE = "/unpaid-by-phone";

    private static final String URL_PAYMENT = "/payment";

    @Autowired
    private StudentInvoiceService studentInvoiceService;


    @GetMapping(URL_GET_WAIT_INVOICES)
    public ModelAndView unpaidInvoices(ModelAndView modelAndView) {
        List<StudentInvoiceDto> invoicesDto = studentInvoiceService.getUnpaidInvoicesDto();
        modelAndView.addObject("invoicesDto", invoicesDto);
        return modelAndView;
    }

    @PostMapping(URL_PAYMENT)
    public ModelAndView unpaidInvoices(ModelAndView modelAndView, @ModelAttribute("invoiceId") long invoiceId) {
        boolean payment = studentInvoiceService.payment(invoiceId);
        if (!payment) {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, "Something went wrong");
        } else {
            modelAndView.addObject(SUCCESS_MESSAGES_ATTRIBUTE_NAME, "Invoice was payed");
        }
        modelAndView.setViewName("/home");

        return modelAndView;
    }

    @GetMapping(URL_GET_UNPAID_INVOICES_BY_PHONE)
    public ModelAndView unpaidInvoicesByPhone(ModelAndView modelAndView) {
        return modelAndView;
    }

    @PostMapping(URL_GET_UNPAID_INVOICES_BY_PHONE)
    public ModelAndView unpaidInvoicesByPhone(ModelAndView modelAndView, @ModelAttribute("phone") String phone) {
        List<StudentInvoiceDto> invoiceDtos = studentInvoiceService.getAllUnpaidByPhone(phone);
        modelAndView.addObject("unpaidInvoices", invoiceDtos);
        return modelAndView;
    }

}
