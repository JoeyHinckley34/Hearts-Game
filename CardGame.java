import java.util.*;
import java.util.Scanner;
import java.lang.Math;
public class CardGame {
    static int USER_SCORE;
    static int CPU1_SCORE;
    static int CPU2_SCORE;
    static int CPU3_SCORE;
    static String USERNAME;
    static int GAMENUM;
    public static void main(String[] args) {
    //start game --------------------------------------------------------------------------------------------------
    // starting deck 
    USER_SCORE = 0;
    CPU1_SCORE = 0;
    CPU2_SCORE = 0;
    CPU3_SCORE = 0;
    Deck deck = new Deck();
    ArrayList<Card> user = new ArrayList<Card>();
    ArrayList<Card> cpu1 = new ArrayList<Card>();
    ArrayList<Card> cpu2 = new ArrayList<Card>();
    ArrayList<Card> cpu3 = new ArrayList<Card>();
    ArrayList<Card> takeuser = new ArrayList<Card>();
    ArrayList<Card> takecpu1 = new ArrayList<Card>();
    ArrayList<Card> takecpu2 = new ArrayList<Card>();
    ArrayList<Card> takecpu3 = new ArrayList<Card>();
    ArrayList<Card> trick = new ArrayList<Card>();

    intro(user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
    System.out.println("\nShuffling...");
    System.out.print(USERNAME + "'s sorted deck -------------------------");
    play(deck, user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
 }
         
     
      public static void sort2(ArrayList<Card> list){
         for (int i = 0; i<list.size(); i++){
            for (int j = 0; j<list.size(); j++){
               if(list.get(i).compareCard(list.get(j)) == -1){
                  Card temp = list.get(i);
                  list.set(i,list.get(j));
                  list.set(j,temp);
               }
            }
         }
      }
      
      public static void print(ArrayList<Card> cards){
         System.out.println("\n");
         for(int i = 0; i<cards.size(); i++){
         System.out.println((i+1) + ": " + cards.get(i));
         }
      }

      public static void pick(ArrayList<Card> user, ArrayList<Card> cpu1 ,ArrayList<Card> cpu2,ArrayList<Card> cpu3, ArrayList<Card> trick){
          Scanner scan = new Scanner(System.in);
          for(int i =0; i<4; i++){
            trick.add(null);
          }
          print(user); 
          //Print CPU's cards to see if AI is working---------------------------------------------------   
          print(cpu1);
          print(cpu2);
          print(cpu3);
          System.out.println("\nPick a card by number:");
          int choice = scan.nextInt();
          while(choice >user.size()){
            System.out.println("\nPick a different card:");
            choice = scan.nextInt();
          }
          
          trick.set(0, user.get(choice - 1));
          user.remove(choice - 1);
          pickCPU(cpu1,trick,1);
          pickCPU(cpu2,trick,2);
          pickCPU(cpu3,trick,3);
      }
            
      public static void pickCPU(ArrayList<Card> cpu, ArrayList<Card> trick, int x){
         int choice = 0;
         ArrayList<Card> yeet = new ArrayList<Card>();
         for(int i = 0; i<cpu.size(); i++){
            if(cpu.get(i).suit == trick.get(0).suit){
               yeet.add(cpu.get(i));
            }
         }
         if(yeet.size() == 1){
            trick.set(x, yeet.get(0));
         }
         else if(yeet.size() > 1){
            sort2(yeet);
            if(yeet.get(0).rank > trick.get(0).rank){
                  trick.set(x, yeet.get(0));   
            }
            else{
               for(int i = yeet.size()-1; i<=0; i--){
                  if(yeet.get(i).rank < trick.get(0).rank){
                     trick.set(x, yeet.get(i));   
                  }
               } 
            trick.set(x, yeet.get(yeet.size()-1));
            } 
         }
         else{
            for(int i = 0; i<cpu.size(); i++){
               if(cpu.get(i).suit == 3 && cpu.get(i).rank == 10){
                   trick.set(x, cpu.get(i));
               } 
            }
            ArrayList<Card> hearts = new ArrayList<Card>();
            for(int i = 0; i<cpu.size(); i++){
               if(cpu.get(i).suit == 3){
                  hearts.add(cpu.get(i));
               }
            }
            if(hearts.size() > 0){
               sort2(hearts);  
               trick.set(x, hearts.get(choice));
            
            }
            else{
               for(int i = 1; i< cpu.size(); i++){
                  if(cpu.get(choice).rank < cpu.get(i).rank){
                     choice = i;
                  }
               }
            trick.set(x,cpu.get(choice));
            }
         }
         
         for(int j = 0; j<cpu.size(); j++){
            if(trick.get(x).suit == cpu.get(j).suit && trick.get(x).rank == cpu.get(j).rank){
               cpu.remove(j); 
            }
         }
       }
    
    public static void compareTrick(ArrayList<Card> user, 
                                    ArrayList<Card> cpu1,
                                    ArrayList<Card> cpu2,
                                    ArrayList<Card> cpu3,
                                    ArrayList<Card> trick,
                                    ArrayList<Card> takeuser,
                                    ArrayList<Card> takecpu1,
                                    ArrayList<Card> takecpu2,
                                    ArrayList<Card> takecpu3){
      Card highest = trick.get(0);
      for (int i = 1; i < 4; i++){
         if (highest.rank < trick.get(i).rank && 
             trick.get(i).suit == trick.get(0).suit){
            highest = trick.get(i);
         }
      }
      if (highest == trick.get(0)){
         while (trick.size() > 0){
            takeuser.add(trick.get(0));
            trick.remove(0);
         }
      } else if (highest == trick.get(1)){
         while (trick.size() > 0){
            takecpu1.add(trick.get(0));
            trick.remove(0);
         }
      } else if (highest == trick.get(2)){
         while (trick.size() > 0){
            takecpu2.add(trick.get(0));
            trick.remove(0);
         }
      } else {
         while (trick.size() > 0){
            takecpu3.add(trick.get(0));
            trick.remove(0);
         }
      }
    }
    
   public static int endgame(ArrayList<Card> takeplayer){
      int score = 0;
      for (int i = 0; i < takeplayer.size(); i++){
         if (takeplayer.get(i).suit == 2){
            score++;
         }
         if (takeplayer.get(i).suit == 3 &&
             takeplayer.get(i).rank == 10){
             score += 13;
         }
      }
      return score;
    }
    
    public static void scoreboard(ArrayList<Card> takeuser,
                                  ArrayList<Card> takecpu1,
                                  ArrayList<Card> takecpu2, 
                                  ArrayList<Card> takecpu3){
      System.out.println("\n\nScoreboard-----------------------");
      if(endgame(takeuser) == 26){
         System.out.println(USERNAME + "'s score: " + USER_SCORE);
         System.out.println("CPU1 score: " + (26 + CPU1_SCORE));
         System.out.println("CPU2 score: " + (26 + CPU2_SCORE));
         System.out.println("CPU3 score: " + (26 + CPU3_SCORE));
         CPU1_SCORE+= 26;
         CPU2_SCORE+= 26;
         CPU3_SCORE+= 26;
      }
      else if(endgame(takecpu1) == 26){
         System.out.println(USERNAME + "'s score: " + (26 + USER_SCORE));
         System.out.println("CPU1 score: " + CPU1_SCORE);
         System.out.println("CPU2 score: " + (26 + CPU2_SCORE));
         System.out.println("CPU3 score: " + (26 + CPU3_SCORE));
         USER_SCORE+= 26;
         CPU2_SCORE+= 26;
         CPU3_SCORE+= 26;
      }
      else if(endgame(takecpu2) == 26){
         System.out.println(USERNAME + "'s score: " + (26 + USER_SCORE));
         System.out.println("CPU1 score: " + (26 + CPU1_SCORE));
         System.out.println("CPU2 score: " + CPU2_SCORE);
         System.out.println("CPU3 score: " + (26 + CPU3_SCORE));
         USER_SCORE+= 26;
         CPU1_SCORE+= 26;
         CPU3_SCORE+= 26;
      }
       else if(endgame(takecpu3) == 26){
         System.out.println(USERNAME + "'s score: " + (26 + USER_SCORE));
         System.out.println("CPU1 score: " + (26 + CPU1_SCORE));
         System.out.println("CPU2 score: " + (26 + CPU2_SCORE));
         System.out.println("CPU3 score: " + CPU3_SCORE);
         USER_SCORE+= 26;
         CPU2_SCORE+= 26;
         CPU1_SCORE+= 26;
      }
      else{         
         System.out.println(USERNAME + "'s score: " + (endgame(takeuser) + USER_SCORE));
         System.out.println("CPU1 score: " + (endgame(takecpu1) + CPU1_SCORE));
         System.out.println("CPU2 score: " + (endgame(takecpu2) + CPU2_SCORE));
         System.out.println("CPU3 score: " + (endgame(takecpu3) + CPU3_SCORE));
         USER_SCORE+= endgame(takeuser);
         CPU1_SCORE+= endgame(takecpu1);
         CPU2_SCORE+= endgame(takecpu2);
         CPU3_SCORE+= endgame(takecpu3);
      }
    } 
    
    public static void play(Deck deck,
                            ArrayList<Card> user, 
                            ArrayList<Card> cpu1,
                            ArrayList<Card> cpu2,
                            ArrayList<Card> cpu3,
                            ArrayList<Card> trick,
                            ArrayList<Card> takeuser,
                            ArrayList<Card> takecpu1,
                            ArrayList<Card> takecpu2,
                            ArrayList<Card> takecpu3){
    GAMENUM++;
    deck.shuffle();
     
    //sub decks 
    for(int i = 0; i<=51; i++){
      if (i%4 == 0) 
         user.add(deck.cards[i]);
      else if (i%4 == 1)
         cpu1.add(deck.cards[i]);
      else if (i%4 == 2)
         cpu2.add(deck.cards[i]);
      else  
         cpu3.add(deck.cards[i]);
    }
    sort2(user);
    sort2(cpu1);
    sort2(cpu2);
    sort2(cpu3);
      System.out.print("\nROUND " + GAMENUM + "----------");
     while (user.size() > 0){
      pick(user, cpu1, cpu2, cpu3, trick);
      System.out.print("Trick: " + trick);
      compareTrick(user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
     }
    scoreboard(takeuser, takecpu1, takecpu2, takecpu3);
    playAgain(deck, user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
   }
   
   public static void intro(ArrayList<Card> user, 
                            ArrayList<Card> cpu1,
                            ArrayList<Card> cpu2,
                            ArrayList<Card> cpu3,
                            ArrayList<Card> trick,
                            ArrayList<Card> takeuser,
                            ArrayList<Card> takecpu1,
                            ArrayList<Card> takecpu2,
                            ArrayList<Card> takecpu3){
      System.out.println("WELCOME TO HEARTS!");
      Scanner scan = new Scanner(System.in);
      System.out.println("What is your name?");
      USERNAME = scan.nextLine();
      System.out.println("Do you know how to play?");
      String answer = scan.nextLine();
      if (answer.substring(0,1).toLowerCase().equals("y")){
         System.out.println("Great, you're confident!");
      } else {
         System.out.println("This is a classic trick-winning card game.");
         System.out.println("Lowest score wins.");
         System.out.println("First to 100 loses.");
         System.out.println("Hearts are each worth 1, Q of spades is 13.");
         System.out.println("Good luck!");
      }
   }  
   
   public static void playAgain(Deck deck,
                               ArrayList<Card> user, 
                               ArrayList<Card> cpu1,
                               ArrayList<Card> cpu2,
                               ArrayList<Card> cpu3,
                               ArrayList<Card> trick,
                               ArrayList<Card> takeuser,
                               ArrayList<Card> takecpu1,
                               ArrayList<Card> takecpu2,
                               ArrayList<Card> takecpu3){
      int a = (int)Math.max(USER_SCORE, CPU1_SCORE);
      int b = (int)Math.max(CPU2_SCORE, CPU3_SCORE);
      int c = (int)Math.min(USER_SCORE, CPU1_SCORE);
      int d = (int)Math.min(CPU2_SCORE, CPU3_SCORE);
      reset(takeuser);
      reset(takecpu1);
      reset(takecpu2);
      reset(takecpu3);
   if (Math.max(a, b) < 100){
      play(deck, user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
   } else {
      int winner = (int)Math.min(c,d);
      if (winner == USER_SCORE){
         System.out.print(USERNAME + " wins!");
      } else if (winner == CPU1_SCORE){
         System.out.print("CPU1 wins. Better luck next time!");
      } else if (winner == CPU2_SCORE){
         System.out.print("CPU2 wins. Better luck next time!");
      } else if (winner == CPU3_SCORE){
         System.out.print("CPU3 wins. Better luck next time!");
      }
   }
   } 
   
   public static void reset(ArrayList<Card> take){
      while (take.size() > 0){
         take.remove(0);
      }
   }
                                                 
}
