/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */

package project5;
import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

//import com.sun.java.util.jar.pack.Package.Class.Method;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CritterView extends Application {
	
	 public static void main(String[] args) {
	        launch(CritterView.class, args);
	    }
	    
	    private void clear(Canvas canvas) {
	    	GraphicsContext gc = canvas.getGraphicsContext2D();
	    	gc.setFill(Color.WHITE);
	    	gc.fillRect(0,  0,  canvas.getWidth(), canvas.getHeight());
	    }
	 
	    @Override
	    public void start(Stage primaryStage) {
	    	
	    	if(primaryStage.equals(null)) return;
	        
	        primaryStage.setTitle("CritterSim");
	        
	        Button btn = new Button();
	        
	        btn.setText("CritterSim");
	        ButtonEventHandler handler = new ButtonEventHandler();
	        btn.setOnAction(handler);
	        
	        BorderPane border = new BorderPane();
	        VBox vbox = addVBox();
	        border.setLeft(vbox);
	        
	        Canvas canvas = new Canvas(600, 500);
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        drawShapes(gc);
	        border.setCenter(canvas);
	        primaryStage.setScene(new Scene(border, 300, 250));
	        
	        primaryStage.show();
	    
	    }
	    
	    private VBox addVBox() {
	    	VBox vbox = new VBox();
	        vbox.setPadding(new Insets(15, 12, 15, 12));
	        vbox.setSpacing(10);
	        vbox.setStyle("-fx-background-color: #336699;");
	    	
	        Button buttonMake = new Button("Make");
	        buttonMake.setPrefSize(100, 20);
	        Button buttonSeed = new Button("Seed");
	        buttonSeed.setPrefSize(100, 20);
	        Button buttonQuit = new Button("Quit");
	        buttonQuit.setPrefSize(100, 20);
	        
	        vbox.getChildren().addAll(buttonMake, buttonSeed, buttonQuit);
	        
	    	return vbox;
	    }
	    
	    class ButtonEventHandler implements EventHandler<ActionEvent> {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		Controller.hang();
	    	}
	    }
	    	
	    private void drawShapes(GraphicsContext gc) {
	        gc.setFill(Color.GREEN);
	        gc.setStroke(Color.BLUE);
	        gc.setLineWidth(5);
	        gc.strokeLine(40, 10, 10, 40);
	        gc.fillOval(10, 60, 30, 30);
	        gc.strokeOval(60, 60, 30, 30);
	        gc.setLineWidth(1);
	        gc.strokeOval(150, 60, 10, 10);
	        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
	        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
	        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
	        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
	        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
	        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
	        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
	        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
	        gc.fillPolygon(new double[]{10, 40, 10, 40},
	                       new double[]{210, 210, 240, 240}, 4);
//	        gc.strokePolygon(new double[]{60, 90, 60, 90},
//	                         new double[]{210, 210, 240, 240}, 4);
//	        gc.strokePolyline(new double[]{110, 140, 110, 140},
//	                          new double[]{210, 210, 240, 240}, 4);
	    }
	        

	        /* the timer class (below) shows a simple way to get animation
	         * You can clear a canvas by drawing a white rectangle (see clear below)
	         * and then redraw all your objects, and you can trigger that behavior inside the 
	         * timer's handle method.
	         * 
	         * You can stop a timer by calling the stop method and start it with the start method
	         * (starting and stopping an animation are good things to do with buttons, push
	         * a button to start the timer, push another to stop it).
	         * 
	         * Anyway, this is included to get your ideas flowing
	         */
//	        Timer timer = new Timer();
//	        timer.start();
//		}

	    
	    /* work with the examples below.
	     * Change the size and colors of the shapes
	     * Draw a triangle 
	     */
	    
	    class Timer extends AnimationTimer {
	    	
	    	@Override
	    	public void handle(long counter) {
	    		System.out.print("Tick\n");
	    		System.out.flush();
	    	}
	    }
	
}

