import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class URLDownloader {
  public String URLSelector(String urlAddress) {
    // Pulled from networking section of docs.oracle.com/javase
    try {
      URL url = new URL(urlAddress);
      int retry = 3;
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      do {
        // implementing retry function for http connection
        System.out.println(
            "Github Connection Busy, retrying in 3 seconds, Attempts left: " + retry);
        TimeUnit.SECONDS.sleep(3);
        urlConnection = (HttpURLConnection) url.openConnection();
        retry--;
      } while ((urlConnection.getResponseCode() == 503) && retry > 0);
      if (retry == 0) {
        System.out.println("Too many connection attempts failed closing application");
        System.exit(0);
      }

      InputStreamReader connStream =
          new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
      BufferedReader in = new BufferedReader(connStream);
      String inputLine;
      StringBuilder jsonData = new StringBuilder();
      while ((inputLine = in.readLine()) != null) jsonData.append(inputLine);
      in.close();
      // Handle it as a null so we don't run into issues with strings and NULL json Fields
      if (jsonData.toString().equals("[]")) {
        return null;
      }
      return jsonData.toString();
    } catch (IOException | InterruptedException e1) {
      e1.printStackTrace();
    }
    return "";
  }

  public List<JobPost> gitJsonToList(Gson gson, String url) {
    List<JobPost> jobLists = new ArrayList<JobPost>();
    List<JobPost> tempJobPostList = new ArrayList<JobPost>();
    int pageNumber = 1;
    String jsonData = URLSelector(url);
    while (jsonData != null) {
      jsonData = URLSelector(url + pageNumber);
      tempJobPostList = gson.fromJson(jsonData, new TypeToken<List<JobPost>>() {}.getType());
      Optional.ofNullable(tempJobPostList).ifPresent(jobLists::addAll);
      pageNumber++;
    }
    return jobLists;
  }

  public List<StackOverFlowJobPost> stackXMLToList(String urlAddress) {
    List<StackOverFlowJobPost> stackJobLists = new ArrayList<StackOverFlowJobPost>();
    StackOverFlowJobPost stackSinglePost = null;

    // Use JDOM to parse the XML data
    // https://howtodoinjava.com/xml/read-xml-dom-parser-example/
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      URL url = new URL(urlAddress);
      int retry = 3;
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      do {
        // implementing retry function for http connection
        System.out.println(
            "StackOverflow Connection Busy, retrying in 5 seconds, attempts left: " + retry);
        TimeUnit.SECONDS.sleep(5);
        urlConnection = (HttpURLConnection) url.openConnection();
        retry--;
      } while ((urlConnection.getResponseCode() == 503) && retry > 0);
      if (retry == 0) {
        System.out.println("Too many connection attempts failed closing application");
        System.exit(0);
      }
      InputStreamReader inputStreams =
          new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
      InputSource inputSource = new InputSource(inputStreams);
      inputSource.setEncoding("UTF-8");
      Document document = builder.parse(inputSource);
      // Fixes some issues allegedly
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("item");
      for (int i = 0; i < nList.getLength(); i++) {
        Node node = nList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          // Create new Employee Object
          stackSinglePost = new StackOverFlowJobPost();
          stackSinglePost.setGuid(eElement.getElementsByTagName("guid").item(0).getTextContent());
          stackSinglePost.setLink(eElement.getElementsByTagName("link").item(0).getTextContent());
          stackSinglePost.setAuthor(
              eElement.getElementsByTagName("a10:name").item(0).getTextContent());
          stackSinglePost.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
          stackSinglePost.setDescription(
              eElement.getElementsByTagName("description").item(0).getTextContent());
          stackSinglePost.setPubDate(
              eElement.getElementsByTagName("pubDate").item(0).getTextContent());
          if (eElement.getElementsByTagName("location").getLength() == 0) {
            stackSinglePost.setLocation(null);
          } else {
            stackSinglePost.setLocation(
                eElement.getElementsByTagName("location").item(0).getTextContent());
          }
          // change this for appending
          if (eElement.getElementsByTagName("category").getLength() > 0) {
            int lengthIterator = eElement.getElementsByTagName("category").getLength();
            StringBuilder categoryStringBuilder = new StringBuilder();
            while (lengthIterator > 0) {
              // cleaning up the categories
              categoryStringBuilder.append(
                  eElement
                      .getElementsByTagName("category")
                      .item(lengthIterator - 1)
                      .getTextContent());
              if (lengthIterator > 1) {
                categoryStringBuilder.append(",");
              }
              lengthIterator--;
            }
            stackSinglePost.setCategory(categoryStringBuilder.toString());
          }
          // Add Employee to list
          stackJobLists.add(stackSinglePost);
        }
      }

    } catch (IOException | ParserConfigurationException | SAXException | InterruptedException e) {
      e.printStackTrace();
    }
    return stackJobLists;
  }
}
