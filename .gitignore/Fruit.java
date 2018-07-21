
package snakegame;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Fruit {
    int x;
    int y;
    Random random = new Random();
    
    Fruit() {
        x = random.nextInt(40)*10;
        y = random.nextInt(30)*10;
    }
     
    void draw(GraphicsContext gc) {

        gc.setFill(Color.RED);
        gc.fillRect(x, y, 10, 10);
    }
    
    void relocate() {
        x = random.nextInt(40)*10;
        y = random.nextInt(30)*10;
    }
    
}
