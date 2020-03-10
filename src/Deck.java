/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.util.Arrays;
import java.util.Random;
/**
 *
 * @author jmm4115
 */
public class Deck {
    private Card[] deck;
    private int deckSize;
    public final int MAX_SIZE = 52;
    
    public Deck()
    {
        this(true); //initialise using the constructor below with parameter "true".
    }
    
    public Deck(boolean fullDeck)
    {
        deck = new Card[MAX_SIZE];
        
        if (fullDeck)
        {
            deckSize = MAX_SIZE;
            initialiseFullDeck();
        }
        else
        {
            deckSize = 0;
        }
    }
    
    private void initialiseFullDeck()
    {
        int k = 0;
        
        for (int i = 1; i <= 13; i++)
        {
            for (int j = 1; j <= 4; j++)
            {
               deck[k] = new Card(i, j);
               k++;
            }
        }
    }
    
    public Card drawCard()
    {
        deckSize--;
        Card drawnCard = deck[getDeckSize()];
        deck[getDeckSize()] = null;
        
        return drawnCard;
    }
    
    public void placeCard(Card card)
    {
        deck[getDeckSize()] = card;
        deckSize++;
    }
    
//    Shuffling the deck starting from index 0, and work the way up. deck[0] can swap with deck[0] and above.
//    deck[1] can swap with deck[1] and above but not deck[0].
    public void shuffle()
    {
        int size = getDeckSize();
        Card temp;
        Random randomIndex = new Random();
        
        for (int k = 0; k < size; k++)
        {
            int indexToSwap = k + randomIndex.nextInt(size - k);
            temp = deck[k];
            deck[k] = deck[indexToSwap];
            deck[indexToSwap] = temp;
        }
    }
    
    public int getDeckSize()
    {
        return deckSize;
    }
    
    public boolean hasCardsRemaining()
    {
        return getDeckSize() > 0;
    }
    
    public String toString()
    {
        return Arrays.toString(deck);
    }
}
