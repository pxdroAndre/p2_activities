public class Individual extends TaxPayer
{
    private double HealthExpenditures;

    @Override
    void tax()
    {
        double tax;
        if (getAnnualIncome() <= 20000.00)
        {
            tax = (this.getAnnualIncome() * 0.15) - (this.getHealthExpenditures() * 0.5);
        }
        else
        {
            tax = (this.getAnnualIncome() * 0.25) - (this.getHealthExpenditures() * 0.5);
        }
        this.setTaxes(tax);
    }

    public double getHealthExpenditures() {
        return HealthExpenditures;
    }

    public void setHealthExpenditures(double healthExpenditures) {
        HealthExpenditures = healthExpenditures;
    }
}
