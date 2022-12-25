module com.sap.myosotis {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sap.myosotis to javafx.fxml;
    exports com.sap.myosotis;
}