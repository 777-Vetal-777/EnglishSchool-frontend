package ua.englishschool.frontend.model.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.englishschool.frontend.entity.Course;
import ua.englishschool.frontend.entity.dto.CourseDto;
import ua.englishschool.frontend.entity.dto.StudentDto;
import ua.englishschool.frontend.model.service.CourseService;
import ua.englishschool.frontend.model.service.RestHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class CourseRestServiceImpl implements CourseService {

    private static final String RESPONSE_ERROR_MESSAGE = "Response is null or has no body.";

    private static final String URL = "/courses";

    private static final String URL_GET_ALL_COURSES_DTO = URL + "/get-all/dto";

    private static final String URL_GET_ALL_ACTIVE_COURSES_DTO = URL + "/get-all/active/dto";

    private static final String URL_GET_ALL_WAIT_COURSES_DTO = URL + "/get-all/wait/dto";

    private Logger logger;

    private RestHandler restHandler;

    @Autowired
    public CourseRestServiceImpl(Logger logger, RestHandler restHandler) {
        this.logger = logger;
        this.restHandler = restHandler;
    }

    @Override
    public Optional<Long> create(Course course) {
        logger.debug("Create Student has been executed for : " + course.toString());
        Optional<ResponseEntity> responseEntity = restHandler.doPost(URL, course);

        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Create student is failed." + RESPONSE_ERROR_MESSAGE);
            return Optional.empty();
        }

        Long studentId = Long.valueOf(responseEntity.get().getBody().toString());
        logger.debug("Create Course is finished successfully " + course.toString());
        return Optional.ofNullable(studentId);
    }

    @Override
    public List<CourseDto> getAllDto() {
        logger.debug("Get all CoursesDto has been executed");
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL_GET_ALL_COURSES_DTO);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get all CoursesDto is failed. " + RESPONSE_ERROR_MESSAGE);
            return Collections.emptyList();
        }
        List<CourseDto> listCoursesDto = getListCoursesDtoFromResponseBody(responseEntity.get());
        logger.debug("Get all CoursesDto  is finished successfully.");
        return listCoursesDto;
    }

    @Override
    public Set<CourseDto> getAllActive() {
        logger.debug("Get all active CoursesDto has been executed");
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL_GET_ALL_ACTIVE_COURSES_DTO);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get all active CoursesDto if failed. " + RESPONSE_ERROR_MESSAGE);
            return Collections.emptySet();
        }
        Set<CourseDto> setCoursesDto = getSetCoursesDtoFromResponseBody(responseEntity.get());
        logger.debug("Get all active CoursesDto is finished successfully.");
        return setCoursesDto;
    }

    @Override
    public List<CourseDto> getAllWait() {
        logger.debug("Get all wait CoursesDto has been executed");
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL_GET_ALL_WAIT_COURSES_DTO);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get all wait CoursesDto if failed. " + RESPONSE_ERROR_MESSAGE);
            return Collections.emptyList();
        }
        List<CourseDto> listCoursesDto = getListCoursesDtoFromResponseBody(responseEntity.get());
        logger.debug("Get all wait CoursesDto is finished successfully.");
        return listCoursesDto;
    }


    private List<CourseDto> getListCoursesDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        List<CourseDto> coursesDto;
        try {
            coursesDto = mapper.readValue(body, new TypeReference<List<CourseDto>>() {
            });
        } catch (IOException e) {
            logger.error(String.format("Get list of CoursesDto from body failed. '%s' ", e.getMessage()));
            return Collections.emptyList();
        }
        return coursesDto;
    }

    private Set<CourseDto> getSetCoursesDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        Set<CourseDto> coursesDto;
        try {
            coursesDto = mapper.readValue(body, new TypeReference<Set<CourseDto>>() {
            });
        } catch (IOException e) {
            logger.error(String.format("Get set of CoursesDto from body failed. '%s' ", e.getMessage()));
            return Collections.emptySet();
        }
        return coursesDto;
    }

}
