module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com to javafx.graphics, javafx.fxml;
    opens Controllers to javafx.fxml;
    opens models to javafx.base;

    exports com;
}