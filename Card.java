public class Card {
    private int value;
    private String suit;

    public int getValue() {
        return value;
    }
    
    public String getSuit() {
        return suit;
    }

    public Card(int newValue, String newSuit) {
        this.value = newValue;
        this.suit = newSuit;
    }
}