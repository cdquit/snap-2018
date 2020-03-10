/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.util.Random;
/**
 *
 * @author jmm4115
 */
public class Card implements Comparable<Card>{
    private int value;
    private int suit;
    private final Random random = new Random();
    
    public Card(int value, int suit)
    {
        this.value = random.nextInt(13) + 1;
        this.suit = random.nextInt(4) + 1;
        
        if (value >= 1 && value <= 13)
        {
            this.value = value;
        }
        
        if (suit >= 1 && suit <= 4)
        {
            this.suit = suit;
        }
    }
    
    public int getValue()
    {
        return value;
    }
    
    public int getSuit()
    {
        return suit;
    }
    
    public String toString()
    {
        String myCard;
        
        switch (value)
        {
            case 1:
                myCard = "A";
                break;
            case 11:
                myCard = "J";
                break;
            case 12:
                myCard = "Q";
                break;
            case 13:
                myCard = "K";
                break;
            default: // all other cases between 2 - 10
                myCard = "" + value;
                break;
        }
        
        switch (suit)
        {
            case 1:
                myCard = myCard + "S";
                break;
            case 2:
                myCard = myCard + "C";
                break;
            case 3:
                myCard = myCard + "D";
                break;
            case 4:
                myCard = myCard + "H";
                break;
        }
        
        return myCard;
    }
    
    @Override
    public int compareTo(Card other)
    {
        if (value == other.value)
        {
            return suit - other.suit;
        }
        
        return value - other.value;
    }
}
