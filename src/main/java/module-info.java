module net.windyweather.panimagetwo {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.windyweather.panimagetwo to javafx.fxml;
    exports net.windyweather.panimagetwo;
}