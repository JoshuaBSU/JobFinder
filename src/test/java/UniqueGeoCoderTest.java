import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UniqueGeoCoderTest {
  @Test
  void geoCodeGoodData() {
    // have geocoder
    UniqueGeoCoder geocode = new UniqueGeoCoder();
    String testLocation = "boston";
    List<String> dataToTest = new ArrayList<>();
    dataToTest = geocode.forward(testLocation);
    System.out.println(dataToTest.get(0) + " " + dataToTest.get(1));

    assertTrue(dataToTest.get(0).equals("-75.6964264") && dataToTest.get(1).equals("4.7997846"));
  }

  @Test
  // should get null
  void geoCodeBadData() {
    UniqueGeoCoder geocode = new UniqueGeoCoder();
    String testLocation = "moonStarShipClockWall";
    List<String> dataToTest = new ArrayList<>();
    dataToTest = geocode.forward(testLocation);

    assertNull(dataToTest);
  }
  // return for boston expected 4.7997846-75.6964264
}
