package application;

import java.util.Arrays;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class MyColorPalette {
	
	
	//Instance variables
	double widthTile;
	double heightTile;
	
	//Objects of MyColor type
	MyColor colorPicked;	
	MyColor [] colors = MyColor.getMyColors();
	
	int sizeMyColor = colors.length;
	
	
	public MyColorPalette(double widthPalette, double heightPalette) {
		this.widthTile = widthPalette/ 14;	//width of the tile
		this.heightTile = widthPalette/ 14;		//height of the tile
	}
	
	
	//set method
	public void setColorPicked(MyColor color) {colorPicked = color;}
	
	//get method
	public MyColor getColorPicked() {return colorPicked;}
	
	
	public TilePane getPalette() {
		TilePane TP = new TilePane();
		TP.setPrefTileWidth(widthTile);
		TP.setPrefTileHeight(heightTile);
		TP.setPrefRows(10);
		TP.setOrientation(Orientation.HORIZONTAL);
		TP.setPadding(new Insets(5)); //spacing of the TP to the border
		
		for(int i = 0; i<sizeMyColor; i++) {
			MyColor color = colors[i];
			String tileId = color.toString();
			
			Pane tileMyColor = new Pane();
			tileMyColor.setId(tileId);
			tileMyColor.setBackground(new Background(new BackgroundFill(color.getJavaFXColor(), CornerRadii.EMPTY, Insets.EMPTY)));
			
			
			//on mouse click: lambda expression
			tileMyColor.setOnMouseClicked(e -> {
				
				MyColor colorClicked = colors[Arrays.asList(MyColor.getMyColorIds()).indexOf(tileId)];
				setColorPicked(colorClicked);
				
				
			});
			
			//add tileMyColor to TP
			TP.getChildren().add(tileMyColor);
			
		}
		
		return TP;
	}
	
	
	public ObservableList<Node> getTiles(){		//returns getPallete's components when changes occurs
		return getPalette().getChildren();	
	}
	
}
