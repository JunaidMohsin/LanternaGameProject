import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Menu {

    public static int menu(Terminal terminal) throws IOException, InterruptedException {
        getMenu(terminal);

        Character difficultyChoice = menuKeyStroke(terminal).getCharacter();

        int chosenLevel = Character.getNumericValue(difficultyChoice);
        System.out.println("Chosen level:" + chosenLevel);

        return chosenLevel;
    }

    public static void getMenu(Terminal terminal)throws IOException {
        ArrayList<char []> difficultyList = new ArrayList<>();



        String [] difficulty = {"JOOPS!","Choose difficulty level","1. Easy", "2. Medium", "3. Hard",
                "Enter difficulty level by pressing 1, 2 or 3.","Use arrowkeys (up/down) to navigate your spaceship."};

        for(int i = 0; i< difficulty.length;i++){
            char [] difLvl = difficulty[i].toCharArray();
            difficultyList.add(difLvl);
        }

        for(int i = 0; i < difficultyList.size(); i++){
            char [] list = difficultyList.get(i);
            for(int k = 0; k <difficultyList.get(i).length; k++){

                if(i == 0){
                    terminal.setForegroundColor(TextColor.ANSI.MAGENTA);
                }else if(i == 1){
                    terminal.setForegroundColor(TextColor.ANSI.WHITE);
                }else if(i == 2){
                    terminal.setForegroundColor(TextColor.ANSI.GREEN);
                }else if(i == 3){
                    terminal.setForegroundColor(TextColor.ANSI.YELLOW);
                }else if(i == 4){
                    terminal.setForegroundColor(TextColor.ANSI.RED);
                }
                else{
                    terminal.setForegroundColor(TextColor.ANSI.WHITE);
                }
                terminal.setCursorPosition(16+k, 3+(2*(1+i)));
                terminal.putCharacter(list[k]);
            }
        }
        terminal.flush();
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static KeyStroke menuKeyStroke(Terminal terminal)throws InterruptedException, IOException{
        KeyStroke keyS = null;
        do {
            Thread.sleep(5); // might throw InterruptedException
            keyS = terminal.pollInput();
        } while (keyS == null);
        return keyS;
    }


}
