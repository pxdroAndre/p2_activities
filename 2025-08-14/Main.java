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

        for (int i = 1; i <= counter; i++)
        {
            System.out.println("Tax payer #" + i + " data:");
            System.out.print("Individual or company? (i/c) ");
            String ans = scan.nextLine();
            List <TaxPayer> taxPayerList = new ArrayList<TaxPayer>();

            if (Objects.equals(ans, "i"))
            {
                Individual person = new Individual();
                String name = scan.nextLine();
                double income = scan.nextDouble();
                double expenditures = scan.nextDouble();
                person.setName(name);
                person.setAnnualIncome(income);
                person.setHealthExpenditures(expenditures);
                person.tax();
                taxPayerList.add(person);

            }
            else
            {
                Company company = new Company();
                String name = scan.nextLine();
                double income = scan.nextDouble();
                int employees = scan.nextInt();
                company.setName(name);
                company.setAnnualIncome(income);
                company.setNumEmployees(employees);
                company.tax();
                taxPayerList.add(company);
            }
        }

    }
}
