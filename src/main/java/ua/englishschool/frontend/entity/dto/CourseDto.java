package ua.englishschool.frontend.entity.dto;


import ua.englishschool.frontend.entity.Course;

import java.util.Objects;

public class CourseDto {

    private Course course;

    private int freeVacancies;

    public CourseDto(Course course, int freeVacancies) {
        this.course = course;
        this.freeVacancies = freeVacancies;
    }

    public Course getCourse() {
        return course;
    }

    public CourseDto() {
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getFreeVacancies() {
        return freeVacancies;
    }

    public void setFreeVacancies(int freeVacancies) {
        this.freeVacancies = freeVacancies;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return freeVacancies == courseDto.freeVacancies &&
                Objects.equals(course, courseDto.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, freeVacancies);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "course=" + course +
                ", availableStudents=" + freeVacancies +
                '}';
    }
}
