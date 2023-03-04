package application;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	MyColor [] myColors = MyColor.getMyColors();
	int sizeMyColor = myColors.length;
	
	public VBox addLeftVBox(double widthLeftCanvas, double heightCanvas, TilePane TP, MyColor color) {
		
		//VB Node
		VBox VB = new VBox();
		VB.setPrefWidth(widthLeftCanvas);
		VB.setPadding(new Insets(5));	//spacing of the border
		
		//label object L: "MyColor Palette"
		Label L = new Label("MyColor Palette");
		L.setPrefWidth(widthLeftCanvas);
		L.setTextFill(MyColor.WHITE.getJavaFXColor());
		L.setBackground(new Background(new BackgroundFill(Optional.ofNullable(color).orElse(MyColor.GREY).getJavaFXColor(), CornerRadii.EMPTY, Insets.EMPTY)));
		
		//add Label (L) and TilePane (TP) to VB
		VB.getChildren().add(L);
		VB.getChildren().add(TP);
		
		return VB;
	}
	
	public Canvas addCenterCanvas(double widthCanvas, double heightCanvas, MyColor color) {
		
		MyColor colorPicked = Optional.ofNullable(color).orElse(MyColor.WHITE);
		Canvas CV = new Canvas (widthCanvas, heightCanvas);
		GraphicsContext GC= CV.getGraphicsContext2D();	//used to draw, color, and write on the canvas
		
		GC.clearRect(0, 0, widthCanvas, heightCanvas);
		GC.setFill(colorPicked.getJavaFXColor());
		GC.fillRect(0, 0, widthCanvas, heightCanvas);
		
		double xText = 5.0;
		double yText = 20.0;
		GC.setStroke(colorPicked.invertColor());
		GC.setFont(Font.font("Calibri", 13));
		GC.strokeText(colorPicked.toString(), xText, yText);
		
		return CV;
		
	}
	
	@Override
	public void start(Stage ps) {
			BorderPane root = new BorderPane();		//root node 
			Pane leftPane = new Pane();
			Pane centerPane = new Pane();
			
			double widthCanvas = 600;
			double heightCanvas = 300;
			double widthLeftCanvas = 0.4 * widthCanvas;
			double widthCenterCanvas = widthCanvas - widthLeftCanvas;
			
			
			MyColorPalette CP = new MyColorPalette(widthLeftCanvas, heightCanvas);
			TilePane TP = CP.getPalette();
			
			leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCanvas, TP, MyColor.BLACK));
			root.setLeft(leftPane);
			
			centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, null));
			root.setCenter(centerPane);
			
			//On mouse click: lambda expression
			TP.setOnMouseClicked(e -> {
				
				MyColor color = CP.getColorPicked();
				String tileId = color.toString();
				for (Node tile : TP.getChildren()) {
					if(tile.getId() == tileId) {	//if the expression is true, set the centerPane with color
						centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, color));
						root.setCenter(centerPane);
						break;
					}
				}
			});
			
			//create scene, set scene to the stage, and show stage
			Scene scene = new Scene(root, widthCanvas, heightCanvas);
			ps.setTitle("MyColor");
			ps.setScene(scene);
			ps.show();
			
	}
	
	//static method requires extend Application above
	public static void main(String[] args) {
		launch(args);
	}
}
