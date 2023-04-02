package edu.wpi.teame;

import Database.DatabaseController;
import pathfinding.HospitalNode;

public class Main {

  public static void main(String[] args) {
    DatabaseController db = new DatabaseController("teame", "teame50");
    db.retrieveFromTable();
    HospitalNode.processEdgeList(db.getHospitalEdges());
    System.out.println(HospitalNode.allNodes);

    App.launch(App.class, args);
  }

  // shortcut: psvm

}
