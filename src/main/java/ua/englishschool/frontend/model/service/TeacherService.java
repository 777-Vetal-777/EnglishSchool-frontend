package ua.englishschool.frontend.model.service;

import ua.englishschool.frontend.entity.Teacher;
import ua.englishschool.frontend.entity.dto.TeacherDto;

import java.util.List;
import java.util.Optional;

public interface TeacherService extends GenericService<Teacher> {

    Optional<TeacherDto> findByPhone(String phone);

    List<TeacherDto> getAllDto();

    boolean changeStatusActive(long teacherId);

}
