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
  public Map<String, String> getUniqueLocationsFromPrimaryList()
  {
    Map<String, String> uniqueLocations = new HashMap<String, String>();
    String sqlStatement =
            "SELECT location, coordinates FROM jobListings";
    if (conn != null) {

      try {
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatement);
        while (results.next()) {
          results.getString("location");
          // If remote exists it will be the default, therefore it makes sense to not carry that over in the geocoder table
          if ( results.getString("coordinates") == null && uniqueLocations.get(results.getString("location")) == null && (results.getString("location") != null))
          {
            if(!(results.getString("location").contains("remote") || (results.getString("location").contains("Remote"))))
            {
              //removes second location and will be handled in matching
              if (results.getString("location").contains("or "))
              {
                String formattedString = results.getString("location");
                formattedString = formattedString.substring(0,formattedString.indexOf("or "));
                uniqueLocations.put(formattedString,null);
              }
              else if (results.getString("location").contains("("))
              {
                String formattedString = results.getString("location");
                formattedString = formattedString.substring(0,formattedString.indexOf("("));
                uniqueLocations.put(formattedString,null);
              }
              //Because // "Mountain View" // and potentially other non location data in location fields... WHY!?
              else if(results.getString("location").contains("/") || results.getString("location").contains("\\")){
                String formattedString = results.getString("location");
                if(results.getString("location").contains("/"))
                {
                  formattedString = formattedString.substring(0,formattedString.indexOf("/"));
                  uniqueLocations.put(results.getString("location"), null);
                }
                else
                {
                  formattedString = formattedString.substring(0,formattedString.indexOf("\\"));
                  uniqueLocations.put(results.getString("location"), null);
                }
              }else {
              uniqueLocations.put(results.getString("location"), null);
              }
            }
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Connection was closed unexpectedly, exiting");
      System.exit(0);
    }
    return uniqueLocations;
  }

  public void uniqueTransferToSecondDB(Map<String, String> locations)
  {
    String sqlStatement =
            "Insert Into uniqueLocations(location, longitude, latitude) VALUES(?,?,?)";
    try {
      PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
      {
        for (Map.Entry<String, String> entry : locations.entrySet()) {
          preparedStatement.setString(1, entry.getKey());
          preparedStatement.setString(2, null);
          preparedStatement.setString(3, null);
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    //code to insert into uniquelocations table

  }

  public void testUpdateStatement()
  {
    String sqlStatement = "SELECT location, longitude, latitude FROM uniqueLocations";
    String sqlStatementUpdate = "UPDATE uniqueLocations SET longitude = ? , "
            + "latitude = ? "
            + "WHERE location = ?";
    if (conn != null) {
      int total = 0;
      try {
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatement);
        while (results.next() && total < 2) {
          if (results.getString("Location") != null && results.getString("longitude") == null)
          {
              PreparedStatement preparedStatement = conn.prepareStatement(sqlStatementUpdate);
              preparedStatement.setString(3, results.getString("Location"));
              preparedStatement.setString(1, "hi");
              preparedStatement.setString(2, "bye");
              preparedStatement.executeUpdate();
              total++;
              System.out.println("Update Number:" + total);
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void geoCodeSecondDB()
  {
    String sqlStatement = "SELECT location, longitude, latitude FROM uniqueLocations";
    String sqlStatementUpdate = "UPDATE uniqueLocations SET longitude = ? , "
            + "latitude = ? "
            + "WHERE location = ?";
    if (conn != null) {
      int total = 0;
      try {
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatement);
        while (results.next()) {
          if (results.getString("Location") != null && results.getString("longitude") == null && results.getString("latitude") == null ){
            List<String> temporaryCordinates = new ArrayList<>();
            UniqueGeoCoder geocode = new UniqueGeoCoder();
            temporaryCordinates = geocode.forward(results.getString("Location"));

            if (temporaryCordinates != null){

              PreparedStatement preparedStatement = conn.prepareStatement(sqlStatementUpdate);
              if (temporaryCordinates.get(0) != null) {
                preparedStatement.setString(3, results.getString("Location"));
                preparedStatement.setString(1, temporaryCordinates.get(0).toString());
                preparedStatement.setString(2, temporaryCordinates.get(1).toString());
                preparedStatement.executeUpdate();
                System.out.println(temporaryCordinates.get(0) + temporaryCordinates.get(1));
              }
            }
            total++;
            System.out.println("Update Number:" + total);
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public Map<String, List<String>> getUniquedbInfo()
  {
    Map<String, List<String>> uniquesFullInfo = new HashMap<>();

    String sqlStatement =
          "SELECT location, longitude, latitude FROM uniqueLocations";
    if (conn != null) {
      try {
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatement);
        UniqueGeoCoder geocode = new UniqueGeoCoder();
        while (results.next() ) {
            List<String> cordinates = new ArrayList<String>();
            //run geocoder on
            cordinates.add(results.getString("longitude"));
            cordinates.add(results.getString("latitude"));
            uniquesFullInfo.put(results.getString("location"), cordinates);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Connection was closed unexpectedly, exiting");
      System.exit(0);
    }
    return uniquesFullInfo;
  }

  public void tableJoinPrimaryWithUniques()
  {
    //table matching, anything not handled is a remote
    String sqlStatementGrabUniques = "SELECT location, longitude, latitude FROM uniqueLocations";
    String sqlStatementUpdatePrimary = "UPDATE jobListings SET coordinates = ? "
            + "WHERE location = ?";
    int count = 0;
    if (conn != null) {
      try{
        Statement searchStatement = conn.createStatement();
        ResultSet results = searchStatement.executeQuery(sqlStatementGrabUniques);
        PreparedStatement preparedStatement = conn.prepareStatement(sqlStatementUpdatePrimary);
        while (results.next())
        {
          count++;
          preparedStatement.setString(2, results.getString("Location"));
          preparedStatement.setString(1, results.getString("longitude") + " "+ results.getString("latitude"));
          preparedStatement.executeUpdate();
          System.out.println("Update Number: " + count);
        }

      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }

}
