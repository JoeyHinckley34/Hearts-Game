/* FINAL PROJECT
   CARD CLASS */
   
public class Card{
   int suit, rank;
   String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
   String [] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

   
   public Card(){
      this.suit = 0;
      this.rank = 0;
   }
   
   public Card(int suit, int rank){
      this.suit = suit;
      this.rank = rank;
   }
   
   public String toString(){
      return (ranks[this.rank] + " of " + suits[this.suit]);
   }
   
   public int compareCard(Card that){
      if (this.suit>that.suit)
         return 1;
      if (this.suit<that.suit)
         return -1;
      if(this.rank>that.rank)
         return 1;
      if(this.rank<that.rank)
         return -1;
         
      return 0; 
   }
}
