import java.util.Scanner;

public abstract class TaxPayer
{
    private String name;
    private double annualIncome;
    private double taxes;

    abstract void tax();

    public final String getName() {
        return name;
    }
    public final void setName(String name) {
        this.name = name;
    }

    public final double getAnnualIncome() {
        return annualIncome;
    }

    public final void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public final double getTaxes() {
        return taxes;
    }

    public final void setTaxes(double taxes) {
        this.taxes = taxes;
    }

}
