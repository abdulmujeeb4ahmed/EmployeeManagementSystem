import java.util.Date;

public class PayStatement {
    private Date payDate;
    private double amount;

    public PayStatement(Date payDate, double amount) {
        this.payDate = payDate;
        this.amount = amount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public double getAmount() {
        return amount;
    }
}