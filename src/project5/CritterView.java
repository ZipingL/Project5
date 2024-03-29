/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */

package project5;
import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project5.Controller.commandType;


//import com.sun.java.util.jar.pack.Package.Class.Method;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class CritterView extends Application {
	
	Canvas canvas = null;
	Stage globalStage = null;
	BorderPane border = null;

	
	ListView<String> listSteps = null;
	ListView<String> listCritters = null;
	
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
	    	
	    	globalStage = primaryStage;
	    	
	    	if(primaryStage.equals(null)) return;
	        
	        primaryStage.setTitle("CritterSim");
	        
	        Button btn = new Button();
	        
	        btn.setText("CritterSim");
	        ButtonEventHandler handler = new ButtonEventHandler();
	        btn.setOnAction(handler);
	        
	        border = new BorderPane();
	        VBox vbox = addVBox();
	        border.setLeft(vbox);
	        
	        HBox hbox = addHBox();
	        border.setBottom(hbox);
	        
	        canvas = new Canvas(Params.world_height, Params.world_width);
	        border.setCenter(canvas);
	        globalStage.setScene(new Scene(border, 300, 250));
	        globalStage.show();
	    }
	    
	    private HBox addHBox() {
	    	
	    	HBox hbox = new HBox();
	        hbox.setPadding(new Insets(15, 12, 15, 12));
	        hbox.setSpacing(10);
	        hbox.setStyle("-fx-background-color: #ffa500;");
	    	
	    	Text title = new Text("Statistics");
	    	Text stats = new Text(CritterWorld.statOutput == null ? "No data" : CritterWorld.statOutput);
	    	
	        hbox.getChildren().addAll(title, stats);
	        
	        return hbox;
	    }
	    
	    private VBox addVBox() {
	    	listSteps = new ListView<String>();
	    	ObservableList<String> itemsSteps =FXCollections.observableArrayList (
	    	    "1", "25", "100", "1000");
	    	listSteps.setItems(itemsSteps);
	    	
	    	listCritters = new ListView<String>();
	    	ObservableList<String> itemsCritters =FXCollections.observableArrayList (
	    	    "Craig", "Ankit", "Sharma", "Ziping", "Liu", "All");
	    	listCritters.setItems(itemsCritters);
	    	
	    	VBox vbox = new VBox();
	        vbox.setPadding(new Insets(15, 12, 15, 12));
	        vbox.setSpacing(10);
	        vbox.setStyle("-fx-background-color: #ffa500;");
	    	
	        Button buttonMake = new Button("Make");
	        buttonMake.setPrefSize(100, 20);
			buttonMake.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	String input[] = getSelectedChoices();
			        Controller.runCommand(input[1], Integer.parseInt(input[0]), commandType.MAKE);
			    }
			});
	        Button buttonSeed = new Button("Seed");
	        buttonSeed.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	
			    	 Controller.runCommand(null, (long) Critter.getRandomInt(1000), commandType.SEED);
			    }
			});;
	        buttonSeed.setPrefSize(100, 20);
	        Button buttonQuit = new Button("Quit");
	        buttonQuit.setPrefSize(100, 20);
	        buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {

			        System.exit(0);
			    }
			});
	        
	        Button buttonStep = new Button("Step");
	        buttonStep.setPrefSize(100, 20);
	        buttonStep.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	String input[] = getSelectedChoices();
			    	Controller.runCommand(null, Integer.parseInt(input[0]), commandType.STEP);
			    	
			    	clear(canvas);
			    	GraphicsContext gc = canvas.getGraphicsContext2D();
			    	drawShapes(gc);
			        border.setCenter(canvas);
			        
			        Controller.runCommand(input[1], 0, commandType.STATS);
			        
			        
			        HBox hbox = addHBox();
			        border.setBottom(hbox);
			        
			        
			        globalStage.setScene(new Scene(border, 300, 250));
			        globalStage.show();
			    }
			});
	        
	        Button buttonProject4Show = new Button("Project 4 Show");
	        buttonProject4Show.setPrefSize(100, 20);
	        buttonProject4Show.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {

			    	Controller.runCommand(null, 0, commandType.SHOW);
			    }
			});
	        vbox.getChildren().addAll(buttonMake, buttonSeed, buttonQuit, buttonStep, buttonProject4Show, listSteps, listCritters);
	        
	    	return vbox;
	    }
	    
	     String[] getSelectedChoices()
	    {	
	    	String[] inputs = new String[2];
	    	inputs[0] = listSteps.getSelectionModel().getSelectedItem();
	    	inputs[1] = listCritters.getSelectionModel().getSelectedItem();
	    	
	    	return inputs;
	    }
	    
	    class ButtonEventHandler implements EventHandler<ActionEvent> {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		System.out.println("Fuck you");
	   
	    	}
	    }
	    	
	    private void drawShapes(GraphicsContext gc) {
	        
	    	if(CritterWorld.critterList == null) return;
	    	
	    	
	    	
	    	for(Critter c : CritterWorld.critterList) {
	    		
	    		int x = c.x_coord;
	    		int y = c.y_coord;
	    		
	    		
	    		
	    		switch(c.viewShape()) {
	    		case SQUARE:
	    			gc.setStroke(c.viewColor());
	    			gc.strokeRect((double) x, (double) y, 5, 5);
	    			break;
	    		case CIRCLE:
	    			gc.setStroke(c.viewColor());
	    			gc.strokeOval((double) x, (double) y, 5, 5);
	    			break;

//	    		case DIAMOND:
//	    			double[] DiamondxPoints = { c.x_coord - 3, c.x_coord, c.x_coord, c.x_coord + 3 };
//					double[] DiamondyPoints = { c.y_coord, c.y_coord - 3, c.y_coord + 3, c.y_coord };
//					int DiamondnPoints = 4;
//					gc.setStroke(c.viewColor());
//					gc.strokePolygon(DiamondxPoints, DiamondyPoints, DiamondnPoints);
//					break;
//	    		case STAR:
//	    			
//	    			double[] StarxPoints = { c.x_coord, c.x_coord + 1, c.x_coord + 3, c.x_coord + 1,
//												c.x_coord, c.x_coord - 1, c.x_coord - 3, c.x_coord - 1 };
//					double[] StaryPoints = { c.y_coord + 3, c.y_coord + 1, c.y_coord, c.y_coord - 1,
//												c.y_coord - 3, c.y_coord - 1, c.y_coord, c.y_coord + 1 };
//					int StarnPoints = 8;
//					gc.setStroke(c.viewColor());
//					gc.strokePolygon(StarxPoints, StaryPoints, StarnPoints);
//					
//
//					//System.out.println("DIAMOND DOGS");
//					break;
//	    		case TRIANGLE:
//
//	    			double[] TrianglexPoints = { c.x_coord - 3, c.x_coord, c.x_coord, c.x_coord + 3 };
//					double[] TriangleyPoints = { c.y_coord, c.y_coord - 3, c.y_coord + 3, c.y_coord };
//					int TrianglenPoints = 4;
//					gc.setStroke(c.viewColor());
//					gc.strokePolygon(TrianglexPoints, TriangleyPoints, TrianglenPoints);
//					break;
	    		
	    		default:
	    			break;
	    		}
	    		
	    		
	    	}
	    	
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

