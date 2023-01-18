module com.example.animals {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.siemieniuk.animals to javafx.fxml;
    exports com.siemieniuk.animals;
    exports com.siemieniuk.animals.hobhw_parser;
    opens com.siemieniuk.animals.hobhw_parser to javafx.fxml;
    exports com.siemieniuk.animals.math;
    opens com.siemieniuk.animals.math to javafx.fxml;
    exports com.siemieniuk.animals.controllers;
    opens com.siemieniuk.animals.controllers to javafx.fxml;
}