import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.text.*;
import java.util.*;

public class WebJsonGrabber {
  public static void main(String[] args) {
    // Structs items
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<JobPost> temp = new ArrayList<JobPost>();
    URLDownloader downloader = new URLDownloader();
    String url = "https://jobs.github.com/positions.json?page=";
    int pageNumber = 1;
    // Adds data for the loop to run
    String jsonData = downloader.URLSelector(url);
    // System.out.println(downloader.URLSelector(url));

    // Gson Configuration
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    // test

    while (jsonData != null) {
      jsonData = downloader.URLSelector(url + pageNumber);
      temp = gson.fromJson(jsonData, new TypeToken<List<JobPost>>() {}.getType());
      Optional.ofNullable(temp).ifPresent(jobLists::addAll);
      pageNumber++;
    }

    int i = 0;
    for (JobPost jobList : jobLists) {
      System.out.println("JobList Entry" + i);
      i++;
      System.out.println(jobList.toString());
    }
  }
}
