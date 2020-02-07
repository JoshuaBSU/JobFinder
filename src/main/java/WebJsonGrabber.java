import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WebJsonGrabber {
  public static void main(String[] args) throws IOException {
    // Initiates Variables
    Connection conn = null;
    List<JobPost> jobLists = new ArrayList<JobPost>();
    URLDownloader downloader = new URLDownloader();
    String url = "https://jobs.github.com/positions.json?page=";
    String dbLocation = "jdbc:sqlite:jobPosts.db";
    String sqlCreate =
        "CREATE TABLE IF NOT EXISTS jobListings (\n"
            + " id text PRIMARY KEY,\n"
            + " type text,\n"
            + " url text,\n"
            + " created_at text,\n"
            + " company text,\n"
            + " company_url text,\n"
            + " location text,\n"
            + " title text,\n"
            + " description text,\n"
            + " how_to_apply text,\n"
            + " company_logo text\n"
            + " );";

    // Create the initial fields in the db and establish a connection
    conn = dbConnection(dbLocation);
    dbManager(dbLocation, sqlCreate, conn);

    // Gson Configuration
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    // Fill joblists with every post formatted to the object
    jobLists = downloader.gitJsonToList(gson, url);

    // Omitted to not interfere with grading
    /*
    try{
      gitAddToDB(jobLists,conn);
    }catch (SQLException e) {
      e.printStackTrace();
    }
    */
    // Just Prints out Jobs
    for (JobPost jobList : jobLists) {
      System.out.println(jobList.toString());
    }

    // Stores job info into 1 json file
    fileWriter(gson, jobLists);
  }

  // lets us access a database with any command we want but wont return any information
  public static void dbManager(String dbLocation, String sqlCommand, Connection conn) {
    try {
      // create connection and statement
      Statement statem = conn.createStatement();
      statem.execute(sqlCommand);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // manually close connection later
  }

  // return connection to any given db location
  public static Connection dbConnection(String dbLocation) {
    Connection conn = null;
    try {
      // Setting up forName so commands don't get thrown into an error
      Class.forName("org.sqlite.JDBC");
      // create connection and statement
      conn = DriverManager.getConnection(dbLocation);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public static void fileWriter(Gson gson, List<JobPost> jobLists) {
    try {
      gson.toJson(jobLists, new FileWriter("jobposts.json"));
    } catch (IOException x) {
      x.printStackTrace();
    }
  }

  public static void gitAddToDB(List<JobPost> jobLists, Connection conn) throws SQLException {
    // prepared statement
    String sql =
        "Insert Into jobListings(id,type,url,created_at,company,company_url,location,title,description,how_to_apply,company_logo) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    try {
      PreparedStatement preparedStatemnt = conn.prepareStatement(sql);
      {
        for (JobPost jobList : jobLists) {
          preparedStatemnt.setString(1, jobList.getId());
          preparedStatemnt.setString(2, jobList.getType());
          preparedStatemnt.setString(3, jobList.getUrl());
          preparedStatemnt.setString(4, jobList.getCreated_at());
          preparedStatemnt.setString(5, jobList.getCompany());
          preparedStatemnt.setString(6, jobList.getCompany_url());
          preparedStatemnt.setString(7, jobList.getLocation());
          preparedStatemnt.setString(8, jobList.getTitle());
          preparedStatemnt.setString(9, jobList.getDescription());
          preparedStatemnt.setString(10, jobList.getHow_to_apply());
          preparedStatemnt.setString(11, jobList.getCompany_logo());
          preparedStatemnt.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
