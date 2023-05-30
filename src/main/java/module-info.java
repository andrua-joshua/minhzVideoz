module com.example.minhzvideos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.minhzvideos to javafx.fxml;
    exports com.example.minhzvideos;
}