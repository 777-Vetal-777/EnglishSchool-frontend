package ua.englishschool.frontend.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.englishschool.frontend.entity.Course;
import ua.englishschool.frontend.entity.PeriodDate;
import ua.englishschool.frontend.entity.PeriodTime;
import ua.englishschool.frontend.entity.dto.CourseDto;
import ua.englishschool.frontend.model.service.CourseService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/courses")
public class CourseController {

    private static final String URL_ADD_COURSE = "/create-course";

    private static final String URL_GET_ALL = "/get-all";

    private static final String URL_GET_ALL_ACTIVE = "/get-all-active";

    private static final String URL_GET_ALL_WAIT = "/get-all-wait";

    private static final String ERROR_MESSAGES_ATTRIBUTE_NAME = "errorMessage";

    private static final String SUCCESS_MESSAGES_ATTRIBUTE_NAME = "successMessage";


    @Autowired
    private CourseService courseService;


    @GetMapping(URL_ADD_COURSE)
    public ModelAndView createCourse(ModelAndView modelAndView) {
        return modelAndView;
    }


    @PostMapping(URL_ADD_COURSE)
    public ModelAndView createCourse(ModelAndView modelAndView, String name, String maxCapacity, String price,
                                     @ModelAttribute("startDate") String startDate, @ModelAttribute("endDate") String endDate,
                                     @ModelAttribute("startTime") String startTime, @ModelAttribute("endTime") String endTime) {
        PeriodDate periodDate = new PeriodDate();
        periodDate.setStartDate(LocalDate.parse(startDate));
        periodDate.setEndDate(LocalDate.parse(endDate));

        PeriodTime periodTime = new PeriodTime();
        periodTime.setStartTime(LocalTime.parse(startTime));
        periodTime.setEndTime(LocalTime.parse(endTime));
        Course course = createCourse(periodTime, periodDate, name, maxCapacity, price);

        Optional<Long> id = courseService.create(course);

        if (id.isEmpty()) {
            modelAndView.addObject(ERROR_MESSAGES_ATTRIBUTE_NAME, "Not Free Teachers or you should activate teacher");
        } else {
            modelAndView.addObject(SUCCESS_MESSAGES_ATTRIBUTE_NAME, "Course was created");
        }
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @GetMapping(URL_GET_ALL)
    public ModelAndView getAll(ModelAndView modelAndView) {
        List<CourseDto> courseDtoList = courseService.getAllDto();
        modelAndView.addObject("coursesDto", courseDtoList);
        return modelAndView;
    }

    @GetMapping(URL_GET_ALL_ACTIVE)
    public ModelAndView getAllActive(ModelAndView modelAndView) {
        Set<CourseDto> courseDtoList = courseService.getAllActive();
        modelAndView.addObject("coursesDto", courseDtoList);
        return modelAndView;
    }

    @GetMapping(URL_GET_ALL_WAIT)
    public ModelAndView getAllWait(ModelAndView modelAndView) {
        List<CourseDto> courseDtoList = courseService.getAllWait();
        modelAndView.addObject("coursesDto", courseDtoList);
        return modelAndView;
    }

    private Course createCourse(PeriodTime periodTime, PeriodDate periodDate, String name, String maxCapacity, String price) {
        Course course = new Course();
        course.setName(name);
        course.setMaxCapacity(Integer.valueOf(maxCapacity));
        course.setPrice(Integer.valueOf(price));
        course.setPeriodDate(periodDate);
        course.setPeriodTime(periodTime);
        return course;
    }
}
