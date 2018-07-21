package snakegame;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.paint.Color.rgb;
import javafx.stage.Stage;
import javafx.util.Duration;


public class App extends Application {
    
    private Group root;
    private Scene scene;
    private Snake snake;
    private Fruit fruit;
    private Canvas canvas;
    private GraphicsContext gc;
    private Button playBtn;
    private Button exitBtn;
    private Timeline timeline;
    private final int width = 400;
    private final int height = 300;
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;
    private Integer score = 0;
    Media sound1;
    Media sound2;
    
    
    
    @Override
    public void start(Stage primaryStage) {
        // Initialization app components
        Application.setUserAgentStylesheet(getClass().getResource("buttonstyle.css").toExternalForm());
        root = new Group();
        scene = new Scene(root, width, height);
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: black");
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        sound1 = new Media(new File("src/snakegame/eat.wav").toURI().toString());
        sound2 = new Media(new File("src/snakegame/hit.wav").toURI().toString());
        MediaPlayer eat = new MediaPlayer(sound1);
        MediaPlayer hit = new MediaPlayer(sound2);
        

        // Initialization controls
        exitBtn = new Button("Exit");
        playBtn = new Button("Play");
        VBox vbox = new VBox();
        vbox.setPrefWidth(width);
        vbox.setPrefHeight(height);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        vbox.getChildren().add(playBtn);
        vbox.getChildren().add(exitBtn);
        movingUp = true;
        movingDown = movingLeft = movingRight = false;
        
        // Intialization game objects
        fruit = new Fruit();
        snake = new Snake();
        
        // Creating game scene
        pane.getChildren().add(canvas);
        root.getChildren().add(pane);
        root.getChildren().add(vbox);
        root.setFocusTraversable(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake game");
        primaryStage.show();
        startGame();
        
        
        
        Label l = new Label();
        l.setText("Game over");
        l.setScaleX(2);
        l.setScaleY(2);
        Button b = new Button("Play again");
        
        // Animation
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), new EventHandler() {
            @Override
            public void handle(Event event) {
                eat.stop();
                hit.stop();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                
                snake.draw(gc);
                fruit.draw(gc);
                gc.fillText("Score " + score.toString(), 20, 20);
                if (snake.colision(fruit)) {
                    snake.eat();
                    eat.play();
                    fruit.relocate();
                    score++;
                
                }
                if (movingRight)
                    snake.move(0);
                else if (movingLeft)
                    snake.move(1);
                else if (movingDown)
                    snake.move(2);
                else if (movingUp)
                    snake.move(3);
                  
                if (snake.over(width, height)) {
                    hit.play();
                    
                    vbox.getChildren().add(l);
                    vbox.getChildren().add(b);
                    vbox.setSpacing(20);
                    timeline.stop();
                    snake = null;
                    score = 0;
                    
                }
            }
        }
               
        ));
        
        b.setOnMousePressed((MouseEvent e) -> {
            snake = new Snake();
            startGame();
            
            timeline.playFromStart();
            vbox.getChildren().remove(b);
            vbox.getChildren().remove(l);
                        
        });
        
        // 
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.RIGHT && !movingLeft) {
                movingRight = true;
                movingLeft = movingUp = movingDown = false;
            }
            if (event.getCode() == KeyCode.LEFT && !movingRight) {
                movingLeft = true;
                movingRight = movingUp = movingDown = false;
            }
            if (event.getCode() == KeyCode.DOWN && !movingUp) {
                movingDown = true;
                movingLeft = movingRight = movingUp = false;
            }
            if (event.getCode() == KeyCode.UP && !movingDown) {
                movingUp = true;
                movingLeft = movingRight = movingDown = false;
                
            }
            
            
        });
        
        playBtn.setOnAction((ActionEvent event) -> {
            System.out.println(event.getEventType().getName());
            timeline.playFromStart();
            vbox.getChildren().removeAll(playBtn, exitBtn);
        });
        
        exitBtn.setOnMouseClicked((MouseEvent event) -> {
            Platform.exit();
        });
    }
    
    public void startGame() {
        snake.draw(gc);
        fruit.draw(gc);
        gc.setFill(rgb(100, 100, 100, 0.5));
        gc.fillRect(0, 0, width, height);
        
    }
    
    public static void main(String[] argc) {
        App app = new App();
        app.launch(argc);
        
    } 

}

