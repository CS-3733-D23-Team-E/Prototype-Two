package edu.wpi.teame.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/HomePage.fxml"),

  SERVICE_REQUESTS("views/ServiceRequestPage.fxml"),

  SIGNAGE_TEXT("views/SignagePage.fxml"),
  MEAL_REQUEST("views/MealRequest.fxml"),
  FLOWER_REQUEST("views/FlowerRequest.fxml"),
  MAP("views/Map.fxml"),
  DATABASE_VIEW("views/DatabaseEditor.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
