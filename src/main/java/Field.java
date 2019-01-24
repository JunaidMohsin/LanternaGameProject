import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        Player player = createPlayer();

        //Obstacles

        drawCharacters(terminal, player);

        do {
            KeyStroke keyStroke = getUserKeyStroke(terminal);

            movePlayer(player, keyStroke);

            //moveObstacle...

            drawCharacters(terminal, player /*, Obstacle */);

        } while (isPlayerAlive(player,/*, Obstacle */));


    }


    //Created player
    private static Player createPlayer() {
        return new Player(10, 10, '\u27c1');
    }

    //jumpfunction. Default?
    private static void movePlayer(Player player, KeyStroke keyStroke) {
        switch (keyStroke.getKeyType()) {
            case ArrowUp:
                player.moveUp();
                break;
//            case ArrowDown:
//                player.moveDown();
//                break;
//            case ArrowLeft:
//                player.moveLeft();
//                break;
//            case ArrowRight:
//                player.moveRight();
//                break;
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
    private static KeyStroke getUserKeyStroke(Terminal terminal) throws InterruptedException, IOException {
        KeyStroke keyStroke;
        long i = 0;
        do {
            Thread.sleep(5);
            keyStroke = terminal.pollInput();
            if(i%100 ==0){
                //move obstacle function.
            }
            i++;
        } while (keyStroke == null);
        return keyStroke;
    }

    //Checking for collition.
    private static boolean isPlayerAlive(Player player, List<Monster> monsters) {
        for (Monster monster : monsters) {
            if (monster.getX() == player.getX() && monster.getY() == player.getY()) {
                return false;
            }
        }
        return true;
    }
}
