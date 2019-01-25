public class Shape {

    char[][] body;
    char symbol1;
    char symbol2;

    int size = 3;

    public Shape(char symbol1,char symbol2) {
        this.symbol1 = symbol1;
        this.symbol2 = symbol2;
        this.size = size;
        this.body = new char[size][size];

        /*for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                body[i][j] = symbol;
            }
        }*/

        body[0][0] = ' ';     body[0][1] = symbol1;    body[0][2] = ' ';
        body[1][0] = symbol2;     body[1][1] = symbol1;    body[1][2] = symbol2;
        body[2][0] = ' ';     body[2][1] = symbol1;    body[2][2] = ' ';

    }

}
