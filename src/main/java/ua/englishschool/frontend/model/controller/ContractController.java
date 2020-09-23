package ua.englishschool.frontend.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.englishschool.frontend.entity.dto.ContractDto;
import ua.englishschool.frontend.entity.dto.CourseDto;
import ua.englishschool.frontend.entity.dto.CreateContractDto;
import ua.englishschool.frontend.entity.dto.StudentDto;
import ua.englishschool.frontend.model.service.ContractService;
import ua.englishschool.frontend.model.service.CourseService;
import ua.englishschool.frontend.model.service.StudentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private static final String URL_CREATE_CONTRACT = "/create-contract";

    private static final String URL_FIND_BY_PHONE = "/find-by-phone";

    private static final String ERROR_MESSAGES_ATTRIBUTE_NAME = "errorMessage";

    private static final String SUCCESS_MESSAGES_ATTRIBUTE_NAME = "successMessage";

    private CourseService courseService;

    private ContractService contractService;

    private StudentService studentService;

    @Autowired
    public ContractController(CourseService courseService, ContractService contractService, StudentService studentService) {
        this.courseService = courseService;
        this.contractService = contractService;
        this.studentService = studentService;
    }

    @GetMapping(URL_FIND_BY_PHONE)
    public ModelAndView findByPhone(ModelAndView modelAndView) {
        return modelAndView;
    }

    @PostMapping(URL_FIND_BY_PHONE)
    public ModelAndView findByPhone(ModelAndView modelAndView, @ModelAttribute("phone") String phone) {
        Optional<StudentDto> student = studentService.findByPhone(phone);
        if (student.isEmpty()) {
            modelAndView.setViewName("redirect:/students/create");
            return modelAndView;
        }
        Optional<ContractDto> contractDto = contractService.findByPhone(phone);
        if (contractDto.isPresent()) {
            modelAndView.addObject("contractDto", contractDto.get());
        } else {
            modelAndView.addObject("studentId", student.get().getStudentId());
            modelAndView.setViewName("redirect:/contracts/create-contract");
        }

        return modelAndView;
    }

    @GetMapping(URL_CREATE_CONTRACT)
    public ModelAndView createContract(ModelAndView modelAndView, @ModelAttribute("studentId") long studentId) {
        List<CourseDto> courseDtoList = courseService.getAllDto();
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("coursesDto", courseDtoList);
        return modelAndView;
    }

    @PostMapping(URL_CREATE_CONTRACT)
    public ModelAndView createContract(ModelAndView modelAndView, @ModelAttribute("studentId") long studentId,
                                       @ModelAttribute("courseId") long courseId) {
        CreateContractDto createContractDto = new CreateContractDto();
        createContractDto.setCourseId(courseId);
        createContractDto.setStudentId(studentId);
        Optional<Long> contractId = contractService.createContract(createContractDto);
        if (contractId.isPresent()) {
            modelAndView.addObject(SUCCESS_MESSAGES_ATTRIBUTE_NAME, "Contract was created");
        } else {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, "Contract was not created.");
        }
        modelAndView.setViewName("/home");
        return modelAndView;
    }


}
