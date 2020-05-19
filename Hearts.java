/* Names: Joey Hinckley
          Jessica Detwiler
          Emma Sabbadini
          Rhys Hammond      
   Date: 10 June 2019
   Assignment: Final Project
   Description: Hearts Card Game */

import java.util.*;
import java.util.Scanner;
import java.lang.Math;

public class Hearts{
   static int USER_SCORE;
   static int CPU1_SCORE;
   static int CPU2_SCORE;
   static int CPU3_SCORE;
   static String USERNAME;
   static int GAMENUM;
   static int TRICKNUM;
   static int STARTNUM;
   static boolean BROKEN;
   public static void main(String[] args){
      USER_SCORE = 0;
      CPU1_SCORE = 0;
      CPU2_SCORE = 0;
      CPU3_SCORE = 0;
      STARTNUM = 0;
      TRICKNUM = 0;
      GAMENUM = 0;
      BROKEN = false;
      Deck deck = new Deck();
      ArrayList<Card> user = new ArrayList<Card>();
      ArrayList<Card> cpu1 = new ArrayList<Card>();
      ArrayList<Card> cpu2 = new ArrayList<Card>();
      ArrayList<Card> cpu3 = new ArrayList<Card>();
      ArrayList<Card> takeuser = new ArrayList<Card>();
      ArrayList<Card> takecpu1 = new ArrayList<Card>();
      ArrayList<Card> takecpu2 = new ArrayList<Card>();
      ArrayList<Card> takecpu3 = new ArrayList<Card>();
      Card[] trick = new Card[4];
      intro();
      play(deck, user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
   } 
      
   // asks user general questions
   public static void intro(){
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
         System.out.println("Follow suit if you can.\nPlay 2 of clubs first if you have it.\nGood luck!");
      }
   }  
   
