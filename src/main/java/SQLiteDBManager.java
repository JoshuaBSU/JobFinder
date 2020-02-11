import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.List;

public class SQLiteDBManager {
    private Connection conn = null;

    //creates a connection and returns it so we can manually close if needed.
    public Connection dbConnection(String dbLocation) {
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

    //Send commands to DB without any return information
    public void dbManager(String dbLocation, String sqlCommand, Connection conn) {
        try {
            // create connection and statement
            Statement statement = conn.createStatement();
            statement.execute(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // manually close connection later
    }

    //Lets us add git formatted lists into our db
    public void gitJsonAddToDB(List<JobPost> jobLists, Connection conn) throws SQLException {
        // prepared statement
        String sql =
                "Insert Into jobListings(id,type,url,created_at,company,company_url,location,title,description,how_to_apply,company_logo) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
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
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //creates a blank database
    public void blankDBMaker(String dbLocation)
    {
        try {
            // Setting up forName so commands don't get thrown into an error
            Class.forName("org.sqlite.JDBC");
            // create connection and statement
            conn = DriverManager.getConnection(dbLocation);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//WIP
    public boolean searchDBViaPrimaryKey(String key)
    {
        boolean ItemFound = false;
        if (conn != null)
        {
            try {
                Statement searchStatement = conn.createStatement();

            } catch (SQLException e){
                e.printStackTrace();
            }

        }
        else
        {
            System.out.println("Connection was closed unexpectedly, exiting");
            //System.exit(0);
        }
        return ItemFound;
    }

//Prints out all keys
    public void printFullDBKeys()
    {
        String sqlStatement = "SELECT id FROM jobListings";
        if (conn != null)
        {

            try {
                Statement searchStatement = conn.createStatement();
                ResultSet results = searchStatement.executeQuery(sqlStatement);
                while(results.next())
                {
                    System.out.print(results.getString("id") + "\t");
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

        }
        else
        {
            System.out.println("Connection was closed unexpectedly, exiting");
            System.exit(0);
        }
    }
    public boolean checkIfJobListByIDandURL(JobPost jobPostToCheck)
    {
        String sqlStatement = "SELECT id, type, url, created_at, company, company_url, location,title, description, how_to_apply, company_logo FROM jobListings";
        if (conn != null)
        {

            try {
                Statement searchStatement = conn.createStatement();
                ResultSet results = searchStatement.executeQuery(sqlStatement);
                while(results.next())
                {
                    if (results.getString("id").equals(jobPostToCheck.getId()) && results.getString("url").equals(jobPostToCheck.getUrl()))
                    {
                        return true;
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Connection was closed unexpectedly, exiting");
            System.exit(0);
        }
        return false;
    }

}
