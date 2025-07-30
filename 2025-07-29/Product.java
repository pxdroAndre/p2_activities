public class Product
{
    // Object attributes
    String Name;
    double price;
    int quantity;
    
    // constructor, I used parameters to assure that values will come right(I guess)
    public Product(String Name, double price, int quantity)
    {
        //I used "this" because the parameters have the same name of the attributes
        this.Name = Name;
        this.price = price;
        this.quantity = quantity;
    }
    
    
    
}
