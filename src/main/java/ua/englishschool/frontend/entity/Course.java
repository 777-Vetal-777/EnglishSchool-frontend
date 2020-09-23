package ua.englishschool.frontend.entity;

import com.sun.istack.NotNull;

import java.util.Objects;

public class Course {

    private long id;

    private String name;

    private PeriodDate periodDate;

    private PeriodTime periodTime;

    private Teacher teacher;

    private int price;

    private int maxCapacity;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PeriodDate getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(PeriodDate periodDate) {
        this.periodDate = periodDate;
    }

    public PeriodTime getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(PeriodTime periodTime) {
        this.periodTime = periodTime;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                price == course.price &&
                maxCapacity == course.maxCapacity &&
                Objects.equals(name, course.name) &&
                Objects.equals(periodDate, course.periodDate) &&
                Objects.equals(periodTime, course.periodTime) &&
                Objects.equals(teacher, course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, periodDate, periodTime, teacher, price, maxCapacity);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", periodDate=" + periodDate +
                ", periodTime=" + periodTime +
                ", teacher=" + teacher +
                ", price=" + price +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
