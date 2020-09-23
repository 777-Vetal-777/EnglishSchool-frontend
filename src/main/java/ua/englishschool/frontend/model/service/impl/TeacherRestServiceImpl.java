package ua.englishschool.frontend.model.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.englishschool.frontend.entity.Teacher;
import ua.englishschool.frontend.entity.dto.StudentDto;
import ua.englishschool.frontend.entity.dto.TeacherDto;
import ua.englishschool.frontend.model.service.RestHandler;
import ua.englishschool.frontend.model.service.TeacherService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TeacherRestServiceImpl implements TeacherService {

    private static final String RESPONSE_ERROR_MESSAGE = "Response is null or has no body.";

    private static final String URL_CREATE_TEACHER = "/teachers";

    private static final String URL_FIND_TEACHER_DTO_BY_PHONE = "/teachers/dto/by-phone/";

    private static final String URL_GET_ALL_TEACHERS_DTO = "/teachers/dto";


    private static final String URL_CHANGE_STATUS_ACTIVE = "/teachers/change-active/";

    @Autowired
    private Logger logger;

    @Autowired
    private RestHandler restHandler;


    @Override
    public Optional<Long> create(Teacher teacher) {
        logger.debug("Create teacher has been executed for : " + teacher.toString());
        Optional<ResponseEntity> responseEntity = restHandler.doPost(URL_CREATE_TEACHER, teacher);

        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Create teacher is failed." + RESPONSE_ERROR_MESSAGE);
            return Optional.empty();
        }

        Long teacherId = Long.valueOf(responseEntity.get().getBody().toString());
        logger.debug("Create teacher is finished successfully " + teacher.toString());
        return Optional.ofNullable(teacherId);

    }

    @Override
    public Optional<TeacherDto> findByPhone(String phone) {
        logger.debug("Find TeacherDto by phone has been executed :" + phone);
        String URL = URL_FIND_TEACHER_DTO_BY_PHONE + phone;
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Find TeacherDto by phone is failed. " + RESPONSE_ERROR_MESSAGE);
            return Optional.empty();
        }
        Optional<TeacherDto> teacherDto = getTeacherDtoFromResponseBody(responseEntity.get());
        logger.debug("Find TeacherDto by phone is finished successfully. For student: " + teacherDto.get().toString());
        return teacherDto;
    }

    @Override
    public List<TeacherDto> getAllDto() {
        logger.debug("Get all TeachersDto has been executed");
        Optional<ResponseEntity> responseEntity = restHandler.doGet(URL_GET_ALL_TEACHERS_DTO);
        if (responseEntity.isEmpty() || !responseEntity.get().hasBody()) {
            logger.error("Get all TeachersDto is failed. " + RESPONSE_ERROR_MESSAGE);
            return Collections.emptyList();
        }
        List<TeacherDto> listTeacherDto = getListTeacherDtoFromResponseBody(responseEntity.get());
        logger.debug("Get all TeachersDto  is finished successfully.");
        return listTeacherDto;
    }

    @Override
    public boolean changeStatusActive(long teacherId) {
        logger.debug("Change status active for teacher has been executed with id: " + teacherId);
        String URL = URL_CHANGE_STATUS_ACTIVE + teacherId;
        boolean deactivateTeacher = restHandler.doPut(URL);
        if (!deactivateTeacher) {
            logger.error("Change status active for teacher was failed for teacher with id: " + teacherId);
            return false;
        }
        logger.debug("Change status active for teacher is finished successfully for teacher with id: " + teacherId);
        return true;
    }

    @Override
    public boolean update(Teacher object) {
        return false;
    }

    @Override
    public List<Teacher> getAll() {
        return null;
    }

    @Override
    public Optional<Teacher> getById(long id) {
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

    private Optional<TeacherDto> getTeacherDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        TeacherDto teacherDto;
        try {
            teacherDto = mapper.readValue(body, TeacherDto.class);
        } catch (JsonProcessingException e) {
            logger.error("Get TeacherDto from ResponseBody is failed " + e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(teacherDto);
    }

    private List<TeacherDto> getListTeacherDtoFromResponseBody(ResponseEntity responseEntity) {
        String body = responseEntity.getBody().toString();
        ObjectMapper mapper = new ObjectMapper();
        List<TeacherDto> teacherDtos;
        try {
            teacherDtos = mapper.readValue(body, new TypeReference<List<TeacherDto>>() {
            });
        } catch (IOException e) {
            logger.error(String.format("Get list of teacherDto from body failed. '%s' ", e.getMessage()));
            return Collections.emptyList();
        }
        return teacherDtos;
    }
}
