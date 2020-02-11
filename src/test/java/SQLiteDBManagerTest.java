import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

class SQLiteDBManagerTest {

  @Test
  //check if a good data exists when passed in
  void SQLiteDBManagerGoodData(){
    Connection conn = null;
    List<JobPost> jobLists = new ArrayList<JobPost>();
    SQLiteDBManager sqlDBManager = new SQLiteDBManager();
    //make a test db since we are adding fake data
    String dbLocation = "jdbc:sqlite:jobPoststests.db";
    //Check for the files existence
    File dbFileCheck = new File("jobPoststest.db");
    if(!dbFileCheck.exists())
    {
      sqlDBManager.blankDBMaker(dbLocation);
    }
    //basic table structure
    //added ignore to throw away duplicate primary ID
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

    //let this dbManager instance know what file to access
    conn = sqlDBManager.dbConnection(dbLocation);

    // Create the initial fields in the db and establish a connection
    sqlDBManager.dbManager(dbLocation, sqlCreate, conn);

    try{
      sqlDBManager.gitJsonAddToDB(jobLists,conn);
    }catch (SQLException e) {
      e.printStackTrace();
    }
    //create a test jobpost to look for
    JobPost jobPostTest = new JobPost("test","test","test","test","test","test","test","test","test","test","test");
    jobLists.add(jobPostTest);
    try{
      sqlDBManager.gitJsonAddToDB(jobLists,conn);
    }catch (SQLException e) {
      e.printStackTrace();
    }
    boolean testForID = sqlDBManager.checkIfJobListByIDandURL(jobPostTest);
    assertTrue(testForID);
  }

  @Test
  void SQLTableCheck() throws IOException {
    String[] a = new String[0];
    WebJsonGrabber test = new WebJsonGrabber();
    WebJsonGrabber.main(a);

    SQLiteDBManager sqlDBManager = new SQLiteDBManager();
    String dbLocation = "jdbc:sqlite:jobPosts.db";
    Connection conn = sqlDBManager.dbConnection(dbLocation);

    boolean tableExists = false;
    try {
      ResultSet results = conn.getMetaData().getTables(null,null,null,null);
      while(results.next()){
        if (results.getString("TABLE_NAME").equals("jobListings"))
        {
          tableExists = true;
        }
      }
    }
    catch (SQLException e){
      e.printStackTrace();
    }
    assertTrue(tableExists);
  }
}
