package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.*;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import pathfinding.AStarPathfinder;
import pathfinding.HospitalNode;

public class MapController {

  @FXML ImageView map;
  @FXML AnchorPane anchorPane;
  @FXML TextField fromTextField;
  @FXML TextField toTextField;
  @FXML MFXButton exitButton;
  @FXML MFXButton findPathButton;
  @FXML MFXButton refreshButton;

  ArrayList<Line> currentLines = new ArrayList<Line>();

  @FXML
  public void initialize() {

    //    navMouseSetup(exitButton);
    exitButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    findPathButton.setOnMouseClicked(event -> createPath());
    refreshButton.setOnMouseClicked(event -> refreshPath());
    this.addLabels();
  }

  private void addLabels() {
    Label label = new Label();
  }

  public void createPath() {
    if (!fromTextField.getText().equals("") && !toTextField.getText().equals("")) {
      System.out.println("stage x: " + anchorPane.getWidth());
      System.out.println("stage y:" + anchorPane.getHeight());

      AStarPathfinder pf = new AStarPathfinder();
      List<HospitalNode> path =
          pf.findPath(
              HospitalNode.allNodes.get("CLABS001L1"), HospitalNode.allNodes.get("CCONF002L1"));

      System.out.println("Path: " + path);
      drawPath(path);
    }
  }

  private double convertYCoord(int yCoord) {
    double paneHeight = anchorPane.getHeight();
    double mapHeight = 3400;

    return yCoord * (paneHeight / mapHeight);
  }

  private double convertXCoord(int xCoord) {
    double paneWidth = anchorPane.getWidth();
    double mapWidth = 5000;

    return xCoord * (paneWidth / mapWidth);
  }

  /**
   * draws the path with lines connecting each node on the map
   *
   * @param path
   */
  private void drawPath(List<HospitalNode> path) {
    int x1 = path.get(0).getxCoord();
    int y1 = path.get(0).getyCoord();
    Circle startCircleOutside = new Circle(convertXCoord(x1), convertYCoord(y1), 10);
    startCircleOutside.setFill(BLACK);
    Circle startCircleInside = new Circle(convertXCoord(x1), convertYCoord(y1), 8);
    startCircleInside.setFill(WHITE);
    anchorPane.getChildren().add(startCircleOutside);
    anchorPane.getChildren().add(startCircleInside);



    int x2, y2;

    for (int i = 1; i < path.size(); i++) {
      HospitalNode node = path.get(i);
      x2 = node.getxCoord();
      y2 = node.getyCoord();

      drawLine(x1, y1, x2, y2);
      System.out.println("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);

      x1 = x2;
      y1 = y2;
    }

    Circle endCircle = new Circle(convertXCoord(x1), convertYCoord(y1), 10);
    endCircle.setFill(BLACK);
    anchorPane.getChildren().add(endCircle);
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
    anchorPane.getChildren().add(line);
    currentLines.add(line);
  }

  /** removes all the lines in the currentLines list */
  public void refreshPath() {
    anchorPane.getChildren().removeAll(currentLines);
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