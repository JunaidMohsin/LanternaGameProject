public class Player {

    private int x;
    private int y;
    private int oldx;
    private int oldy;
    char symbol;

//this is for jump logic
    private int jumpSpeed;
    private int gravity;


    public int getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(int jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOldx() {
        return oldx;
    }

    public void setOldx(int oldx) {
        this.oldx = oldx;
    }

    public int getOldy() {
        return oldy;
    }

    public void setOldy(int oldy) {
        this.oldy = oldy;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Player(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    //for jump implementation
    public double getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void jump(){
        jumpSpeed = -10;
        gravity = 1;

        y = y - jumpSpeed;
    }

}
