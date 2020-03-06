import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FilterGUI extends Application {

    void generate()
    {
        Application.launch();
    }

    @Override
    public void start(Stage stage)
    {
        MapGUI mapGUImaker = new MapGUI();
        String dbLocation = "jdbc:sqlite:jobPosts.db";
        SQLiteDBManager sqlDBManager = new SQLiteDBManager();
        sqlDBManager.dbConnection(dbLocation);
        final List<DatabaseEntry>[] jobsBeingSorted = new List[]{sqlDBManager.returnEntireDB()};

        VBox root = new VBox();
        root.setMinSize(600, 400);

        // Create the Scene
        Scene scene = new Scene(root);

        // Set the Properties of the Stage
        stage.setX(100);
        stage.setY(200);
        stage.setMinHeight(400);
        stage.setMinWidth(600);

        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("Job Filtering");
        // Display the Stage
        stage.show();

        Sorting sorter = new Sorting();
        String[] category = new String[1];
        Double[] longitude = new Double[1];
        Double[] latitude = new Double[1];
        Double[] distance = new Double[1];
        String testDate = "Tue, 10 Feb 2020 08:02:24 Z";

        // 200km from boston
        Double DistanceTest = 200.0;
        //Double longitudeTest = -71.057083;
        //Double latitudeTest = 42.361145;

        Label labelcategorySearchBox = new Label("Enter Category To Search");
        TextField categorySearchBox = new TextField();
        Label labelLongitudeSearchBox = new Label("Enter Longitude");
        TextField LongitudeSearchBox = new TextField();
        Label labelLatitudeSearchBox = new Label("Enter Latitude");
        TextField LatitudeSearchBox = new TextField();
        Label labelDistanceSearchBox = new Label("Enter distance in KM");
        TextField distanceSearchBox = new TextField();


        EventHandler<ActionEvent> enterCategory = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                labelcategorySearchBox.setText(categorySearchBox.getText());
                category[0] = categorySearchBox.getText();
            }
        };
        categorySearchBox.setOnAction(enterCategory);

        EventHandler<ActionEvent> enterLongitude = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                labelLongitudeSearchBox.setText(LongitudeSearchBox.getText());
                longitude[0] = Double.parseDouble(LongitudeSearchBox.getText());
            }
        };
        LongitudeSearchBox.setOnAction(enterLongitude);

        EventHandler<ActionEvent> enterLatitude = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                labelLatitudeSearchBox.setText(LatitudeSearchBox.getText());
                latitude[0] = Double.parseDouble(LatitudeSearchBox.getText());
            }
        };
        LatitudeSearchBox.setOnAction(enterLatitude);

        EventHandler<ActionEvent> enterDistance = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                labelDistanceSearchBox.setText(distanceSearchBox.getText());
                distance[0] = Double.parseDouble(distanceSearchBox.getText());
            }
        };
        distanceSearchBox.setOnAction(enterDistance);


        Button btnSort = new Button();
        btnSort.setText("Sort Location");
        btnSort.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                jobsBeingSorted[0] = sorter.coordinateSearch(jobsBeingSorted[0],longitude[0],latitude[0],distance[0]);
            }
        });

        Button btnSortCategory = new Button();
        btnSortCategory.setText("Sort Category");
        btnSortCategory.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                jobsBeingSorted[0] = sorter.categorySort(jobsBeingSorted[0], category[0]);
            }
        });

        Button btnSortYounger = new Button();
        btnSortYounger.setText("Sort Younger than date");
        btnSortYounger.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                jobsBeingSorted[0] = sorter.dateSearchYoungerThan(jobsBeingSorted[0],testDate);
            }
        });

        Button btnSortOlder = new Button();
        btnSortOlder.setText("Sort Older than date");
        btnSortOlder.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                jobsBeingSorted[0] = sorter.dateSearchOlderThan(jobsBeingSorted[0],testDate);
            }
        });

        Button btnLoad = new Button();
        btnLoad.setText("Load Map");
        btnLoad.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    mapGUImaker.MapMaker(jobsBeingSorted[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Loading Maps with sorted list specified by user");
            }
        });

        StackPane root2 = new StackPane();
        root.getChildren().add(btnLoad);
        root.getChildren().add(btnSort);
        root.getChildren().add(btnSortCategory);
        root.getChildren().add(btnSortOlder);
        root.getChildren().add(btnSortYounger);

        root.getChildren().add(labelcategorySearchBox);
        root.getChildren().add(categorySearchBox);

        root.getChildren().add(labelLongitudeSearchBox);
        root.getChildren().add(LongitudeSearchBox);
        root.getChildren().add(labelLatitudeSearchBox);
        root.getChildren().add(LatitudeSearchBox);
        root.getChildren().add(labelDistanceSearchBox);
        root.getChildren().add(distanceSearchBox);


        stage.setTitle("JobPost Database display");
        stage.setScene(scene);
        stage.show();
    }
}
