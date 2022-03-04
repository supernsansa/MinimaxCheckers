module com.anoc20.minimaxcheckers {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.anoc20.minimaxcheckers to javafx.fxml;
    exports com.anoc20.minimaxcheckers;
}