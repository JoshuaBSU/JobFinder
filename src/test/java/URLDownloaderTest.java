import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

class URLDownloaderTest {
  public String url = "https://jobs.github.com/positions.json?page=";

  @Test
  void gitJsonToList() {
    // setup for test
    URLDownloader urldwnl = new URLDownloader();
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    List<JobPost> test = urldwnl.gitJsonToList(gson, url);
    // expected
    int minimumExpected = 100;
    int actual = test.size();
    // check we have atleast 100 items
    assertTrue(minimumExpected < actual);
  }
}
