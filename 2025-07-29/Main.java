
import java.util.Scanner;

public class Main
{
	public static void printInfo(Product prod)
	{
		System.out.printf("Product data: %s, %d units, Total: $%.2f\n", prod.name, prod.quantity, prod.totalValueInStock());
	}

	public static void main(String[] args)
	{

		System.out.println("Enter the product data");
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter the product name:");
		String name = scan.nextLine();

		System.out.print("Enter the product price: $");
		double price = scan.nextDouble();

		System.out.print("Enter the product quantity:");
		int qtt = scan.nextInt();

		Product prod = new Product(name, price, qtt);
    
  
        printInfo(prod);

		System.out.print("Enter the amount of items that you want to add: ");
		prod.addProducts((qtt = scan.nextInt()));
		
		printInfo(prod);
	
		System.out.print("Enter the amount of items that you want to remove: ");
		prod.removeProducts((qtt = scan.nextInt()));
		
		printInfo(prod);
	}
}
 

