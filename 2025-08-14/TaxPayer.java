public class TaxPayer 
{
    private String name;
    private double annualIncome;
    public double taxes;
    
    private abstract tax()
    {
        
    }
    
    public void getData (Scanner scan)
    {
        System.out.print("Name: ");
        this.name = scan.nextLine();
        System.out.print("Annual income: $");
        this.annualIncome = scan.nextDouble();
    }
}
