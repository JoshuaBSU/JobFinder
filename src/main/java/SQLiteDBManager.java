import java.sql.*;
import java.util.*;

public class SQLiteDBManager {
  private Connection conn = null;

  // creates a connection and returns it so we can manually close if needed.
  public void dbConnection(String dbLocation) {
    try {
      // Setting up forName so commands don't get thrown into an error
      Class.forName("org.sqlite.JDBC");
      // create connection and statement
      conn = DriverManager.getConnection(dbLocation);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return conn;
  }

  public void dbConnectionClose() {
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Send commands to DB without any return information
  public void dbManager(String sqlCommand) {
    try {
      // create connection and statement
      Statement statement = conn.createStatement();
      statement.execute(sqlCommand);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // manually close connection later
  }

  // Lets us add git formatted lists into our db
  public void gitJsonAddToDB(List<JobPost> jobLists) throws SQLException {
    // prepared statement
    String sql =
        "Insert Into jobListings(id,type,url,created_at,company,company_url,location,title,description,how_to_apply,company_logo,category,coordinates) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      {
        for (JobPost jobList : jobLists) {
          preparedStatement.setString(1, jobList.getId());
          preparedStatement.setString(2, jobList.getType());
          preparedStatement.setString(3, jobList.getUrl());
          preparedStatement.setString(4, jobList.getCreated_at());
          preparedStatement.setString(5, jobList.getCompany());
          preparedStatement.setString(6, jobList.getCompany_url());
          preparedStatement.setString(7, jobList.getLocation());
          preparedStatement.setString(8, jobList.getTitle());
          preparedStatement.setString(9, jobList.getDescription());
          preparedStatement.setString(10, jobList.getHow_to_apply());
          preparedStatement.setString(11, jobList.getCompany_logo());
          preparedStatement.setString(12, null);
          preparedStatement.setString(13, null);
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // WIP
  public void stackXMLAddToDB(List<StackOverFlowJobPost> stackJobList) throws SQLException {
    // prepared statement
    String sql =
        "Insert Into jobListings(id,type,url,created_at,company,company_url,location,title,description,how_to_apply,company_logo,category,coordinates) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      {
        // nulled out some information to get it to fit with our current table
        for (StackOverFlowJobPost stackJobs : stackJobList) {
          preparedStatement.setString(1, stackJobs.getGuid());
          preparedStatement.setString(2, null);
          preparedStatement.setString(3, stackJobs.getLink());
          preparedStatement.setString(4, stackJobs.getPubDate());
          preparedStatement.setString(5, stackJobs.getAuthor());
          preparedStatement.setString(6, null);
          preparedStatement.setString(7, stackJobs.getLocation());
          preparedStatement.setString(8, stackJobs.getTitle());
          preparedStatement.setString(9, stackJobs.getDescription());
          preparedStatement.setString(10, null);
          preparedStatement.setString(11, null);
          preparedStatement.setString(12, stackJobs.getCategory());
          preparedStatement.setString(13, null);
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // creates a blank database
  public void blankDBMaker(String dbLocation) {
    try {
      // Setting up forName so commands don't get thrown into an error
      Class.forName("org.sqlite.JDBC");
      // create connection and statement
      conn = DriverManager.getConnection(dbLocation);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  // Prints out all keys
  public void printFullDBKeys() {
    String sqlStatement = "SELECT id FROM jobListings";
    if (conn != null) {

      try {
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatement);
        while (results.next()) {
          System.out.print(results.getString("id") + "\t");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

    } else {
      System.out.println("Connection was closed unexpectedly, exiting");
      System.exit(0);
    }
  }

  public boolean checkIfJobListByID(String id) {
    String sqlStatement =
        "SELECT id, type, url, created_at, company, company_url, location,title, description, how_to_apply, company_logo, category, coordinates FROM jobListings";
    if (conn != null) {

      try {
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatement);
        while (results.next()) {
          if (results.getString("id").equals(id)) {
            return true;
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Connection was closed unexpectedly, exiting");
      System.exit(0);
    }
    return false;
  }
  // Creating a hashmap for all the locations to cut down on queries to a geoCoder
  public void getUniqueLocations() {
    Map<String, String> uniqueLocations = new HashMap<String, String>();
    String sqlStatement = "SELECT location, coordinates FROM jobListings";
    if (conn != null) {

      try {
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatement);
        while (results.next()) {
          results.getString("location");
          if (results.getString("coordinates") == null
              && uniqueLocations.get(results.getString("location")) == null
              && (results.getString("location") != null)
              && !(results.getString("location").contains("remote"))) {
            uniqueLocations.put(
                results.getString("location"), geoCoder(results.getString("location")));
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Connection was closed unexpectedly, exiting");
      System.exit(0);
    }
    // quick test
    int countOfTotalLocations = 0;

    for (Map.Entry<String, String> entry : uniqueLocations.entrySet()) {
      String key = entry.getKey();
      Object val = entry.getValue();
      System.out.println("Test our hashmap :" + key + "  " + val);
      countOfTotalLocations++;
    }
    System.out.println(countOfTotalLocations);
  }
  // our intermediary between the hashmap and GeoCoder
  public String geoCoder(String location) {
    UniqueGeoCoder encoding = new UniqueGeoCoder();
    return encoding.Forward(location);
  }

  public void updateCoordinates() {}
}
