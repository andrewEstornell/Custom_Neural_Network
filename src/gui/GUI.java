package gui;

import game.Game;
import game.Snake;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class GUI extends JFrame
{
    private JPanel panel = new JPanel();
    private boolean wasClicked;
    private JButton[][] buttons;
    private int boardSize1;
    private int boardSize2;


    public GUI(int boardSize1, int boardSize2)
    {
        this.boardSize1 = boardSize1;
        this.boardSize2 = boardSize2;
        this.buttons = new JButton[this.boardSize1][this.boardSize2];
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //dimensions of the board
        setSize(900,90000);
        //does not allow the user to resize the board, may change later if we get better with scaling
        setResizable(true);
        this.setWasClicked(false);
        //sets up an array that we will add buttons to\
        this.panel.setLayout(new GridLayout(boardSize1, boardSize2));

        //creates the actual buttons
        for(int i = 0; i < boardSize1; i++)
        {
            for(int j = 0; j < boardSize2; j++)
            {
                //Initializes the button
                this.buttons[i][j] = new JButton();
                //colors every offset buttons black creating the checker board effect
                this.panel.add(buttons[i][j]);
                this.buttons[i][j].setActionCommand(i + " " + j);
            }
        }
        //adds the grid of buttons to the window
        add(this.panel);
        //displays the window to the user
        setVisible(true);
        setFocusable(true);
    }

    public void updateeGui(Game game){
        Snake snake = game.getSnake();
        for(int i = 0; i < this.buttons.length; i++){
            for(int j = 0; j < this.buttons[i].length; j++){
                if(i == 0 || i == 19 || j == 0 || j == 19){
                    this.buttons[i][j].setBackground(Color.BLACK);
                    this.buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                }
                else {
                    this.buttons[i][j].setBackground(Color.LIGHT_GRAY);
                    this.buttons[i][j].setBorder(new LineBorder(Color.GRAY));
                    this.buttons[i][j].setOpaque(true);
                }
            }
        }
        for(int i = 0; i < snake.getSnakeBody().size(); i++) {
            if (i == 0) {
                this.buttons[snake.getSnakeBody().get(i)[0]][snake.getSnakeBody().get(i)[1]].setBackground(Color.GREEN);
                this.buttons[snake.getSnakeBody().get(i)[0]][snake.getSnakeBody().get(i)[1]].setBorder(new LineBorder(Color.orange));
                this.buttons[snake.getSnakeBody().get(i)[0]][snake.getSnakeBody().get(i)[1]].setOpaque(true);

            }
            else {
                this.buttons[snake.getSnakeBody().get(i)[0]][snake.getSnakeBody().get(i)[1]].setBackground(Color.BLUE);
                this.buttons[snake.getSnakeBody().get(i)[0]][snake.getSnakeBody().get(i)[1]].setBorder(new LineBorder(Color.orange));
                this.buttons[snake.getSnakeBody().get(i)[0]][snake.getSnakeBody().get(i)[1]].setOpaque(true);
            }
        }
        //this.buttons[snake.getSnakeBody().get(0)[0]][snake.getSnakeBody().get(1)[1]].setBackground(Color.GREEN);
        this.buttons[game.getLocationOfObjective()[0]][game.getLocationOfObjective()[1]].setBackground(Color.RED);
        this.buttons[game.getLocationOfObjective()[0]][game.getLocationOfObjective()[1]].setOpaque(true);

    }

    public boolean wasClicked(){return this.wasClicked;}
    public void setWasClicked(boolean wasClicked){this.wasClicked = wasClicked;}

    public JButton[][] getButtons(){return this.buttons;}

}
