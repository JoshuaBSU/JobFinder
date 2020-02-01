import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class URLDownloader {
  public String URLSelector(String urlAdress) {
    // Pulled from networking section of docs.oracle.com/javase
    try {
      URL url = new URL(urlAdress);
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;
      StringBuilder jsonData = new StringBuilder();
      while ((inputLine = in.readLine()) != null) jsonData.append(inputLine);
      in.close();
      // Handle it as a null so we don't run into issues with strings and NULL json Fields
      if (jsonData.toString().equals("[]")) {
        return null;
      }
      return jsonData.toString();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return "Try and Catch Failure";
  }

  public List<JobPost> gitJsonToList(Gson gson, String url) {
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<JobPost> temp = new ArrayList<JobPost>();
    int pageNumber = 1;
    String jsonData = URLSelector(url);
    while (jsonData != null) {
      jsonData = URLSelector(url + pageNumber);
      temp = gson.fromJson(jsonData, new TypeToken<List<JobPost>>() {}.getType());
      Optional.ofNullable(temp).ifPresent(jobLists::addAll);
      pageNumber++;
    }
    return jobLists;
  }
}
