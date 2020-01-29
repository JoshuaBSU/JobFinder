import java.net.*;
import java.io.*;

public class URLDownloader {
    public String address(String page, String number)
    {
        //Pulled from networking section of docs.oracle.com/javase (Minus catch conditions)
        try
        {
            URL url = new URL(page+number);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        }
        catch (MalformedURLException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e2)
        {
            e2.printStackTrace();
        }
        return "placeholder";
    }
}
