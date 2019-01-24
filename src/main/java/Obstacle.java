public class Obstacle {
    private int x;
    private int y;
    private int oldX;
    private int oldY;
    private char symbolObstacle;


    public Obstacle(int x, int y, char symbolObstacle) {
        this.x = x;
        this.y = y;
        this.symbolObstacle = symbolObstacle;
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
        x--;

    }


}
