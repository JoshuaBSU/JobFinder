import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class WebJsonGrabberTest {

  @Test
  void fileWriter()
  {
    //initialize
    WebJsonGrabber run = new WebJsonGrabber();
    String jsontext = "";
    //job title from https://jobs.github.com/positions/4cd29974-e48b-11e8-8478-fb9810f86b79
    String knowJobTitle = "Software engineer (back end)";
    try
    {
      jsontext = new String(Files.readAllBytes(Paths.get("jobposts.json")));
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    //boolean inFile = jsontext.indexOf(knowJobTitle);
    assertTrue(jsontext.indexOf(knowJobTitle) >= 1);
  }
}
