package snakegame;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class SnakePart {
    int x;
    int y;
    int width;
    int height;
    
    SnakePart(int _x, int _y, int w, int h) {
        x = _x;
        y = _y;
        width = w;
        height = h;
    }
}

public class Snake {
    
    ArrayList<SnakePart> snake;
    SnakePart head;
    Snake() {
        snake = new ArrayList<>();
        snake.add(new SnakePart(200, 150, 10, 10));
        head = snake.get(0);
  
    }
    
    void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 200, 3));
        gc.fillRect(head.x, head.y, head.width, head.height);
        gc.setFill(Color.rgb(0, 130, 3));
        for (int i = 1; i < snake.size(); i++) {
            gc.fillRect(snake.get(i).x, snake.get(i).y, snake.get(i).width, snake.get(i).height);
        }
    }
    
    void eat() {
        int x = snake.get(snake.size()-1).x;
        int y = snake.get(snake.size()-1).y;
        snake.add(new SnakePart(x, y+head.width, 10, 10));
    }
    
    void move(int dir) {
        int prevX;
        int prevY;
        int pprevX;
        int pprevY;
        prevX = head.x;
        prevY = head.y;
        
        if (dir == 0)
          head.x += 10;
        if (dir == 1)
            head.x -=10;
        if (dir == 2)
            head.y += 10;
        if (dir == 3)
            head.y -= 10;
        
        for (int i = 1; i < snake.size(); i++) {
            pprevX = snake.get(i).x;
            pprevY = snake.get(i).y;
            snake.get(i).x = prevX;
            snake.get(i).y = prevY;
            prevX = pprevX;
            prevY = pprevY;
        }
    }
    
    boolean colision(Fruit f) {
        float d = (float) Math.hypot(this.head.x - f.x, this.head.y - f.y);
        return d <= 0;
    }
    
    boolean over(int w, int h) {
        return head.x < 0 || head.x >= w || head.y < 0 || head.y >= h;
    }
    
}