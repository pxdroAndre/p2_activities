public class Product
{
    // Object attributes
    String name;
    double price;
    int quantity;
    
    // constructor, I used parameters because I read the values before creating the object
    public Product(String name, double price, int quantity)
    {
        //I used "this" because the parameters have the same name of the attributes
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public void addProducts (int qtt)
    {
        this.quantity = quantity + qtt;
    }
    
    public double totalValueInStock ()
    {
        return (double) this.quantity * this.price;
    }

    public void removeProducts (int qtt)
    {
        this.quantity = quantity - qtt;
    }
}
