import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WebJsonGrabber {
  public static void main(String[] args) {
    // Initiates Variables
    // looks good - comment to test workflow
    Connection conn;
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<StackOverFlowJobPost> stackJobLists = new ArrayList<StackOverFlowJobPost>();
    URLDownloader downloader = new URLDownloader();
    SQLiteDBManager sqlDBManager = new SQLiteDBManager();

    String rssURL = "https://stackoverflow.com/jobs/feed";
    String url = "https://jobs.github.com/positions.json?page=";
    String dbLocation = "jdbc:sqlite:jobPosts.db";

    // Check for the files existence
    File dbFileCheck = new File("jobPosts.db");
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
    sqlDBManager.dbConnection(dbLocation);

    // Create the initial fields in the db and establish a connection
    sqlDBManager.dbManager(sqlCreate);

    // Gson Configuration
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    // Fill job lists with every post formatted to the object for github and stackOverflow
    jobLists = downloader.gitJsonToList(gson, url);
    stackJobLists = downloader.stackXMLToList(rssURL);
    // add git to DB
    try {
      sqlDBManager.gitJsonAddToDB(jobLists);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Add Stack to DB
    try {
      sqlDBManager.stackXMLAddToDB(stackJobLists);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Post run test functions commented out
    // sqlDBManager.printFullDBKeys();
    /*
    for (StackOverFlowJobPost test : stackJobLists) {
      System.out.println(test.getCategory());
    }
    */
    /*
    // Stores job info into 1 json file
    fileWriter(gson, jobLists);
    */

  }
  // unused from old code
  public static void fileWriter(Gson gson, List<JobPost> jobLists) {
    try {
      gson.toJson(jobLists, new FileWriter("jobposts.json"));
    } catch (IOException x) {
      x.printStackTrace();
    }
  }
}
