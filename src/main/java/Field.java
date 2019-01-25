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


        boolean isAlive = true;

        MP3Player m = new MP3Player();
        m.play("Victory.mp3");

        Terminal terminal = createTerminal();
        //we decide level
        int chosenLevel = Menu.menu(terminal);

        terminal.clearScreen();     //Menu is removed after level is chosen and the game starts.


        int noRocks = 10*chosenLevel;
        if(chosenLevel == 3){
            noRocks = 10;
        }
        System.out.println("Amount of enemies: "+ noRocks);

        Player player = createPlayer();
        //for score
        int score = 0;
        String msg1 = "Score: ";
        String msg2 = "FINAL SCORE: ";

        //Obstacles
        List<Obstacle> rocklist = new ArrayList<>();
        for(int i = 0; i <noRocks; i++){
            rocklist.add(new Obstacle(ThreadLocalRandom.current().nextInt(30,70),ThreadLocalRandom.current().nextInt(1,21),'\u0464'));
        }

        //int px = 20;
       // int py = 20;

        do {

                score += 1;
            //System.out.println(score);
            if(score%5 == 0){
                drawScore(terminal,score,msg1);
            }

            // just to check how lanterna terminal prints a shape
         /*   terminal.setCursorPosition(px, py);
            terminal.putCharacter('o');

            terminal.setCursorPosition(px-1, py-1);
            terminal.putCharacter('Y');

            terminal.setCursorPosition(px+1, py+1);
            terminal.putCharacter('X');

            terminal.setCursorPosition(50, 50);
            terminal.putCharacter('D');*/

            KeyStroke keyStroke = getUserKeyStroke(terminal, rocklist);//moving obstacle inside this function

            movePlayer(player, keyStroke);

            if(chosenLevel <= 2) {
                drawPlayer(terminal, player);
                isAlive = isPlayerAlive(player,rocklist);
            }
            else {
                drawPlayerShape(terminal, player);

                for (Obstacle rock : rocklist) {
                    isAlive = isPlayerShapeAlive(player, rock);
                    if (isAlive == false)
                        break;
                }
            }



        } while (isAlive); //while(isAlive);//


        //Before printing GAME OVER!!!, Clear the screen
        terminal.clearScreen();

        //update score to screen
        drawScore(terminal,score,msg2);

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
        m.play("Blues-Loop.mp3",true);
        terminal.flush();
    }


    //Created player
    private static Player createPlayer() {
        return new Player(10, 14, '\u058E');
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

    //Method for keystroke. Jump funktion and moving object.
    private static KeyStroke getUserKeyStroke(Terminal terminal, List<Obstacle> rocks) throws InterruptedException, IOException {
        KeyStroke keyStroke;
        long i = 0;
       // do {
            Thread.sleep(30);
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


    private static boolean isPlayerShapeAlive(Player player, Obstacle rock) {

        if( (rock.getX() <= player.getX()+1) && (rock.getX() >= player.getX()-1) && (rock.getY() <= player.getY()+1) && (rock.getY() >= player.getY()-1))
        {
            return false;
        }

        return true;
    }

    private static void drawObstacle(Terminal terminal, Obstacle rock) throws IOException{

        terminal.setCursorPosition(rock.getOldX(), rock.getOldY());
        terminal.putCharacter(' ');
        terminal.setForegroundColor(TextColor.ANSI.YELLOW);
        terminal.setCursorPosition(rock.getX(), rock.getY());
        terminal.putCharacter(rock.getSymbolObstacle());
        terminal.flush();
    }

    private static void drawPlayer(Terminal terminal, Player player) throws IOException{

        terminal.setCursorPosition(player.getOldx(), player.getOldy());
        terminal.putCharacter(' ');
        terminal.setForegroundColor(TextColor.ANSI.CYAN);

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        terminal.flush();

    }

    private static void drawObstacleShape(Terminal terminal, Obstacle rock) throws IOException{

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                terminal.setCursorPosition(rock.getOldX()+i,rock.getOldY()+j);
                terminal.putCharacter(' ');
            }
        }
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                terminal.setCursorPosition(rock.getX()+i,rock.getY()+j);
                terminal.putCharacter(rock.getChar(i+1,j+1));
            }
        }
        terminal.flush();

    }

    private static void drawPlayerShape(Terminal terminal, Player player) throws IOException{

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                terminal.setCursorPosition(player.getOldx()+i,player.getOldy()+j);
                terminal.putCharacter(' ');
            }
        }
        terminal.setForegroundColor(TextColor.ANSI.CYAN);
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                terminal.setCursorPosition(player.getX()+i,player.getY()+j);
                terminal.putCharacter(player.getChar(i+1,j+1));
            }
        }
        terminal.flush();

    }

    private static void drawScore(Terminal terminal,int score, String msg) throws IOException{
        String val = msg+score;
        terminal.setForegroundColor(TextColor.ANSI.GREEN);
        for(int i = 0; i < val.length(); i++){
            terminal.setCursorPosition(10+i,1);
            terminal.putCharacter(val.charAt(i));
        }
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
