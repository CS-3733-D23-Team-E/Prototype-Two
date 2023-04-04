package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.*;

import Database.DatabaseController;
import Database.DatabaseGraphController;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.MoveAttribute;
import edu.wpi.teame.map.pathfinding.AStarPathfinder;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class MapController {
  @FXML private MFXButton backButton;

  @FXML private MFXButton FirstFloorButton;

  @FXML private MFXButton SecondFloorButton;

  @FXML private MFXButton ThirdFloorButton;

  @FXML private MFXButton groundFloorButton;

  @FXML private MFXButton lowerLevelTwoButton;

  @FXML private AnchorPane anchorPane;

  @FXML private BorderPane borderPane;

  @FXML private ImageView map;

  @FXML private Pane mapPane;
  @FXML private ComboBox<String> currentLocationComboBox;
  @FXML private ComboBox<String> destinationComboBox;

  ArrayList<Line> currentLines = new ArrayList<>();
  ArrayList<Circle> currentCircles = new ArrayList<>();

  DatabaseGraphController graphController;

  @FXML
  public void initialize() {
    DatabaseController db = new DatabaseController("teame", "teame50");
    graphController = new DatabaseGraphController(db);
    setupCombobox();
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    ArrayList<String> listOfNames = new ArrayList<>();
    FXCollections.observableArrayList(listOfNames);
  }

  private void setupCombobox() {
    System.out.println(graphController.getMoveAttributeFromFloor(Floor.LOWER_ONE));
    List<MoveAttribute> allMoveAttributes =
        graphController.getMoveAttributeFromFloor(Floor.LOWER_ONE);

    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (MoveAttribute attr : allMoveAttributes) {
      locationNames.add(attr.longName);
    }
    currentLocationComboBox.setItems(locationNames);
    destinationComboBox.setItems(locationNames);
  }

  private void addLabels() {
  }

  @FXML
  public void createPath() {
    if (destinationComboBox.getValue() != null && currentLocationComboBox.getValue() != null) {
      refreshPath();
      AStarPathfinder pf = new AStarPathfinder();

      // test 2290 >>>>> 1705
      String from = currentLocationComboBox.getValue();
      String to = destinationComboBox.getValue();

      String toNodeID = graphController.getNodeIDFromName(to) + "";
      String fromNodeID = graphController.getNodeIDFromName(from) + "";

      List<HospitalNode> path =
          pf.findPath(HospitalNode.allNodes.get(fromNodeID), HospitalNode.allNodes.get(toNodeID));

      if (path == null) {
        System.out.println("Path does not exist");
        return;
      }
      drawPath(path);
    }
  }

  private double convertYCoord(int yCoord) {
    double paneHeight = mapPane.getHeight();
    double mapHeight = 3400;

    return yCoord * (paneHeight / mapHeight);
  }

  private double convertXCoord(int xCoord) {
    double paneWidth = mapPane.getWidth();
    double mapWidth = 5000;

    return xCoord * (paneWidth / mapWidth);
  }

  /**
   * draws the path with lines connecting each node on the map
   *
   * @param path
   */
  private void drawPath(List<HospitalNode> path) {

    // create circle to symbolize start
    int x1 = path.get(0).getXCoord();
    int y1 = path.get(0).getYCoord();
    Circle startCircleOutside = new Circle(convertXCoord(x1), convertYCoord(y1), 4);
    startCircleOutside.setFill(BLACK);
    Circle startCircleInside = new Circle(convertXCoord(x1), convertYCoord(y1), 3);
    startCircleInside.setFill(WHITE);
    mapPane.getChildren().add(startCircleOutside);
    mapPane.getChildren().add(startCircleInside);
    currentCircles.add(startCircleInside);
    currentCircles.add(startCircleOutside);

    // draw the lines between each node
    int x2, y2;
    for (int i = 1; i < path.size(); i++) {
      HospitalNode node = path.get(i);
      x2 = node.getXCoord();
      y2 = node.getYCoord();

      drawLine(x1, y1, x2, y2);

      x1 = x2;
      y1 = y2;
    }

    // create circle to symbolize end
    Circle endCircle = new Circle(convertXCoord(x1), convertYCoord(y1), 4);
    endCircle.setFill(BLACK);
    mapPane.getChildren().add(endCircle);
    currentCircles.add(endCircle);
  }

  /**
   * draws a line given the starting and ending (x,y) coordinates and saves the line to a global
   * list of lines
   */
  private void drawLine(int x1, int y1, int x2, int y2) {
    x1 = (int) this.convertXCoord(x1);
    y1 = (int) this.convertYCoord(y1);
    x2 = (int) this.convertXCoord(x2);
    y2 = (int) this.convertYCoord(y2);

    Line line = new Line(x1, y1, x2, y2);
    mapPane.getChildren().add(line);
    currentLines.add(line);
  }

  /** removes all the lines in the currentLines list */
  public void refreshPath() {
    mapPane.getChildren().removeAll(currentCircles);
    mapPane.getChildren().removeAll(currentLines);
  }

  public void createTriangle(Pane pane, double x, double y, double size) {
    Polygon triangle = new Polygon();

    double cos120 = -0.5;
    double sizeSquared = size * size;
    double length = Math.sqrt(sizeSquared + sizeSquared - 2 * sizeSquared * cos120);

    triangle.getPoints().addAll(new Double[] {x, y + size, x - length / 2, y});

    pane.getChildren().add(triangle);
  }
}
