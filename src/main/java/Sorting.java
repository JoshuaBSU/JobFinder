import java.util.ArrayList;
import java.util.List;

public class Sorting {

  public List<DatabaseEntry> categorySort(List<DatabaseEntry> dbListRaw, String category) {
    List<DatabaseEntry> dbListSorted = new ArrayList<>();

    for (DatabaseEntry jobfilter : dbListRaw) {
      if (jobfilter.getCategory() != null) {
        if (jobfilter.getCategory().toLowerCase().contains(category.toLowerCase())) {
          dbListSorted.add(jobfilter);
        }
      }
    }

    // iterate over dblistraw and assign it to dbListSorted
    return dbListSorted;
  }

  // Taken from stack
  // https://stackoverflow.com/questions/15372705/calculating-a-radius-with-longitude-and-latitude
  public List<DatabaseEntry> coordinateSearch(
      List<DatabaseEntry> dbListRaw, Double longitude, Double latitude, Double distanceFrom) {
    //
    List<DatabaseEntry> dbListSorted = new ArrayList<>();

    for (DatabaseEntry jobfilter : dbListRaw) {
      if (jobfilter.getLatitude() != null && jobfilter.getLongitude() != null) {
        // convert everything to radians for it to work
        double latitude1 = latitude * (Math.PI / 180);
        double longitude1 = longitude * (Math.PI / 180);
        double latitude2 = Double.parseDouble(jobfilter.getLatitude()) * (Math.PI / 180);
        double longitude2 = Double.parseDouble(jobfilter.getLongitude()) * (Math.PI / 180);
        double distanceCalculated =
            (6371
                * Math.acos(
                    Math.sin(latitude1) * Math.sin(latitude2)
                        + Math.cos(latitude1)
                            * Math.cos(latitude2)
                            * Math.cos(longitude2 - longitude1)));
        if (distanceCalculated < distanceFrom) {
          dbListSorted.add(jobfilter);
        }
      }
    }
    return dbListSorted;
  }
}
