import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class SQLiteDBManagerTest {

  @Test
  // check if a good data exists when passed in
  void SQLiteDBManager_GoodData() {
    Connection conn;
    List<JobPost> jobLists = new ArrayList<JobPost>();
    SQLiteDBManager sqlDBManager = new SQLiteDBManager();
    // make a test db since we are adding fake data
    String dbLocation = "jdbc:sqlite:jobPoststests.db";
    // Check for the files existence
    File dbFileCheck = new File("jobPoststest.db");
    if (!dbFileCheck.exists()) {
      sqlDBManager.blankDBMaker(dbLocation);
    }
    // basic table structure
    // added ignore to throw away duplicate primary ID
    String sqlCreate =
        "CREATE TABLE IF NOT EXISTS jobListings (\n"
            + " id text PRIMARY KEY ON CONFLICT IGNORE,\n"
            + " type text,\n"
            + " url text,\n"
            + " created_at text,\n"
            + " company text,\n"
            + " company_url text,\n"
            + " location text,\n"
            + " title text,\n"
            + " description text,\n"
            + " how_to_apply text,\n"
            + " company_logo text,\n"
            + " category text,\n"
            + " coordinates text\n"
            + " );";

    // let this dbManager instance know what file to access
    sqlDBManager.dbConnection(dbLocation);

    // Create the initial fields in the db and establish a connection
    sqlDBManager.dbManager(sqlCreate);

    try {
      sqlDBManager.gitJsonAddToDB(jobLists);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // create a test jobpost to look for
    JobPost jobPostTest =
        new JobPost(
            "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test");
    jobLists.add(jobPostTest);
    try {
      sqlDBManager.gitJsonAddToDB(jobLists);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    boolean testForID = sqlDBManager.checkIfJobListByID(jobPostTest.getId());
    assertTrue(testForID);
  }

  // check for a known stack overflow id
  @Test
  void SQLiteDBManager_GitHub_ShouldPass() {
    Connection conn = null;
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<StackOverFlowJobPost> stackJobLists = new ArrayList<StackOverFlowJobPost>();
    URLDownloader downloader = new URLDownloader();
    SQLiteDBManager sqlDBManager = new SQLiteDBManager();

    String url = "https://jobs.github.com/positions.json?page=";
    String dbLocation = "jdbc:sqlite:jobPostsTest1.db";

    // Check for the files existence
    File dbFileCheck = new File("jobPostsTest1.db");
    if (!dbFileCheck.exists()) {
      sqlDBManager.blankDBMaker(dbLocation);
    }

    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    // basic table structure
    // added ignore to throw away duplicate primary ID
    String sqlCreate =
        "CREATE TABLE IF NOT EXISTS jobListings (\n"
            + " id text PRIMARY KEY ON CONFLICT IGNORE,\n"
            + " type text,\n"
            + " url text,\n"
            + " created_at text,\n"
            + " company text,\n"
            + " company_url text,\n"
            + " location text,\n"
            + " title text,\n"
            + " description text,\n"
            + " how_to_apply text,\n"
            + " company_logo text,\n"
            + " category text,\n"
            + " coordinates text\n"
            + " );";

    // let this dbManager instance know what file to access
    sqlDBManager.dbConnection(dbLocation);

    // Create the initial fields in the db and establish a connection
    sqlDBManager.dbManager(sqlCreate);

    // Fill job lists with every post formatted to the object for github and stackOverflow
    jobLists = downloader.gitJsonToList(gson, url);

    // Add Stack to DB
    try {
      sqlDBManager.gitJsonAddToDB(jobLists);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //Random JobList ID is chosen and checked if it was added to the DB
    String testID = "";
    Random rand = new Random();
    int randomJobID = 1 + rand.nextInt(150);
    int i = 1;
    for(JobPost randJobSelection : jobLists)
    {
      if (i == randomJobID)
      {
        testID = randJobSelection.getId();
      }
    }
    System.out.println(testID);
    assertTrue(sqlDBManager.checkIfJobListByID(testID));
  }

  @Test
  void SQLiteDBManager_TableCheck_ShouldPass() throws IOException, SQLException {
    // test if we make a table properly

    SQLiteDBManager sqlDBManager = new SQLiteDBManager();
    String dbLocation = "jdbc:sqlite:tableCheck.db";
    sqlDBManager.dbConnection(dbLocation);
    String sqlCreate =
        "CREATE TABLE IF NOT EXISTS jobListings (\n"
            + " id text PRIMARY KEY ON CONFLICT IGNORE,\n"
            + " type text,\n"
            + " url text,\n"
            + " created_at text,\n"
            + " company text,\n"
            + " company_url text,\n"
            + " location text,\n"
            + " title text,\n"
            + " description text,\n"
            + " how_to_apply text,\n"
            + " company_logo text,\n"
            + " category text,\n"
            + " coordinates text\n"
            + " );";
    File dbFileCheck = new File("tableCheck.db");
    if (!dbFileCheck.exists()) {
      sqlDBManager.blankDBMaker(dbLocation);
    }
    sqlDBManager.dbConnection(dbLocation);
    sqlDBManager.dbManager(sqlCreate);

    Connection conn = sqlDBManager.getConnection();

    boolean tableExists = false;
    try {
      ResultSet results = conn.getMetaData().getTables(null, null, null, null);
      while (results.next()) {
        if (results.getString("TABLE_NAME").equals("jobListings")) {
          tableExists = true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    conn.close();
    assertTrue(tableExists);
  }
}
