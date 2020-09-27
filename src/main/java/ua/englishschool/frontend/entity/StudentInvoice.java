package ua.englishschool.frontend.entity;

import ua.englishschool.frontend.entity.core.StudentInvoiceType;

import java.time.LocalDate;
import java.util.Objects;

public class StudentInvoice {

    private long id;

    private PeriodDate period;

    private LocalDate paymentDate;

    private int money;

    private StudentInvoiceType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PeriodDate getPeriod() {
        return period;
    }

    public void setPeriod(PeriodDate period) {
        this.period = period;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public StudentInvoiceType getType() {
        return type;
    }

    public void setType(StudentInvoiceType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentInvoice that = (StudentInvoice) o;
        return id == that.id &&
                money == that.money &&
                Objects.equals(period, that.period) &&
                Objects.equals(paymentDate, that.paymentDate) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, period, paymentDate, money, type);
    }

    @Override
    public String toString() {
        return "StudentInvoice{" +
                "id=" + id +
                ", period=" + period +
                ", paymentDate=" + paymentDate +
                ", money=" + money +
                ", type=" + type +
                '}';
    }
}
