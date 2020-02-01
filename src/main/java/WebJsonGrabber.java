import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WebJsonGrabber {
  public static void main(String[] args) throws IOException {
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

    // Loop page pulls until blank job entries
    while (jsonData != null) {
      jsonData = downloader.URLSelector(url + pageNumber);
      temp = gson.fromJson(jsonData, new TypeToken<List<JobPost>>() {}.getType());
      Optional.ofNullable(temp).ifPresent(jobLists::addAll);
      pageNumber++;
    }

    // Stores job info into 1 json file
    try {
      gson.toJson(jobLists, new FileWriter("jobposts.json"));
    } catch (IOException x) {
      x.printStackTrace();
    }

    // Just Prints out Jobs
    int i = 0;
    for (JobPost jobList : jobLists) {
      System.out.println("JobList Entry \n" + i);
      i++;
      System.out.println(jobList.getId());
      // System.out.println(jobList.toString());
      System.out.println(jobList.getCreated_at());
    }
  }
}
