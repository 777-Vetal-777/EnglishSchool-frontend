package ua.englishschool.frontend.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.englishschool.frontend.entity.Student;
import ua.englishschool.frontend.entity.core.RoleType;
import ua.englishschool.frontend.entity.dto.StudentDto;
import ua.englishschool.frontend.model.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String ERROR_MESSAGES_ATTRIBUTE_NAME = "errorMessage";

    private static final String SUCCESS_MESSAGES_ATTRIBUTE_NAME = "successMessage";

    private static final String URL_CREATE_STUDENT = "/create";

    private static final String URL_FIND_BY_PHONE = "/find-by-phone";

    private static final String URL_GET_ALL_STUDENTS = "/get-all";

    private static final String URL_GET_ALL_ACTIVE_STUDENTS_DTO = "/get-all-active";

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(URL_CREATE_STUDENT)
    public ModelAndView addStudent(ModelAndView modelAndView) {

        modelAndView.addObject("student", new Student());
        return modelAndView;
    }

    @PostMapping(URL_CREATE_STUDENT)
    public ModelAndView addStudent(ModelAndView modelAndView, @ModelAttribute("student") Student student) {
        student.setRole(RoleType.STUDENT);
        Optional<Long> studentId = studentService.create(student);
        if (studentId.isEmpty()) {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, "Student was not added, you can find by phone");
        } else {
            modelAndView.addObject(SUCCESS_MESSAGES_ATTRIBUTE_NAME, "Student was added");
        }
        modelAndView.setViewName("/home");
        return modelAndView;
    }


    @GetMapping(URL_FIND_BY_PHONE)
    public ModelAndView findByPhone(ModelAndView modelAndView) {

        return modelAndView;
    }

    @PostMapping(URL_FIND_BY_PHONE)
    public ModelAndView findByPhone(ModelAndView modelAndView, @ModelAttribute("phone") String phone) {
        Optional<StudentDto> student = studentService.findByPhone(phone);
        if (student.isEmpty()) {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, String.format("Student with number %s was not found.", phone));
            modelAndView.setViewName("/home");
        } else {
            modelAndView.addObject("studentDto", student.get());
        }
        return modelAndView;
    }

    @GetMapping(URL_GET_ALL_ACTIVE_STUDENTS_DTO)
    public ModelAndView getAllActive(ModelAndView modelAndView) {
        List<StudentDto> listStudentsDto = studentService.getAllActiveStudentsDto();
        modelAndView.addObject("students", listStudentsDto);
        return modelAndView;
    }

    @GetMapping(URL_GET_ALL_STUDENTS)
    public ModelAndView getAll(ModelAndView modelAndView) {
        List<StudentDto> listStudentDto = studentService.getAllStudentsDto();
        modelAndView.addObject("students", listStudentDto);
        return modelAndView;
    }
}
