package Database;

<<<<<<< HEAD
import edu.wpi.teame.entities.ServiceRequestData;
import lombok.Getter;
import org.json.JSONObject;

import java.util.List;

public class DatabaseServiceController {
  DatabaseController db;

  @Getter
  List<ServiceRequestData> serviceRequests;

  public DatabaseServiceController(DatabaseController db) {
    this.db = db;
  }

  public void addServiceRequestToDatabase(JSONObject requestData) {

  }

  public List<ServiceRequestData> retrieveRequestsFromTable() {
    return serviceRequests;
=======
public class DatabaseServiceController {
  DatabaseController db;

  public DatabaseServiceController(DatabaseController db) {
    this.db = db;
>>>>>>> parent of 84cdcbc (Merge remote-tracking branch 'origin/DatabaseServiceController' into NB_Submit2)
  }

}