   // overarching play method
   public static void play(Deck deck,
                           ArrayList<Card> user, 
                           ArrayList<Card> cpu1,
                           ArrayList<Card> cpu2,
                           ArrayList<Card> cpu3,
                           Card[] trick,
                           ArrayList<Card> takeuser,
                           ArrayList<Card> takecpu1,
                           ArrayList<Card> takecpu2,
                           ArrayList<Card> takecpu3){
      GAMENUM++;
      BROKEN = false;
      deck.shuffle();
      for(int i = 0; i <= 51; i++){
         if (i%4 == 0){
            user.add(deck.cards[i]);
         } else if (i%4 == 1){
            cpu1.add(deck.cards[i]);
         } else if (i%4 == 2){
            cpu2.add(deck.cards[i]);
         } else {
            cpu3.add(deck.cards[i]);
         }
      }
      sort2(user);
      sort2(cpu1);
      sort2(cpu2);
      sort2(cpu3);
      System.out.print("\nROUND " + GAMENUM);
      while (TRICKNUM < 13){
         firstGo(user, cpu1, cpu2, cpu3, trick);
         compareTrick(user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
      }
      scoreboard(takeuser, takecpu1, takecpu2, takecpu3);
      playAgain(deck, user, cpu1, cpu2, cpu3, trick, takeuser, takecpu1, takecpu2, takecpu3);
   }
   
   // sorts deck 
   public static void sort2(ArrayList<Card> list){
      for (int i = 0; i < list.size(); i++){
         for (int j = 0; j < list.size(); j++){
            if(list.get(i).compareCard(list.get(j)) == -1){
               Card temp = list.get(i);
               list.set(i,list.get(j));
               list.set(j,temp);
            }
         }
      }
   }
   
   // prints deck   
   public static void print(ArrayList<Card> cards){
      System.out.println();
      for(int i = 0; i < cards.size(); i++){
         System.out.println((i + 1) + ": " + cards.get(i));
      }
   }

   // decides who goes first
   public static void firstGo(ArrayList<Card> user, 
                              ArrayList<Card> cpu1,
                              ArrayList<Card> cpu2,
                              ArrayList<Card> cpu3, 
                              Card[] trick){
      if (TRICKNUM == 0){
         for (int i = 0; i < user.size(); i++){
            if (user.get(i).suit == 0 && user.get(i).rank == 0){
               STARTNUM = 0;
            }
            if (cpu1.get(i).suit == 0 && cpu1.get(i).rank == 0){
               STARTNUM = 1;
            }
            if (cpu2.get(i).suit == 0 && cpu2.get(i).rank == 0){
               STARTNUM = 2;
            }
            if (cpu3.get(i).suit == 0 && cpu3.get(i).rank == 0){
               STARTNUM = 3;
            }
         }
      }
      if (STARTNUM == 0){
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);
            System.out.println("\nLead: Your choice");
            pick(user, cpu1, cpu2, cpu3, trick);
            pickCPU(cpu1, trick, 1);
            pickCPU(cpu2, trick, 2);
            pickCPU(cpu3, trick, 3);
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);
            System.out.print("\n\n--------------------------\n");
            TRICKNUM++;
      } else if (STARTNUM == 1){
            pickCPU(cpu1, trick, 1);
            pickCPU(cpu2, trick, 2);
            pickCPU(cpu3, trick, 3);
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);
            System.out.println("\nLead: " + suitString(trick));
            pick(user, cpu1, cpu2, cpu3, trick);
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);
            System.out.print("\n\n--------------------------\n");
            TRICKNUM++;
      } else if (STARTNUM == 2){
            pickCPU(cpu2, trick, 2);
            pickCPU(cpu3, trick, 3);
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);
            System.out.println("\nLead: " + suitString(trick));
            pick(user, cpu1, cpu2, cpu3, trick);
            pickCPU(cpu1, trick, 1);
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);
            System.out.print("\n\n--------------------------\n");
            TRICKNUM++;
      } else if (STARTNUM == 3){
            pickCPU(cpu3, trick, 3);
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);
            System.out.println("\nLead: " + suitString(trick));
            pick(user, cpu1, cpu2, cpu3, trick);
            pickCPU(cpu1, trick, 1);
            pickCPU(cpu2, trick, 2);
            System.out.print("\nTrick " + (TRICKNUM + 1) + ": ");
            aString(trick);            
            System.out.print("\n\n--------------------------\n");
            TRICKNUM++;
      }   
   }
   
   // allows the user to pick a card
   public static void pick(ArrayList<Card> user, 
                           ArrayList<Card> cpu1,
                           ArrayList<Card> cpu2,
                           ArrayList<Card> cpu3, 
                           Card[] trick){
      Scanner scan = new Scanner(System.in);
      System.out.print("\n" + USERNAME + "'s sorted deck ---------------");
      print(user);
      System.out.println("\nPick a card by number:");
      int choice = scan.nextInt();
      while(choice > user.size()){
         System.out.println("\nPick a different card:");
         choice = scan.nextInt();
      }
      if (TRICKNUM == 0 && STARTNUM == 0){
         while (!(user.get(choice - 1).rank == 0 && user.get(choice - 1).suit == 0)){
            System.out.println("Select the 2 of clubs first to start the game.");
            choice = scan.nextInt();
         }
      }
      for (int i = 0; i < user.size(); i++){
         if (STARTNUM != 0){
            boolean has = false;
            for (int j = 0; j < user.size(); j++){
               if (user.get(j).suit == trick[STARTNUM].suit){
                  has = true;
               }
            }
            while(user.get(choice - 1).suit != trick[STARTNUM].suit && has == true){
               System.out.println("Choose a " + suitString(trick) + " to follow suit.");
               choice = scan.nextInt();
            }
         }
         if (BROKEN == false && STARTNUM == 0){
            while (user.get(choice - 1).suit == 2){
               System.out.println("Hearts are not broken. Choose a different suit to lead with.");
               choice = scan.nextInt();
            }
         }
      }
      trick[0] = user.get(choice - 1);
      user.remove(choice - 1);
   }
            
   // AI chooses card
   public static void pickCPU(ArrayList<Card> cpu, 
                              Card[] trick,
                              int x){
      boolean has = false;
      for (int j = 0; j < cpu.size(); j++){
         if (cpu.get(j).suit != 2){
            has = true;
         }
      }
      if (has == false){
         BROKEN = true;
      }
      boolean club2 = false;
      if (STARTNUM == x && TRICKNUM == 0){
         club2 = true;
      }
      if (club2 == true){
         sort2(cpu);
         trick[x] = cpu.get(0);
      } else {                   
         int choice = 0;
         ArrayList<Card> yeet = new ArrayList<Card>();
         if (x == STARTNUM){
            for (int i = 0; i < cpu.size(); i++){
               if (BROKEN == false){
                  if (cpu.get(i).suit != 2){
                     yeet.add(cpu.get(i));
                  }
               } else {
                  yeet.add(cpu.get(i));
               }
            }
            int lowest = 14;
            int index = 0;
            for (int i = 0; i < yeet.size(); i++){
               if (yeet.get(i).rank < lowest){
                  lowest = yeet.get(i).rank;
                  index = i;
               }
            }
            trick[x] = yeet.get(index);   
         } else {
            for (int i = 0; i < cpu.size(); i++){
               if (cpu.get(i).suit == trick[STARTNUM].suit){
                  yeet.add(cpu.get(i));
               }
            }
            if (yeet.size() > 0){
               sort2(yeet); 
               
               for(int i = yeet.size()-1; i>=0 ;i--){
                  if(yeet.get(i).rank < trick[STARTNUM].rank){
                      trick[x] = yeet.get(i);
                      i = -1;
                  }
               }
               if(trick[x] == null){
                  trick[x] = yeet.get(0);
               }
            } else {
               for (int i = 0; i < cpu.size(); i++){
                  if (cpu.get(i).suit == 3 && cpu.get(i).rank == 10){
                     trick[x] = cpu.get(i);
                  } 
               }
               ArrayList<Card> hearts = new ArrayList<Card>();
               for (int i = 0; i < cpu.size(); i++){
                  if (cpu.get(i).suit == 3){
                     hearts.add(cpu.get(i));
                  }
               }
               if (hearts.size() > 0){
                  sort2(hearts);  
                  trick[x] = hearts.get(choice);
               } else {
                  trick[x] = cpu.get(choice);
               }
            }
         }
      }
      for (int j = 0; j < cpu.size(); j++){
         if (trick[x].suit == cpu.get(j).suit && 
             trick[x].rank == cpu.get(j).rank){
               cpu.remove(j);
         }
      }
   }
   
   // decides who wins the trick 
   public static void compareTrick(ArrayList<Card> user, 
                                   ArrayList<Card> cpu1,
                                   ArrayList<Card> cpu2,
                                   ArrayList<Card> cpu3,
                                   Card[] trick,
                                   ArrayList<Card> takeuser,
                                   ArrayList<Card> takecpu1,
                                   ArrayList<Card> takecpu2,
                                   ArrayList<Card> takecpu3){
      for (int i = 0; i < 4; i++){
         if (trick[i].suit == 2){
            BROKEN = true;
         }
      }
      Card highest = trick[STARTNUM];
      for (int i = 0; i < 4; i++){
         if (highest.rank < trick[i].rank && 
            trick[i].suit == trick[STARTNUM].suit){
            highest = trick[i];
         } 
      }
      if (highest == trick[0]){
         for (int i = 0; i < trick.length; i++){
            takeuser.add(trick[i]);
            trick[i] = null;
         }
         STARTNUM = 0;
      } else if (highest == trick[1]){
         for (int i = 0; i < trick.length; i++){
            takecpu1.add(trick[i]);
            trick[i] = null;
         }
         STARTNUM = 1;
      } else if (highest == trick[2]){
         for (int i = 0; i < trick.length; i++){
            takecpu2.add(trick[i]);
            trick[i] = null;
         }
         STARTNUM = 2;
      } else {
         for (int i = 0; i < trick.length; i++){
            takecpu3.add(trick[i]);
            trick[i] = null;
         }
         STARTNUM = 3;
      }
      for(int i = 0; i < 4; i++){
         trick[i] = null;
      }
   }
   
   // tallies scores 
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
   
   // displays scores 
   public static void scoreboard(ArrayList<Card> takeuser,
                                 ArrayList<Card> takecpu1,
                                 ArrayList<Card> takecpu2, 
                                 ArrayList<Card> takecpu3){
      System.out.println("\n\nScoreboard -----------------------");
      if (endgame(takeuser) == 26){
         System.out.println(USERNAME + "'s score: " + USER_SCORE);
         System.out.println("CPU1 score: " + (26 + CPU1_SCORE));
         System.out.println("CPU2 score: " + (26 + CPU2_SCORE));
         System.out.println("CPU3 score: " + (26 + CPU3_SCORE));
         CPU1_SCORE+= 26;
         CPU2_SCORE+= 26;
         CPU3_SCORE+= 26;
      } else if (endgame(takecpu1) == 26){
         System.out.println(USERNAME + "'s score: " + (26 + USER_SCORE));
         System.out.println("CPU1 score: " + CPU1_SCORE);
         System.out.println("CPU2 score: " + (26 + CPU2_SCORE));
         System.out.println("CPU3 score: " + (26 + CPU3_SCORE));
         USER_SCORE+= 26;
         CPU2_SCORE+= 26;
         CPU3_SCORE+= 26;
      } else if (endgame(takecpu2) == 26){
         System.out.println(USERNAME + "'s score: " + (26 + USER_SCORE));
         System.out.println("CPU1 score: " + (26 + CPU1_SCORE));
         System.out.println("CPU2 score: " + CPU2_SCORE);
         System.out.println("CPU3 score: " + (26 + CPU3_SCORE));
         USER_SCORE+= 26;
         CPU1_SCORE+= 26;
         CPU3_SCORE+= 26;
      } else if (endgame(takecpu3) == 26){
         System.out.println(USERNAME + "'s score: " + (26 + USER_SCORE));
         System.out.println("CPU1 score: " + (26 + CPU1_SCORE));
         System.out.println("CPU2 score: " + (26 + CPU2_SCORE));
         System.out.println("CPU3 score: " + CPU3_SCORE);
         USER_SCORE+= 26;
         CPU2_SCORE+= 26;
         CPU1_SCORE+= 26;
      } else {         
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
   
   // decides if another round is played   
   public static void playAgain(Deck deck,
                                ArrayList<Card> user, 
                                ArrayList<Card> cpu1,
                                ArrayList<Card> cpu2,
                                ArrayList<Card> cpu3,
                                Card[] trick,
                                ArrayList<Card> takeuser,
                                ArrayList<Card> takecpu1,
                                ArrayList<Card> takecpu2,
                                ArrayList<Card> takecpu3){
      TRICKNUM = 0;                          
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
   
   // resets cards
   public static void reset(ArrayList<Card> take){
      while (take.size() > 0){
         take.remove(0);
      }
   }
   
   // displays array of cards
   public static void aString(Card[] cards){
      String result = "[";
      for (int i = 0; i < cards.length - 1; i++){
         if (cards[i] == null){
            result += "_____, ";
         } else {
            result += cards[i].toString() + ", ";
         }
      }
      if (cards[cards.length - 1] == null){
         result+= "_____";
      } else {
         result += cards[cards.length - 1].toString();
      }
      result += "]";
      System.out.print(result); 
   }
   
   // displays text of suit
   public static String suitString(Card[] trick){
      if (trick[STARTNUM].suit == 0){
         return "Clubs"; 
      } else if (trick[STARTNUM].suit == 1){
         return "Diamonds";
      } else if (trick[STARTNUM].suit == 2){
         return "Hearts";
      } else {
         return "Spades";
      } 
   }
}
