import java.util.concurrent.ThreadLocalRandom;

public class Obstacle {
    private int x;
    private int y;
    private int oldX;
    private int oldY;
    private char symbolObstacle;
    Shape obstacleBody;


    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }


    public Obstacle(int x, int y, char symbolObstacle) {
        this.x = x;
        this.y = y;
        this.symbolObstacle = symbolObstacle;
     //   obstacleBody = new Shape(symbolObstacle,'\u03F9'); activate to have shape instead of char
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbolObstacle() {
        return symbolObstacle;
    }

    public void scrollLeft(){
        if(x <= 1)
        {
            oldX = x;
            oldY = y;
            x = 50;
            y = ThreadLocalRandom.current().nextInt(1,21);
        }
        else{
            oldX = x;
            oldY = y;
            x-=1;
        }

    }

    public char getChar(int indX, int indY){
        return obstacleBody.body[indX][indY];
    }


}
