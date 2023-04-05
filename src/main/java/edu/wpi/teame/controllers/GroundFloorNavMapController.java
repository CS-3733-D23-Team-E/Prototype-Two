package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class GroundFloorNavMapController {
  @FXML private MFXButton FirstFloorButton;

  @FXML private MFXButton SecondFloorButton;

  @FXML private MFXButton ThirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton lowerLevelOneButton;

  @FXML private MFXButton lowerLevelTwoButton;

  @FXML
  public void initialize() {
    mouseSetup(lowerLevelOneButton);
    mouseSetup(lowerLevelTwoButton);
    mouseSetup(SecondFloorButton);
    mouseSetup(FirstFloorButton);
    mouseSetup(ThirdFloorButton);
    mouseSetup(backButton);
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_ONE));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_TWO));
    FirstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    SecondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    ThirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #192d5a; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }
}
