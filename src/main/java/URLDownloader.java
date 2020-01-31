import java.net.*;
import java.io.*;
public class URLDownloader {
    public String URLSelector(String urlAdress) {
        //Pulled from networking section of docs.oracle.com/javase
        try {
            URL url = new URL(urlAdress);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine;
            StringBuilder jsonData = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                jsonData.append(inputLine);
            in.close();
            //Handle it as a null so we don't run into issues with strings and NULL json Fields
            if(jsonData.toString().equals("[]"))
            {
                return null;
            }
            return jsonData.toString();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "Try and Catch Failure";
    }
}