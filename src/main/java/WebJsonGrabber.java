import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebJsonGrabber {
  public static void main(String[] args) throws IOException {
    // Initiates Variables
    // looks good - comment to test workflow

    //bool vars to help smooth out compile times when certain modules don't need to run
    boolean uniquePopulateTable = false;
    boolean runDownloaders = false;
    boolean runGeoCoder = false;
    boolean runGui = true;
    //split up functions to reduce reruns of network dependent functions
    PrimaryCode(runDownloaders,runGeoCoder,uniquePopulateTable,runGui);
    //MapGUI test = new MapGUI();
    //test.WindowMaker();
  }

  public static void PrimaryCode(boolean runWebScrapper, boolean runGeoCoder,boolean uniquePopulateTable, boolean runGui) throws IOException {
    Connection conn;
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<StackOverFlowJobPost> stackJobLists = new ArrayList<StackOverFlowJobPost>();
    URLDownloader downloader = new URLDownloader();
    SQLiteDBManager sqlDBManager = new SQLiteDBManager();
    MapGUI test = new MapGUI();
    UniqueGeoCoder geocode = new UniqueGeoCoder();

    //test.WindowMaker();

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
                    + " company_logo text,\n"
                    + " category text,\n"
                    + " coordinates text\n"
                    + " );";
    String sqlCreateUniques =
            "CREATE TABLE IF NOT EXISTS uniqueLocations (\n"
                    + " location text PRIMARY KEY ON CONFLICT IGNORE,\n"
                    + " longitude text,\n"
                    + " latitude text\n"
                    + " );";
    // let this dbManager instance know what file to access
    sqlDBManager.dbConnection(dbLocation);
    sqlDBManager.dbManager(sqlCreateUniques);
    sqlDBManager.dbManager(sqlCreate);

    // Gson Configuration
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    if (runWebScrapper)
    {
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
    }

    //not resource effecient, but without pointers its one of the quicker ways
    if (uniquePopulateTable)
    {
      //first run functions to populate the table prior to geocoding
      sqlDBManager.uniqueTransferToSecondDB(sqlDBManager.getUniqueLocationsFromPrimaryList());
    }
    //test
    //sqlDBManager.testUpdateStatement();

    if (runGeoCoder)
    {
      //update unique location db, handle remotes and things like that
      sqlDBManager.geoCodeSecondDB();
      //updates the actual database
      sqlDBManager.tableJoinPrimaryWithUniques();
    }

    //!!-- Testing Area --!!

    //Sorting algorithm takes in a List and sends back a list so we can use the function on itself
    List<DatabaseEntry> jobsBeingSorted =  sqlDBManager.returnEntireDB();

    Sorting sorter = new Sorting();
    String tempCategory = "ruby";
    //jobsBeingSorted = sorter.categorySort(jobsBeingSorted,tempCategory );

    /*
    for(DatabaseEntry jobprintout : jobsBeingSorted)
    {
      System.out.print(jobprintout.getId());
    }
    */

    //!!-- Testing Area --!!

    //System.out.println(geocode.forward("Boston"));
    if (runGui)
    {
      test.WindowMaker(jobsBeingSorted);
    }

    sqlDBManager.dbConnectionClose();
  }
}
