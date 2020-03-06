import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

import org.geotools.data.DefaultFeatureReader;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class MapGUI {
  //first window that makes the map based on the data you filter

  public void MapMaker(List<DatabaseEntry> jobsToDisplay) throws IOException {
    // display a data store file chooser dialog for shapefiles
    File file = new File("ne_50m_admin_0_countries_lakes.shp");
    if (file == null) {
      file = JFileDataStoreChooser.showOpenFile("shp", null);
      if(file == null) {
        return;
      }
    }
    FileDataStore store = FileDataStoreFinder.getDataStore(file);
    SimpleFeatureSource featureSource = store.getFeatureSource();

    //Ask the user for search parameters and use as title
    MapContent map = new MapContent();
    map.setTitle("JobLocations");

    Style style = SLD.createSimpleStyle(featureSource.getSchema());
    Layer layer = new FeatureLayer(featureSource, style);
    map.addLayer(layer);

    JMapFrame mapFrame = new JMapFrame(map);
    mapFrame.enableToolBar(true);
    mapFrame.enableStatusBar(true);
    mapFrame.enableLayerTable(true);


    JToolBar toolbar = mapFrame.getToolBar();
    toolbar.addSeparator();

    //toolbar.add(new JButton(new ValidateGeometryAction()));
    //toolbar.add(new JButton(new ExportShapefileAction()));
    GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    if(jobsToDisplay != null ) {
      for (DatabaseEntry jobPinning : jobsToDisplay) {
        if (jobPinning.getLatitude() != null && jobPinning.getLongitude() != null) {
          Layer pointLayer = addPoint(Double.parseDouble(jobPinning.getLongitude()), Double.parseDouble(jobPinning.getLatitude()));
          pointLayer.setTitle(jobPinning.getTitle());
          map.addLayer(pointLayer);
        }
      }
    }
    else {
      System.out.print("Empty List, Filters likely contain no jobs of users specifications");
    }
    //map.addLayer(pointLayer);


    mapFrame.setSize(800, 600);
    mapFrame.setVisible(true);
    // Now display the map
    //JMapFrame.showMap(map);
  }

  static Layer addPoint(double latitude, double longitude) {
    SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();

    b.setName("JobPosting");
    b.setCRS(DefaultGeographicCRS.WGS84);
    b.add("location", Point.class);
    b.add("title", String.class);
    b.add("description", String.class);
    b.add("category", String.class);
    b.add("company", String.class);
    b.add("id", String.class);

    // building the type
    final SimpleFeatureType TYPE = b.buildFeatureType();

    SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
    GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    Point point = geometryFactory.createPoint(new Coordinate(latitude, longitude));
    featureBuilder.add(point);
    SimpleFeature feature = featureBuilder.buildFeature(null);
    DefaultFeatureCollection featureCollection = new DefaultFeatureCollection("internal", TYPE);
    featureCollection.add(feature);
    float opacity = 1;
    float size = 10;
    Style style = SLD.createPointStyle("Circle",Color.CYAN,Color.BLACK,opacity,size);
    Layer layer = new FeatureLayer(featureCollection, style);
    return layer;
  }

}