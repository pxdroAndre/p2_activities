import java.util.Scanner;

public class Main
{
	public static void main(String[] args) 
	{
	    System.out.println("Enter the product data");
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the product name:");
		String Name = scan.nextLine();
		
		System.out.print("Enter the product price: $");
		double price = scan.nextDouble();
		
		System.out.print("Enter the product quantity:");
		int qtt = scan.nextInt();
		
		Product prod = new Product(Name, price, qtt);
	
	}
}
