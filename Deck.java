import java.lang.Math;
public class Deck{
   Card[] cards;
   //code to string or print deck in deck class 
   //how to shuffle 
   
   public Deck (int n){
      this.cards = new Card[n];
   }
   
   public Deck(){
      this.cards = new Card[52];
      int index = 0;
      for(int suit = 0; suit<=3; suit++){
         for(int rank = 0; rank<=12; rank++){
            cards[index] = new Card(suit, rank); 
            index++;
         }
      }
   }

   public String toString(){
      String result = "";
      for(int i = 0; i<cards.length; i++){
         result += cards[i].toString() + "\n";
      }
      return result; 
   } 
   
   public void print(){
      for(int i = 0; i<cards.length; i++){
         System.out.println(cards[i]);
      }
   }
   
   public void shuffle(){ 
      for (int i = 0; i<52; i++){
         //choose two random locations in the deck 
         int rand1 = (int) (Math.random()*51);
         //use a tempoarty vaible to switch the two values in the deck 
         Card temp = this.cards[i];
         this.cards[i] = this.cards[rand1];
         this.cards[rand1] = temp;
         
      }
      
   }
      
   public static Deck subDeck(Deck deck, int low, int high){
      Deck sub = new Deck(high-low+1);
         for(int i = 0; i<sub.cards.length; i++){
            sub.cards[i] = deck.cards[low+i]; 
         }
      return sub;
      
   }

   public void sort(){
      for (int i = 0; i<cards.length; i++){
         for (int j = 0; j<cards.length; j++){
            if(cards[i].compareCard(cards[j]) == -1){
               Card temp = this.cards[i];
               this.cards[i] = this.cards[j];
               this.cards[j] = temp;
            }
         }
      }
   }
      
   public void shuffle2(){
      Card[] n = new Card[cards.length];
      for(int i = 0; i < cards.length; i++){
         boolean filled = false;
         while(!filled){
            int index = (int)(Math.random() * (double)n.length);
            if(n[index] == null){
               n[index] = cards[i];
               filled = true;
            }
         }
      }
      cards = n;
   } 
   
        
   public Deck merge(Deck deck, Deck deck2){
   
      Deck result = new Deck(deck.cards.length+deck2.cards.length);
      //use an idex to keep track of your spots in the deck 
      
      int i = 0; 
      int j = 0; 
      for(int k = 0; k < result.cards.length; k++){
         if( i>=deck.cards.length){
            result.cards[k] = deck2.cards[j];
            j++;
         }else if(j>=deck2.cards.length){
             result.cards[k] = deck.cards[i];
             i++;
         }else if(deck.cards[i].compareCard(deck2.cards[j])==-1){
            result.cards[k] = deck.cards[i];
            i++;
         }else{
            result.cards[k] = deck2.cards[j];
            j++;
         }

      }
        
      return result;

         }
         
   public Deck mergeSort(Deck deck){
   //if the deck is 0 or 1 cards return it 
      if(deck.cards.length <= 1){
         return deck;
      }
      else{
         //find midpoint of the deck
         int mid = (deck.cards.length)/2;
         //divide the deck into two subdeck
         Deck top = deck.subDeck(deck,0, mid-1);
         Deck bottom = deck.subDeck(deck,mid, deck.cards.length-1); 
         
         //sort the subdecks using merge sort (recursive call)
         
         //merge the two halfs and return the result
         top = mergeSort(top);
         bottom = mergeSort(bottom);
         
         return merge(top,bottom);
      }
      

   }


}
