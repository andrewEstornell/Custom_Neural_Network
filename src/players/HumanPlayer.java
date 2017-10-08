package players;

import game.Snake;

import java.util.Scanner;

/**
 * Created by andes on 9/17/2017.
 */
public class HumanPlayer extends Player{

    public char getMove(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().charAt(0);
    }




}
