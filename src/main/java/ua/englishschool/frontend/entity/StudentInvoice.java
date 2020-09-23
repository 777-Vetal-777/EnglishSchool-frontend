package ua.englishschool.frontend.entity;


import ua.englishschool.frontend.entity.core.StudentInvoiceType;

import java.sql.Timestamp;
import java.util.Objects;

public class StudentInvoice {

    private long id;

    private PeriodDate period;

    private Timestamp paymentDate;

    private int money;

    private boolean payed;

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

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
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
                payed == that.payed &&
                Objects.equals(period, that.period) &&
                Objects.equals(paymentDate, that.paymentDate) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, period, paymentDate, money, payed, type);
    }

    @Override
    public String toString() {
        return "StudentInvoice{" +
                "id=" + id +
                ", period=" + period +
                ", paymentDate=" + paymentDate +
                ", money=" + money +
                ", payed=" + payed +
                ", type=" + type +
                '}';
    }
}
