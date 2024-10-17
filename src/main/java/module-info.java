module demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens fes.aragon.controller to javafx.fxml;
    exports fes.aragon.inicio to javafx.graphics;
}