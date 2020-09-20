package ua.englishschool.frontend.model.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.englishschool.frontend.entity.Student;
import ua.englishschool.frontend.entity.dto.StudentDto;
import ua.englishschool.frontend.model.service.RestHandler;
import ua.englishschool.frontend.model.service.StudentService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentRestServiceImpl implements StudentService {

    private static final String URL_STUDENTS = "/students";

    private static final String URL_FIND_STUDENT_DTO_BY_PHONE = "/students/dto/";

    private static final String URL_GET_ALL_STUDENTS_DTO = "/students/get-all-dto";

    private static final String URL_GET_ACTIVE_STUDENTS_DTO = "/students/active-students-dto";

    private static final String RESPONSE_ERROR_MESSAGE = "Response is null or has no body.";


    @Autowired
    private Logger logger;

    @Autowired
    private RestHandler restHandler;


    @Override
    public Optional<Long> create(Student student) {
        logger.debug("Create Student has been executed for : " + student.toString());
        Optional<ResponseEntity> responseEntity = restHandler.doPost(URL_STUDENTS, student);

        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Create student is failed." + RESPONSE_ERROR_MESSAGE);
            return Optional.empty();
        }

        Long studentId = Long.valueOf(responseEntity.get().getBody().toString());
        logger.debug("Create Student is finished successfully " + student.toString());
        return Optional.ofNullable(studentId);
    }

    @Override
    public Optional<StudentDto> findByPhone(String phone) {
        logger.debug("Find Student by phone has been executed :" + phone);
        String URL = URL_FIND_STUDENT_DTO_BY_PHONE + phone;
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Find student by phone is failed. " + RESPONSE_ERROR_MESSAGE);
            return Optional.empty();
        }
        Optional<StudentDto> studentDto = getStudentDtoFromResponseBody(responseEntity.get());
        logger.debug("Find student by phone is finished successfully. For student: " + studentDto.get().toString());
        return studentDto;
    }

    @Override
    public List<StudentDto> getAllStudentsDto() {
        logger.debug("Get all StudentsDto has been executed");
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL_GET_ALL_STUDENTS_DTO);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get all StudentsDto is failed. " + RESPONSE_ERROR_MESSAGE);
            return Collections.emptyList();
        }
        List<StudentDto> listStudentsDto = getListStudentDtoFromResponseBody(responseEntity.get());
        logger.debug("Get all StudentsDto  is finished successfully.");
        return listStudentsDto;
    }

    @Override
    public List<StudentDto> getAllActiveStudentsDto() {
        logger.debug("Get active StudentsDto has been executed");
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL_GET_ACTIVE_STUDENTS_DTO);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get active StudentsDto is failed. " + RESPONSE_ERROR_MESSAGE);
            return Collections.emptyList();
        }
        List<StudentDto> listStudentsDto = getListStudentDtoFromResponseBody(responseEntity.get());
        logger.debug("Get active StudentsDto  is finished successfully.");
        return listStudentsDto;
    }

    @Override
    public boolean update(Student student) {
        return false;
    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public Optional<Student> getById(long id) {
        logger.debug("Get student by id has been executed for id: " + id);
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean isExists(long id) {
        return false;
    }

    private Optional<Student> getStudentFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        Student student;
        try {
            student = mapper.readValue(body, Student.class);
        } catch (JsonProcessingException e) {
            logger.error("Get Student from ResponseBody is failed " + e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(student);
    }

    private Optional<StudentDto> getStudentDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        StudentDto studentDto;
        try {
            studentDto = mapper.readValue(body, StudentDto.class);
        } catch (JsonProcessingException e) {
            logger.error("Get Student from ResponseBody is failed " + e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(studentDto);
    }

    private List<StudentDto> getListStudentDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        List<StudentDto> studentsDto;
        try {
            studentsDto = mapper.readValue(body, new TypeReference<List<StudentDto>>() {
            });
        } catch (IOException e) {
            logger.error(String.format("Get list of studentsDto from body failed. '%s' ", e.getMessage()));
            return Collections.emptyList();
        }
        return studentsDto;
    }
}
