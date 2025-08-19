public class Company extends TaxPayer
{
    private int numEmployees;

    @Override
    void tax()
    {
        double tax;
        if (this.getNumEmployees() <= 10)
        {
            tax = (this.getAnnualIncome() * 0.16);
        }
        else
        {
            tax = (this.getAnnualIncome() * 0.14);
        }
        this.setTaxes(tax);
    }

    public int getNumEmployees() {
        return numEmployees;
    }

    public void setNumEmployees(int numEmployees) {
        this.numEmployees = numEmployees;
    }


}
