import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UniqueGeoCoder {

    public List<String> forward(String address){
        List<String> longandLatreturn = new ArrayList<>();
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("1bf8deccc1ff4d55963478703cdb3710");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();


        if (firstResultLatLng != null) {
            longandLatreturn.add(firstResultLatLng.getLng().toString());
            longandLatreturn.add(firstResultLatLng.getLat().toString());
            return longandLatreturn;
        }
        else
        {
            return null;
        }
    }
}
