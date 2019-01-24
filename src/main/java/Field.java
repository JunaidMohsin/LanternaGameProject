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

        int noRocks = 6;

        Terminal terminal = createTerminal();

        Player player = createPlayer();

        //Obstacles
        List<Obstacle> rocklist = new ArrayList<>();
        int px = ThreadLocalRandom.current().nextInt(45,55);
        int py =  ThreadLocalRandom.current().nextInt(2,60);
        rocklist.add(new Obstacle(35,5,'*'));
        rocklist.add(new Obstacle(40,15,'*'));
        rocklist.add(new Obstacle(45,8,'*'));
        rocklist.add(new Obstacle(55,20,'*'));
        rocklist.add(new Obstacle(50,10,'*'));

        //Obstacle rock = new Obstacle(40,30,'*');

        //drawCharacters(terminal, player);

        do {
            KeyStroke keyStroke = getUserKeyStroke(terminal, rocklist);//moving obstacle inside this function

            movePlayer(player, keyStroke);
       //     rock.scrollLeft();
      //      drawObstacle(terminal,rock);
            drawPlayer(terminal,player);
        //    Thread.sleep(100);
           // for(int i = 0; i < noRocks; i++) {
            //}
        } while (isPlayerAlive(player,rocklist));


    }


    //Created player
    private static Player createPlayer() {
        return new Player(20, 14, '\u27c1');
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
            Thread.sleep(10);
            keyStroke = terminal.pollInput();
            if(i%10000==0){
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
