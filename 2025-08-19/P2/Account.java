public class Account
{
    private int number;
    private String holder;
    private double balance;
    private double withdrawLimit;

    public void withdraw (double value) throws WithdrawException
    {

        if (value > this.withdrawLimit)
        {
            throw new WithdrawException("The amount exceeds the limit");
        }
        if (value > this.balance)
        {
            throw new WithdrawException("Not enough balance");

        }

        this.balance -= value;
        System.out.println("New balance: " + this.balance);
    }


    public Account(String holder, int number, double balance, double withdrawLimit)
    {
        this.holder = holder;
        this.number = number;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }
}
