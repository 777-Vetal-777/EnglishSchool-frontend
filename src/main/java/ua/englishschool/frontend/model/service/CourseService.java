package ua.englishschool.frontend.model.service;

import ua.englishschool.frontend.entity.Course;
import ua.englishschool.frontend.entity.dto.CourseDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService {

    List<CourseDto> getAllDto();

    Set<CourseDto> getAllActive();

    List<CourseDto> getAllWait();

    Optional<Long> create(Course course);
}
