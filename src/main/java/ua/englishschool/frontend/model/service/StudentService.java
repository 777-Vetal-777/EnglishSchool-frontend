package ua.englishschool.frontend.model.service;

import ua.englishschool.frontend.entity.Student;
import ua.englishschool.frontend.entity.dto.StudentDto;

import java.util.List;
import java.util.Optional;

public interface StudentService extends GenericService<Student> {

    Optional<StudentDto> findByPhone(String phone);

    List<StudentDto> getAllStudentsDto();

    List<StudentDto> getAllActiveStudentsDto();
}
