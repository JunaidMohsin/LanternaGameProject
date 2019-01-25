import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Field {

    //main function calling the actual game.
    public static void main(String[] args) {


        try {
            startGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("Game over!");
        }

    }

    //Starting game.
    private static void startGame() throws IOException, InterruptedException {


        Terminal terminal = createTerminal();



        int chosenLevel = Menu.menu(terminal);
        terminal.clearScreen();     //Menu is removed after level is chosen and the game starts.

        int noRocks = 10*chosenLevel;

        System.out.println("Enemies: "+ noRocks);

        Player player = createPlayer();

        //Obstacles
        List<Obstacle> rocklist = new ArrayList<>();
        for(int i = 0; i <noRocks; i++){
            rocklist.add(new Obstacle(ThreadLocalRandom.current().nextInt(30,70),ThreadLocalRandom.current().nextInt(1,21),'*'));
        }

        //Obstacle rock = new Obstacle(40,30,'*');

        //drawCharacters(terminal, player);

        do {
            KeyStroke keyStroke = getUserKeyStroke(terminal, rocklist);//moving obstacle inside this function

            movePlayer(player, keyStroke);
       //     rock.scrollLeft();
      //      drawObstacle(terminal,rock);
            drawPlayer(terminal,player);
        //Thread.sleep(5);
           // for(int i = 0; i < noRocks; i++) {
            //}
        } while (isPlayerAlive(player,rocklist));
        //Before printing GAME OVER!!!, Clear the screen
        terminal.clearScreen();

        //Logic to print GAME OVER!!! on lanterna terminal
        String str = "GAME OVER!!!!!";
        int col = 30;
        for (char c : str.toCharArray()){
            terminal.setCursorPosition(col,20) ;
            terminal.setForegroundColor(TextColor.ANSI.RED);
            terminal.enableSGR(SGR.BOLD);
            terminal.enableSGR(SGR.BLINK);
            terminal.putCharacter(c);
            col++;
        }
        terminal.flush();


    }


    //Created player
    private static Player createPlayer() {
        return new Player(10, 14, '\u27c1');
    }

    //jumpfunction. Default?
    private static void movePlayer(Player player, KeyStroke keyStroke) {
        if(keyStroke != null) {
            switch (keyStroke.getKeyType()) {
                case ArrowUp:
                    player.jump();
                    break;
            case ArrowDown:
                player.gravity();
                break;
//            case ArrowLeft:
//                player.moveLeft();
//                break;
//            case ArrowRight:
//                player.moveRight();
//                break;
                default:
                    break;
            }
        }
    }

    //Terminal conditions.
    private static Terminal createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        return terminal;
    }

    //Method for keystroke. Jumpfunktion and moving object.
    private static KeyStroke getUserKeyStroke(Terminal terminal, List<Obstacle> rocks) throws InterruptedException, IOException {
        KeyStroke keyStroke;
        long i = 0;
       // do {
            Thread.sleep(30);
            keyStroke = terminal.pollInput();
            if(i%50==0){
                //move obstacle function.
                for(Obstacle rock : rocks){
                rock.scrollLeft();
                drawObstacle(terminal,rock);
                }
            }
            i++;
      //  } while (keyStroke == null);
        return keyStroke;
    }

    //Checking for collition.
    private static boolean isPlayerAlive(Player player, List<Obstacle> rocks) {
        for(Obstacle rock : rocks) {
            if (rock.getX() == player.getX() && rock.getY() == player.getY()) {
                return false;
            }
        }

        return true;
    }


    private static void drawObstacle(Terminal terminal, Obstacle rock) throws IOException{

        terminal.setCursorPosition(rock.getOldX(), rock.getOldY());
        terminal.putCharacter(' ');
        terminal.setCursorPosition(rock.getX(), rock.getY());
        terminal.putCharacter(rock.getSymbolObstacle());
        terminal.flush();
    }

    private static void drawPlayer(Terminal terminal, Player player) throws IOException{

        terminal.setCursorPosition(player.getOldx(), player.getOldy());
        terminal.putCharacter(' ');
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        terminal.flush();

    }


}


/**
 *          movePlayer(player, keyStroke);
 *        //     rock.scrollLeft();
 *       //      drawObstacle(terminal,rock);
 *             drawPlayer(terminal,player);
 *             Thread.sleep(100);
 *            // for(int i = 0; i < noRocks; i++) {
 *                 int px = ThreadLocalRandom.current().nextInt(45,55);
 *                 int py =  ThreadLocalRandom.current().nextInt(60);
 *                 System.out.println("this is x "+px+" this is y "+py);
 *                 rocklist.add(new Obstacle(px, py, '*'));
 *                 if(rocklist.size() >= 10)
 *                     rocklist.remove(9);
 *             //}
 */
