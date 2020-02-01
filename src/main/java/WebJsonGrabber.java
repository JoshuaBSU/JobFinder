import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebJsonGrabber {
  public static void main(String[] args) throws IOException {
    // Initiates Variables
    List<JobPost> jobLists = new ArrayList<JobPost>();
    URLDownloader downloader = new URLDownloader();
    String url = "https://jobs.github.com/positions.json?page=";

    // Gson Configuration
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    // makes jsonData non null;
    // String jsonData = downloader.URLSelector(url);
    // System.out.println(downloader.URLSelector(url));

    jobLists = downloader.gitJsonToList(gson, url);

    // Just Prints out Jobs
    for (JobPost jobList : jobLists) {
      // System.out.println(jobList.getId());
      System.out.println(jobList.toString());
      // System.out.println(jobList.getCreated_at());
    }

    // Stores job info into 1 json file
    fileWriter(gson, jobLists);
  }

  public static void fileWriter(Gson gson, List<JobPost> jobLists) {
    try {
      gson.toJson(jobLists, new FileWriter("jobposts.json"));
    } catch (IOException x) {
      x.printStackTrace();
    }
  }
}
