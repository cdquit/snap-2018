/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author jmm4115
 */
public class Snap {
    private int playerTurn;
    public final int PLAYER_1 = 1;
    public final int PLAYER_2 = 2;
    private Deck player1Deck;
    private Deck player2Deck;
    private ArrayList<Card> pile;
    
    public Snap()
    {
        player1Deck = new Deck(true);
        player2Deck = new Deck(false);
        setupPlayerDecks();
        pile = new ArrayList<>();
        playerTurn = PLAYER_1;
    }
    
    private void setupPlayerDecks()
    {
        for (int k = 0; k < 26; k++)
        {
            player1Deck.shuffle();
            player2Deck.placeCard(player1Deck.drawCard());
        }
    }
    
    private void pickupPile(int player)
    {
        if (player == PLAYER_1)
        {
            while (!pile.isEmpty())
            {
                player1Deck.placeCard(pile.remove(0)); //keep removing index 0 from pile and the remainings will shift left.
            }
            
            player1Deck.shuffle();
        }
        else
        {
            while (!pile.isEmpty())
            {
                player2Deck.placeCard(pile.remove(0));
            }
            
            player2Deck.shuffle();
        }
    }
    
    private boolean checkSnap()
    {
        return (pile.get(pile.size() - 1).getValue() == pile.get(pile.size() - 2).getValue());
    }
    
    public boolean snap(int player)
    {
        if (checkSnap())
        {
            pickupPile(player);
            return true;
        }
        else
        {            
            if (player == PLAYER_1)
                pickupPile(PLAYER_2);
            else
                pickupPile(PLAYER_1);
            
            return false;
        }
    }
    
    public Card drawCard(int player)
    {
        playerTurn = (player == PLAYER_1) ? PLAYER_2 : PLAYER_1;
        Card drawnCard = (player == PLAYER_1) ? player1Deck.drawCard() : player2Deck.drawCard();
        pile.add(drawnCard);
        return drawnCard;
    }
    
    public boolean hasGameFinished() //if one player has no cards left, and the other player who has cards will need to draw one more turn.
    {     
        if (!player1Deck.hasCardsRemaining())
        {
//            When player 1 has no cards left. Player 2 draws one more turn before game ends. Or vice versa.
//            pile.isEmpty is added because the game should also end when the pile is empty and either one player has no cards left - there won't be enough cards to snap.
            if (player2Deck.hasCardsRemaining() && getPlayerTurn() == PLAYER_2 && !pile.isEmpty()) 
            {
                return false;
            }
        }
        
        if (!player2Deck.hasCardsRemaining())
        {
            if (player1Deck.hasCardsRemaining() && getPlayerTurn() == PLAYER_1 && !pile.isEmpty())
            {
                return false;
            }
        }
        
        return !(player1Deck.hasCardsRemaining() && player2Deck.hasCardsRemaining());
    }
    
    public boolean isWinner(int player) //will only call this when game finishes, check if opponent still has cards
    {
        if (player == PLAYER_1)
        {
            if (player1Deck.hasCardsRemaining())
            {
                return !player2Deck.hasCardsRemaining();
            }
        }
        else
        {
            if (player2Deck.hasCardsRemaining())
            {
                return !player1Deck.hasCardsRemaining();
            }
        }
        
        return false;
    }
    
    public int getPlayerTurn()
    {
        return playerTurn;
    }
    
    public int getPlayerCardsRemaining(int player)
    {
        return (player == PLAYER_1) ? player1Deck.getDeckSize() : player2Deck.getDeckSize();
    }
    
    public static void main(String[] args)
    {
        System.out.println("-------------------------SNAP!!!------------------------");
        System.out.println("KEYS: Player 1 draw: [ENTER], Player 1 snap: P->[ENTER]");
        System.out.println("KEYS: Player 2 draw: [ENTER], Player 2 snap: Q->[ENTER]");
        System.out.println("-------------------------GO!!!!-------------------------");
        
        Snap game = new Snap();
        Scanner scan = new Scanner(System.in);
        String playerInput = " ";
        boolean playing = true; //additional variable to control the loop and/or scanner.
        boolean finalRound = true; //additional variable to control the loop and/or scanner.
        
        while (!game.hasGameFinished() || !playerInput.isEmpty())
        {
            if (playing)
            {
                playerInput = scan.nextLine();
                finalRound = true;
            }
            
            if (playerInput.isEmpty())
            {
                Card drawnCard = game.drawCard(game.getPlayerTurn());

                if (game.getPlayerTurn() == game.PLAYER_1)
                {
                    System.out.print("Player 2 ");
                }
                else
                {
                    System.out.print("Player 1 ");
                }

                System.out.print("draws [" + drawnCard + "]  ");
            }
            
            if (playerInput.equalsIgnoreCase("P"))
            {
                if (game.snap(game.PLAYER_1))
                {
                    System.out.println("Player 1 SNAP!!!");
                }
                else
                {
                    System.out.println("False SNAP! Penalty against player 1");
                }
                
                playing = true;
            }
            
            if (playerInput.equalsIgnoreCase("Q"))
            {
                if (game.snap(game.PLAYER_2))
                {
                    System.out.println("Player 2 SNAP!!!");
                }
                else
                {
                    System.out.println("False SNAP! Penalty against player 2");
                }
                
                playing = true;
            }
            
            System.out.println("REMAINING CARDS P1:" + game.getPlayerCardsRemaining(game.PLAYER_1) + " P2:" + game.getPlayerCardsRemaining(game.PLAYER_2));
            
            if (finalRound == false)
                if (game.hasGameFinished())
                    System.out.println();
            
            if (game.hasGameFinished())
            {
                playerInput = "";
                if (finalRound)
                {
                    playerInput = scan.nextLine(); //redundant - but this is all I can think of to allow input at the end of the game to allow one final chance of snap to get back in the game, otherwise the loops breaks and game ends without prompting for input.
                    finalRound = false;
                }
                playing = false;
            }
        }
        
        System.out.println("Game Over");
        System.out.print(game.isWinner(game.PLAYER_1) ? "Player ONE" : game.isWinner(game.PLAYER_2) ? "Player TWO" : "No one");
        System.out.println(" WINS");
    }
}
