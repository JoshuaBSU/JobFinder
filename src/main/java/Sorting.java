import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
  // Checks on the company column field
  public List<DatabaseEntry> companySearch(List<DatabaseEntry> dbListRaw, String company) {
    List<DatabaseEntry> dbListSorted = new ArrayList<>();

    for (DatabaseEntry jobfilter : dbListRaw) {
      if (jobfilter.getCompany() != null) {
        if (jobfilter.getCompany().toLowerCase().contains(company.toLowerCase())) {
          dbListSorted.add(jobfilter);
        }
      }
    }
    return dbListSorted;
  }

  // Both required some changes to the timezone format as it is not a recognized format when running
  // bellow command
  // System.out.println(Arrays.toString(TimeZone.getAvailableIDs()));
  public List<DatabaseEntry> dateSearchOlderThan(List<DatabaseEntry> dbListRaw, String dateinput) {
    List<DatabaseEntry> dbListSorted = new ArrayList<>();
    Date date2 = new Date();
    DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    if (dateinput != null) {
      try {
        Date dateToCheckAgainst = format.parse(dateinput.replaceAll("Z$", "PST"));
        for (DatabaseEntry jobfilter : dbListRaw) {
          if (jobfilter.getCreated_at() != null) {
            try {
              date2 = format.parse(jobfilter.getCreated_at().replaceAll("Z$", "PST"));
            }catch (ParseException e)
            {
              DateFormat format2 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
              date2 = format2.parse(jobfilter.getCreated_at().replaceAll("Z$", "PST"));
            }
            if (date2.getTime() < dateToCheckAgainst.getTime()) {
              dbListSorted.add(jobfilter);
            }
          }
        }
        return dbListSorted;
      } catch (ParseException e) {
        e.printStackTrace();
        System.out.println("Invalid date entry list unchanged");
        return dbListRaw;
      }
    }
    return dbListSorted;
  }

  public List<DatabaseEntry> dateSearchYoungerThan(List<DatabaseEntry> dbListRaw, String dateinput) {
    List<DatabaseEntry> dbListSorted = new ArrayList<>();
    Date date2 = new Date();
    DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    if (dateinput != null) {
      try {
        Date dateToCheckAgainst = format.parse(dateinput.replaceAll("Z$", "PST"));
        for (DatabaseEntry jobfilter : dbListRaw) {
          if (jobfilter.getCreated_at() != null) {
            try {
              date2 = format.parse(jobfilter.getCreated_at().replaceAll("Z$", "PST"));
            }catch (ParseException e)
            {
              DateFormat format2 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
              date2 = format2.parse(jobfilter.getCreated_at().replaceAll("Z$", "PST"));
            }
            if (date2.getTime() > dateToCheckAgainst.getTime()) {
              dbListSorted.add(jobfilter);
            }
          }
        }
        return dbListSorted;
      } catch (ParseException e) {
        e.printStackTrace();
        System.out.println("Invalid date entry list unchanged");
        return dbListRaw;
      }
    }
    return dbListSorted;
  }
}
