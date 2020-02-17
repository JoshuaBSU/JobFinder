import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

class URLDownloaderTest {
  public String url = "https://jobs.github.com/positions.json?page=";
  public String rssURL = "https://stackoverflow.com/jobs/feed";

  @Test
  void gitJsonToList() {
    // setup for test
    URLDownloader urlDownload = new URLDownloader();
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    List<JobPost> test = urlDownload.gitJsonToList(gson, url);
    // expected
    int minimumExpected = 100;
    int actual = test.size();
    // check we have at least 100 items
    assertTrue(minimumExpected < actual);
  }
  @Test
  void stackXMLToList()
  {
    URLDownloader urlDownload = new URLDownloader();
    List<StackOverFlowJobPost> test = urlDownload.stackXMLToList(rssURL);
    //make sure we are making a proper list~
    int atLeast = 900;
    int actual = test.size();
    assertTrue(atLeast < actual);

  }
}
