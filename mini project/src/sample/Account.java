package sample;
import java.io.Serializable;
import java.util.*;
import javax.swing.*;
public class Account implements Serializable
{
    private double balance;

    /**
     * Constructor for objects of class Account
     */
    public Account(Double b)
    {
        balance = b;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
