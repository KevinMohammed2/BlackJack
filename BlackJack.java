import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Things left to add: 
//  - Add the ability for the user to play themselves
//  - Add the ability to split
//  - Add the ability to double down
//  - Add money to the table
//  - Add multiple people to the table
//  - If player hits BJ on first two cards, they win unless dealer hits BJ on first two cards -> push

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

    public static void printIntro() {
        System.out.println("\t\t\t*~~~*~~~* Press y to play *~~~*~~~*");
        System.out.println("\t\t\t*~~~*~~~* Press q to quit *~~~*~~~*");
        System.out.println("\t\t\t*~~~*~~~* Press r to see the rules *~~~*~~~*");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        if (input.equals("y")) {
            System.out.println("Starting game...");
        }

        else if (input.equals("q")) {
            System.out.println("Thank you for playing. Goodbye!");
            System.exit(0);
        }
        else if (input.equals(("r"))) {
            System.out.println("Rules of BlackJack:");
            System.out.println("1. The goal of BlackJack is to beat the dealer's hand without going over 21.");
            System.out.println("2. Face cards are worth 10, aces are worth 1 or 11, Jacks, Queens, and Kings are worth 10.");
            System.out.println("3. The dealer will always hit until his total is at least 17 or more. Dealer will also stand on soft 17.");
            System.out.println("4. The player can choose to hit, stand, double down, or split.");
            System.out.println("   - If the player chooses to hit, they will hit until they go over 21 or choose to stand.");
            System.out.println("   - If the player chooses to stand, they will stand and go on to the next hand.");
            System.out.println("   - If the player chooses to double down, they must double their bet, but they ONLY get one card.");
            System.out.println("   - To split, the player's hand must be two cards of the same value.");
            System.out.println("5. The player can only take one card at a time.");
            System.out.println("6. The player with the highest total wins.");

            printIntro();
        }

        else {
            System.out.println("Invalid input. Please try again.");
            printIntro();
        }
    }

    public static void printWinner(int playerHandTotal, int dealerHandTotal, boolean playerBust, boolean dealerBust) {
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
    public static void main(String[] args) {
        // Card values
        String suits [] = {"CLUBS", "DIAMONDS", "HEARTS", "SPADES"};

        // Create a function to get a new card from the deck and add it to the players hands list
        List<Card> playerCards = new ArrayList<>();
        List<Card> dealerCards = new ArrayList<>();

        System.out.println("\t\tt*~~~*~~~*~~~*~~~* WELCOME TO BLACKJACK *~~~*~~~*~~~*~~~*");
        printIntro();

        // Player and dealer totals and card count and bust status
        int playerHandTotal = 0;
        int dealerHandTotal = 0;
        boolean playerBust = false;
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
        if (!playerBust) {
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
        if (playerBust) {
            dealerHandTotal = getNewCard(suits, playerCards, dealerHandTotal, "Dealer");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tDealer's Final Total: " + dealerHandTotal);
        }
        
        printWinner(playerHandTotal, dealerHandTotal, playerBust, dealerBust);
    
    }
}
