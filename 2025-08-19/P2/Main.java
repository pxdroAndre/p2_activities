import java.util.Scanner;

public class Main
{


    public static void main (String[] args)
    {
        int numAccount;
        double initialBalance;
        double limit;
        String holder;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter account data");

        System.out.print("Number: ");
        numAccount = sc.nextInt();

        System.out.print("Holder: ");
        holder = sc.nextLine();

        System.out.print("Intial balance: ");
        initialBalance = sc.nextDouble();

        System.out.print("Withdraw limit: ");
        limit = sc.nextDouble();

        Account account = new Account(holder, numAccount, initialBalance, limit);

        try
        {
            System.out.print("Enter amount for withdraw: ");
            sc.nextDouble();
        }
        catch (Exception e)
        {

        }
        finally
        {

        }

    }



}
