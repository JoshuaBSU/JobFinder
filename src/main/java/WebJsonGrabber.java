import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WebJsonGrabber {
  public static void main(String[] args) {
    // Structs items
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<JobPost> temp = new ArrayList<JobPost>();
    URLDownloader downloader = new URLDownloader();
    String url = "https://jobs.github.com/positions.json?page=";
    int pageNumber = 1;
    // makes jsonData non null;
    String jsonData = downloader.URLSelector(url);
    // System.out.println(downloader.URLSelector(url));

    // Gson Configuration
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    //Loop page pulls until blank job entries
    while (jsonData != null) {
      jsonData = downloader.URLSelector(url + pageNumber);
      temp = gson.fromJson(jsonData, new TypeToken<List<JobPost>>() {}.getType());
      Optional.ofNullable(temp).ifPresent(jobLists::addAll);
      pageNumber++;
    }

    //tesing whitespaces again
      int a =      9;

    //Just Prints out Jobs
    int i = 0;
    for (JobPost jobList : jobLists) {
      System.out.println("JobList Entry" + i);
      i++;
      System.out.println(jobList.getId());
      System.out.println(jobList.toString());
    }
  }
}
