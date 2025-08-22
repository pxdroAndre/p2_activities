import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the number of tax payers: ");
        int counter = scan.nextInt();
        ArrayList <TaxPayer> taxPayerList = new ArrayList<TaxPayer>();

        for (int i = 1; i <= counter; i++)
        {
            System.out.println("Tax payer #" + i + " data:");
            System.out.print("Individual or company? (i/c) ");
            scan.nextLine();
            String ans = scan.nextLine();

            if (Objects.equals(ans, "i"))
            {
                System.out.print("Name: ");
                String name = scan.nextLine();

                System.out.print("Anual Income: ");
                double income = scan.nextDouble();

                System.out.print("Health Expenditures: ");
                double expenditures = scan.nextDouble();

                Individual person = new Individual();
                person.setName(name);
                person.setAnnualIncome(income);
                person.setHealthExpenditures(expenditures);
                person.tax();
                taxPayerList.add(person);

            }
            else
            {
                System.out.print("Name: ");
                String name = scan.nextLine();

                System.out.print("Anual Income: ");
                double income = scan.nextDouble();

                System.out.print("Number of Employees: ");
                int employees = scan.nextInt();

                Company company = new Company();
                company.setName(name);
                company.setAnnualIncome(income);
                company.setNumEmployees(employees);
                company.tax();
                taxPayerList.add(company);
            }
        }

        System.out.println("TAXES PAID:");
        double totalTaxes = 0.00;
        for (TaxPayer taxPayer : taxPayerList)
        {
            System.out.println(taxPayer.getName() + ": $" + taxPayer.getTaxes());
            totalTaxes += taxPayer.getTaxes();
        }
        System.out.println();
        System.out.println("TOTAL TAXES: $" + totalTaxes);

    }
}
