import java.util.Scanner;

public class Main
{
	public static void main(String[] args) 
	{
	    Scanner scanf = new Scanner(System.in);
	    System.out.print("Enter the number of tax payers: ");
	    int counter = scanf.nextInt();
	    
	    for (int i = 1; i <= counter; i++)
	    {
	        System.out.println("Tax payer #" + i + " data:");
	        System.out.print("Individual or company? (i/c) ");
	        String ans = scan.nextLine();
	        if (ans == "i")
	        {
	            Individual person = new Individual();
	            person.getData(scan);
	        }
	    }
	    
	}
}
