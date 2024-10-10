import java.util.Random;
import java.util.ArrayList;
import java.util.List;

// Things left to add: 
//  - Add the ability for the user to play themselves
//  - Add the ability to split
//  - Add the ability to double down
//  - Add money to the table
//  - Add multiple people to the table

public class BlackJack {

    public static int getCard() {
        Random rand = new Random();
        int card = rand.nextInt(13) + 1;
        return card;
    }

    public static int getSuit() {
        Random rand = new Random();
        int suit = rand.nextInt(4);
        return suit;
    }

    public static int getNewCard(String [] suits, List<Card> playerCards, int handTotal, String person) {
        int card = getCard();
        String suit = suits[getSuit()];
        Card myCard = new Card(card, suit);

        switch (card) {
            case 1:
                // Add 1 to total
                if (handTotal + 11 > 21) {
                    handTotal += 1;
                } else {
                    handTotal += 11;
                }
                playerCards.add(myCard);
                System.out.println("\t\t\t*~~~*~~~* " + person + " card is: ACE of " + suit + " *~~~*~~~*");
                break;
            case 11:
                // Add 10 to total
                handTotal += 10;
                playerCards.add(myCard);
                System.out.println("\t\t\t*~~~*~~~* " + person + " card is: JACK of " + suit + " *~~~*~~~*");
                break;
            case 12:
                // Add 10 to total
                handTotal += 10;
                playerCards.add(myCard);
                System.out.println("\t\t\t*~~~*~~~* " + person + " card is: QUEEN of " + suit + " *~~~*~~~*");
                break;
            case 13:
                // Add 10 to total
                handTotal += 10;
                playerCards.add(myCard);
                System.out.println("\t\t\t*~~~*~~~* " + person + " card is: KING of " + suit + " *~~~*~~~*");
                break;
            default:
                playerCards.add(myCard);
                handTotal += playerCards.get(playerCards.size() - 1).getValue();
                System.out.println("\t\t\t*~~~*~~~* " + person + " card is: " + playerCards.get(playerCards.size() - 1).getValue() 
                                    + " of " + playerCards.get(playerCards.size() - 1).getSuit() + " *~~~*~~~* ");
        }
        return handTotal;
    }

    public static void main(String[] args) {
        // Card values
        String suits [] = {"CLUBS", "DIAMONDS", "HEARTS", "SPADES"};

        // Create a function to get a new card from the deck and add it to the players hands list
        List<Card> playerCards = new ArrayList<>();
        List<Card> dealerCards = new ArrayList<>();

        // Player and dealer totals and card count and bust status
        int playerHandTotal = 0;
        // int playerCardCount = 0;
        boolean playerBust = false;
        
        int dealerHandTotal = 0;
        // int dealerCardCount = 0;
        boolean dealerBust = false;
        boolean dealerFirstDeal = false;

        // Get 2 cards for the player
        while (playerHandTotal < 21) {
            
            playerHandTotal = getNewCard(suits, playerCards, playerHandTotal, "Player's");
            System.out.println("Your total is: " + playerHandTotal);
            
            if (dealerFirstDeal == false) {
                dealerHandTotal = getNewCard(suits, playerCards, dealerHandTotal, "Dealer's");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tDealer's total is: " + dealerHandTotal);
                dealerFirstDeal = true;    
            }

            if (playerHandTotal > 21) {
                System.out.println("You went bust!");
                playerBust = true;
            }

            if (playerHandTotal >= 17 || playerHandTotal == 21) {
                break;
            }            
        }
        
        // only get 2 cards for the dealer if the player didn't go bust
        if (playerBust == false) {
            while (dealerHandTotal < 17) {
                dealerHandTotal = getNewCard(suits, dealerCards, dealerHandTotal, "Dealer's");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tThe dealer's total is: " + dealerHandTotal);

                if (dealerHandTotal > 21) {
                    System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tThe dealer went bust!");
                    dealerBust = true;
                }
            }
        }

        // Determine if the player or dealer went bust
        if (playerBust == true) {
            dealerHandTotal = getNewCard(suits, playerCards, dealerHandTotal, "Dealer");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tDealer's Final Total: " + dealerHandTotal);
        }
        
        System.out.println("\t\t\t*~~~*~~~* Player's total: " + playerHandTotal + " *~~~*~~~*");
        System.out.println("\t\t\t*~~~*~~~* Dealer's total: " + dealerHandTotal + " *~~~*~~~*");

        // Determine winner
        // (x & y) | (x | z) = x & (y | z)
        if (!playerBust && (dealerBust || playerHandTotal > dealerHandTotal)) {
            System.out.println("You Win!");
        }
        // (x & !y) | (z & !y & !x)
        else if ((playerBust && !dealerBust) || (playerHandTotal < dealerHandTotal && !dealerBust && !playerBust)) {
            System.out.println("Dealer Wins!");
        }
        else {
            System.out.println("Tie!");
        }
    
    }
}
