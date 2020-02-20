import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SQLiteDBManagerTest {

  @Test
  // check if a good data exists when passed in
  void SQLiteDBManager_GoodData() {
    Connection conn = null;
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
            + " company_logo text\n"
            + " );";

    // let this dbManager instance know what file to access
    conn = sqlDBManager.dbConnection(dbLocation);

    // Create the initial fields in the db and establish a connection
    sqlDBManager.dbManager(dbLocation, sqlCreate, conn);

    try {
      sqlDBManager.gitJsonAddToDB(jobLists, conn);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // create a test jobpost to look for
    JobPost jobPostTest =
        new JobPost(
            "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test");
    jobLists.add(jobPostTest);
    try {
      sqlDBManager.gitJsonAddToDB(jobLists, conn);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    boolean testForID = sqlDBManager.checkIfJobListByID(jobPostTest.getId());
    assertTrue(testForID);
  }

  // check for a known stack overflow id
  @Test
  void SQLiteDBManager_StackOverflowData_ShouldPass() {
    Connection conn;
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<StackOverFlowJobPost> stackJobLists = new ArrayList<StackOverFlowJobPost>();
    URLDownloader downloader = new URLDownloader();
    SQLiteDBManager sqlDBManager = new SQLiteDBManager();

    String rssURL = "https://stackoverflow.com/jobs/feed";
    String dbLocation = "jdbc:sqlite:jobPostsTestScan.db";


    // Check for the files existence
    File dbFileCheck = new File("jobPostsTestScan.db");
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
            + " company_logo text\n"
            + " );";

    // let this dbManager instance know what file to access
    conn = sqlDBManager.dbConnection(dbLocation);

    // Create the initial fields in the db and establish a connection
    sqlDBManager.dbManager(dbLocation, sqlCreate, conn);

    // Gson Configuration
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    // Fill job lists with every post formatted to the object for github and stackOverflow
    stackJobLists = downloader.stackXMLToList(rssURL);

    // Add Stack to DB
    try {
      sqlDBManager.stackXMLAddToDB(stackJobLists, conn);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //known ID on Stack
    //https://stackoverflow.com/jobs/365391/senior-data-engineers-spark-scala-quieres-ser-indizen-technologies?a=1YxwRUeOQsX6&so_medium=Talent&so_source=TalentApi
    String testID = "365391";

    assertTrue(sqlDBManager.checkIfJobListByID(testID));
  }

  @Test
  void SQLiteDBManager_TableCheck_ShouldPass() throws IOException, SQLException {
    //test if we make a table properly

    SQLiteDBManager sqlDBManager = new SQLiteDBManager();
    String dbLocation = "jdbc:sqlite:jobPoststests.db";
    Connection conn = sqlDBManager.dbConnection(dbLocation);
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
                    + " company_logo text\n"
                    + " );";
    File dbFileCheck = new File("jobPoststests.db");
    if (!dbFileCheck.exists()) {
      sqlDBManager.blankDBMaker(dbLocation);
    }
    conn = sqlDBManager.dbConnection(dbLocation);
    sqlDBManager.dbManager(dbLocation,sqlCreate,conn);

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
  /*
  @Test(expected=NullPointerException.class)
  void SQLiteDBManager_BadData_ShouldFail() throws SQLException {
    //null fields
    List<StackOverFlowJobPost> badDataList = new ArrayList<StackOverFlowJobPost>();
    StackOverFlowJobPost badData = new StackOverFlowJobPost();
    badDataList.add(badData);

    SQLiteDBManager sqlDBManager = new SQLiteDBManager();
    String dbLocation = "jdbc:sqlite:jobPoststests.db";
    Connection conn = sqlDBManager.dbConnection(dbLocation);
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
                    + " company_logo text\n"
                    + " );";
    File dbFileCheck = new File("jobPoststests.db");
    if (!dbFileCheck.exists()) {
      sqlDBManager.blankDBMaker(dbLocation);
    }
    conn = sqlDBManager.dbConnection(dbLocation);
    sqlDBManager.dbManager(dbLocation,sqlCreate,conn);

    sqlDBManager.stackXMLAddToDB(badDataList, conn);
  }

   */
}
