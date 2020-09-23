package ua.englishschool.frontend.model.service;

import ua.englishschool.frontend.entity.Teacher;
import ua.englishschool.frontend.entity.dto.TeacherDto;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    Optional<TeacherDto> findByPhone(String phone);

    List<TeacherDto> getAllDto();

    boolean changeStatusActive(long teacherId);

    Optional<Long> create(Teacher teacher);

    List<TeacherDto> getAllByActive(boolean active);
}
