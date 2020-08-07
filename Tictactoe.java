package com.company;

import java.util.*;

public class Tictactoe<scanner> {
    /*This is my implementation of TICTACTOE game Using AI algorithms
    you can either draw with it or lose...... Lols
     */
    private  Character[][] Board;
    private final char player_1;
    private final char player_2 ;
    private char currentPlayer;
    private final Scanner scanner;
    private final Map <Integer , int[]> coordinates;
    private int count;
    private final Map<String , Character> arr;
    private String[] numArr;


    public Tictactoe(){
        this.Board = new Character[][]{{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};

        this.player_1 = 'X';
        this.player_2 = 'O';
        this.currentPlayer = this.player_1;
        scanner = new Scanner(System.in);
        coordinates = new HashMap <Integer, int[]>();
        this.arr = new HashMap<String, Character>();
        numArr = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        this.count = 0;

    }
    public void initiatecoordinate(){
        /*The coordinate of each spot is Mapped to  1 - 9  i for easy access,
        and numbers in word are mapped to numbers in Integer in the second Dictionary
        FOor Easy comparison (I just 14 days into Java when I wrote this just in case you might think doing that it is not necessary)*/
        int i = 1;
        for (int j = 0; j < 3; j++){
            for (int k = 0; k < 3 ; k++){
                coordinates.put(i, new int[] {j , k});
                this.arr.put(this.numArr[i-1] , this.Board[j][k]);
                i++;
            }
        }
        /*So the coordinate Dictionary (That's what we call it in Python that i came from) looks like --->
        >>>> {1: {0,0} , 2:{0,1} ...., 9:{2,2} } and arr Dictionary looks like -->
        >>>> {"one":1, "two":2, ...... "nine":9}
        so , numbers in word access numbers in INT and numbers INT access coordinate... hahaha
        I know Java Uses = and not : , but that is what i am convenient with...lol
         */
    }
    public void displayBoard() {
        /*As the Function name implies, The function display the board in a nice formatted way*/

        System.out.printf("\n %c | %c | %c ", this.Board[0][0],this.Board[0][1],this.Board[0][2]);
        System.out.print("\n-------------");
        System.out.printf("\n %c | %c | %c ", this.Board[1][0],this.Board[1][1],this.Board[1][2]);
        System.out.print("\n-------------");
        System.out.printf("\n %c | %c | %c \n", this.Board[2][0],this.Board[2][1],this.Board[2][2]);

    }
    public void updateBoard(int point){
        /*This function change the value at a coordinate on he board to Either X or O depending on the player playing*/

        int[] j = this.coordinates.get(point);
        this.Board[j[0]][j[1]] = this.currentPlayer;
        this.arr.put(this.numArr[point-1], this.currentPlayer) ;
        /* So the arr Dictionary looks like---->
        {"one":1, "two":2, "three":"0" ...... "nine":"X"}
         */

        }
    public void switchPlayer(){
      /* As the method's name implies, we only switch players*/
     if (this.currentPlayer == this.player_1) {
         this.currentPlayer = this.player_2;
     }
     else {
         this.currentPlayer = this.player_1;
     }
    }
    public int computermove(){
        /*You Computer is not moving anywhere as the Method name says... LMAO
        it's just the Computer playing, and This is where the main AI logic is.
        In a nutshell , Let me summarize this Algorithm---->
        ----> The Computer check if there is a spot available to make it win, if there is , It plays there.
        ----> If there isn't, it check if there is a spot that will allow you to win, if there is, it plays there.
        ----> so , if there isn't, it plays in the middle if its available. if not , it plays at the extreme ends , hen other available space.
        ----> That's all, Simple Right. Not really simple for a Novice.
        Let me tell you a secret , The computer create a new board and plays in all available spot to know which one will be favourable for it.. hahaha
         */
        System.out.println("Computer Playing!!!");
        Character[][] spaces = new Character[3][3];
        Random random = new Random();
        Tictactoe comp = new Tictactoe();
        comp.initiatecoordinate();
        comp.Board = new Character[3][3];
        comp.initiatecoordinate();
        List <Integer> available = new ArrayList<Integer>();
        int i = 0;
        for (Character x : arr.values()){
            if (Character.isDigit(x)){
                available.add(Integer.parseInt(String.valueOf(x)));
                i++;
            }
        }
        i = 0;
        for (Character[] c : this.Board){
            int p = 0;
            for (Character j : c) {
                comp.Board[i][p] =j;
                p++;
            }
            i++;
        }
        comp.currentPlayer = this.currentPlayer;
        for (int m = 0 ; m < 2; m++) {
            for (int c : available) {
                comp.initiatecoordinate();

                comp.updateBoard(c);
                if (comp.checkWinner()) {
                    System.out.println("Computer: I am Playing at position: "+ c);
                    return c;
                } else {
                    comp.Board = new Character[3][3];
                    i = 0;
                    for (Character[] y : this.Board) {
                        int p = 0;
                        for (Character j : y) {
                            comp.Board[i][p] = j;
                            p++;
                        }
                        i++;
                    }
                }
            }
            comp.switchPlayer();
        }


        List <Integer> edge = new ArrayList<Integer>();
        for (int k = 0; k <= 3 ; k++){
            if (available.contains(9)) {
                edge.add(9);
            }
            else if (available.contains(1)) {
                edge.add(1);
            }
            else if (available.contains(3)) {
                edge.add(3);
            }
            else if (available.contains(7)) {
                edge.add(7);
            }

        }
        List <Integer> side = new ArrayList<Integer>();

        if (available.contains(2)) {
            side.add(2);
        }
        else if (available.contains(6)) {
            side.add(6);
        }
        else if (available.contains(8)) {
            side.add(8);
        }
        else if (available.contains(4)) {
            side.add(4);
        }

        if (available.contains(5)){
            System.out.println("Computer: I am Playing at position: "+ 5);
            return 5;
        }

        else if (available.contains(1) || available.contains(3) || available.contains(9)||available.contains(7)){
            System.out.println("Computer: I am Playing at position: "+ edge.get(0));
            return edge.get(0);
        }
        else if (available.contains(2) || available.contains(6) || available.contains(8)||available.contains(4)){
            System.out.println("Computer: I am Playing at position: "+ side.get(0));
            return side.get(0);
        }
        System.out.println("Computer: I am Playing at position: "+ available.get(0));

        return available.get(0);
    }

    public int playerPlay(){
        //This allows you to play at your own position, your own decision implementation is in your brain ... lol
        System.out.printf("\n%c's turn\n", this.currentPlayer);
        System.out.print("Play in any available spot between 1-9: ");

        return scanner.nextInt();

    }
    public boolean checkValidMove(int c){
        //So the Check Valid move check if your play is a valid one
        // Like you can't play in a position that is not available and you cant play in a place already
        // chosen. I know i can Trust the Computer,  But I can't Trust you.... smh
        if (c > 9 || c < 1){
            System.out.println("Invalid Input");
            return false;
        }
        int[] point = coordinates.get(c);
        if (Character.isDigit(this.Board[point[0]][point[1]]) && Character.isJavaIdentifierPart(this.Board[point[0]][point[1]])){
            return true;
        }
        else{
            if (Character.isLetter(this.Board[point[0]][point[1]])){
                System.out.println("Place taken");
            }
            return false;
        }
    }
    public boolean checkWinner(){
        // Just is the name says
        if (this.arr.get("one") == this.arr.get("two") && this.arr.get("one") == this.arr.get("three")){
            return true;
        }
        else if (this.arr.get("one") == this.arr.get("four")  && this.arr.get("one") == this.arr.get("seven")){
            return true;
        }
        else if ((this.arr.get("one") == this.arr.get("five") && this.arr.get("one") == this.arr.get("nine"))){
            return true;
        }
        else if ((this.arr.get("four")  == this.arr.get("five") && this.arr.get("four")  == this.arr.get("six") )){
            return true;
        }
        else if (this.arr.get("two")  == this.arr.get("five") && this.arr.get("two")  == this.arr.get("eight")){
            return true;
        }
        else if (this.arr.get("three") == this.arr.get("six")  && this.arr.get("three") == this.arr.get("nine")){
            return true;
        }
        else if ( (this.arr.get("three") == this.arr.get("five") && this.arr.get("three") == this.arr.get("seven"))){
            return true;
        }
        else if ((this.arr.get("seven") == this.arr.get("eight") && this.arr.get("seven")  == this.arr.get("nine"))){
            return true;
        }
        else if (this.count == 9){

            return true;
        }
        else {

            return false;
        }

    }
    public void printResult(boolean x, int c){
        if (c == 9){
            System.out.println("Its A Tie!!!");
        }

        else if (x){
            System.out.println(this.currentPlayer + " Wins");
        }
    }

    public static void main(String[] args){
        int coord;
        Scanner scanner1 = new Scanner(System.in);

        boolean valid;
        Tictactoe tictactoe = new Tictactoe();
        tictactoe.initiatecoordinate();
        tictactoe.displayBoard();
        do {
            do {
                if (tictactoe.currentPlayer == tictactoe.player_2) {
                    coord = tictactoe.computermove();
                }
                else {
                    coord = tictactoe.playerPlay();
                }
            } while (!tictactoe.checkValidMove(coord));
            tictactoe.updateBoard(coord);
            tictactoe.displayBoard();
            tictactoe.count ++;
            tictactoe.printResult(tictactoe.checkWinner() , tictactoe.count);
            tictactoe.switchPlayer();
        }while (!tictactoe.checkWinner());

        System.out.print("Do you want to play again: ");
        String choice = scanner1.nextLine();
        if (choice.equalsIgnoreCase("yes")){
            main(args);
        }
        else {
            System.out.println("Thank you");
        }
    }
}
