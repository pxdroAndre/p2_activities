import java.util.Scanner;

public class Main
{


	public static double multiplication (int a, double b)
	{
		return a * b;
	}
	
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
    
  
        System.out.printf("Product %s has %d in stock, and the total amount of: $%.2f\n", prod.Name, prod.quantity, multiplication(prod.quantity, prod.price));

		System.out.print("Enter the amount of items that you want to add: ");
		addProduct((qtt = scan.nextInt()));
		
		System.out.printf("Product %s has %d in stock, and the total amount of: $%.2f\n", prod.Name, prod.quantity, multiplication(prod.quantity, prod.price));
	}
}
 

