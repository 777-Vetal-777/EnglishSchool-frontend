package ua.englishschool.frontend.entity;

import ua.englishschool.frontend.entity.core.ContractStatusType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contract {

    private long id;

    private Student student;

    private Course course;

    private ContractStatusType contractStatusType;

    private List<StudentInvoice> studentInvoices = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ContractStatusType getContractStatusType() {
        return contractStatusType;
    }

    public void setContractStatusType(ContractStatusType contractStatusType) {
        this.contractStatusType = contractStatusType;
    }

    public List<StudentInvoice> getStudentInvoices() {
        return studentInvoices;
    }

    public void setStudentInvoices(List<StudentInvoice> studentInvoices) {
        this.studentInvoices = studentInvoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id == contract.id &&
                Objects.equals(student, contract.student) &&
                Objects.equals(course, contract.course) &&
                contractStatusType == contract.contractStatusType &&
                Objects.equals(studentInvoices, contract.studentInvoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, course, contractStatusType, studentInvoices);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", contractStatusType=" + contractStatusType +
                ", studentInvoices=" + studentInvoices +
                '}';
    }
}
