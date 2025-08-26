public class Account
{
    private int number;
    private String holder;
    private double balance;
    private double withdrawLimit;

    public void deposit(double value)
    {
        this.setBalance(value + this.getBalance());
    }

    public void withdraw (double value)
    {
        this.setBalance(value - this.getBalance());
    }


    public Account(String holder, int number, double balance, double withdrawLimit)
    {
        this.holder = holder;
        this.number = number;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }


    public double getWithdrawLimit()
    {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit)
    {
        this.withdrawLimit = withdrawLimit;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getHolder()
    {
        return holder;
    }

    public void setHolder(String holder)
    {
        this.holder = holder;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
}
