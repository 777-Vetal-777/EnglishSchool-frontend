package ua.englishschool.frontend.model.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.englishschool.frontend.entity.Teacher;
import ua.englishschool.frontend.entity.dto.StudentDto;
import ua.englishschool.frontend.entity.dto.TeacherDto;
import ua.englishschool.frontend.model.service.TeacherService;

import java.net.URL;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private static final String URL_CREATE_TEACHER = "/create";

    private static final String URL_FIND_TEACHER_BY_PHONE = "/find-by-phone";

    private static final String URL_FIND_ALL_TEACHER_BY_PHONE = "/get-all";

    private static final String URL_ACTIVATE = "/change-active";

    private static final String ERROR_MESSAGES_ATTRIBUTE_NAME = "errorMessage";

    private static final String SUCCESS_MESSAGES_ATTRIBUTE_NAME = "successMessage";

    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping(URL_CREATE_TEACHER)
    public ModelAndView create(ModelAndView modelAndView) {
        modelAndView.addObject("teacher", new Teacher());
        return modelAndView;
    }

    @PostMapping(URL_CREATE_TEACHER)
    public ModelAndView create(@ModelAttribute("teacher") Teacher teacher, ModelAndView modelAndView) {
        Optional<Long> id = teacherService.create(teacher);
        if (id.isEmpty()) {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, "Teacher was not added, find by phone");
        } else {
            modelAndView.addObject(SUCCESS_MESSAGES_ATTRIBUTE_NAME, "Teacher was added");
        }
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @GetMapping(URL_FIND_TEACHER_BY_PHONE)
    public ModelAndView findByPhone(ModelAndView modelAndView) {
        return modelAndView;
    }

    @PostMapping(URL_FIND_TEACHER_BY_PHONE)
    public ModelAndView findByPhone(ModelAndView modelAndView, @ModelAttribute("phone") String phone) {
        Optional<TeacherDto> teacherDto = teacherService.findByPhone(phone);
        if (teacherDto.isEmpty()) {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, String.format("Teacher with number %s was not found.", phone));
            modelAndView.setViewName("/home");
        } else {
            modelAndView.addObject("teacherDto", teacherDto.get());
        }
        return modelAndView;
    }

    @GetMapping(URL_FIND_ALL_TEACHER_BY_PHONE)
    public ModelAndView getAll(ModelAndView modelAndView) {
        modelAndView.addObject("teachersDto", teacherService.getAllDto());
        return modelAndView;
    }

    @PostMapping(URL_ACTIVATE)
    public ModelAndView activate(ModelAndView modelAndView, long teacherId) {
        boolean activateTeacher = teacherService.changeStatusActive(teacherId);
        if (activateTeacher) {
            modelAndView.setViewName("redirect:/teachers/get-all");
        } else {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, "Failed to change status");
            modelAndView.setViewName("/home");
        }
        return modelAndView;
    }


}
