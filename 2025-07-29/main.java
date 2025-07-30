import java.util.Scanner;

public class Main
{
	public static void main(String[] args) 
	{
	    
	    
		Scanner scan = new Scanner(System.in);
		String Name = "bla";
		double price = 20.3;
		int qtt = 4;
		Product prod = new Product(Name, price, qtt);
	
		System.out.println(prod.Name);
	}
}

