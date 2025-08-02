public class Product
{
    // Object attributes
    String Name;
    double price;
    int quantity;
    
    // constructor, I used parameters because I read the values before creating the object
    public Product(String Name, double price, int quantity)
    {
        //I used "this" because the parameters have the same name of the attributes
        this.Name = Name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public void addProduct (int qtt)
    {
        this.quantity = quantity - qtt;
    }
    
    public double totalPrice ()
    {
        return (double) quantity * price;
    }
}
