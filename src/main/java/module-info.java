module com.siemieniuk.animals {
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
    exports com.siemieniuk.animals.core;
    opens com.siemieniuk.animals.core to javafx.fxml;
    exports com.siemieniuk.animals.core.locations;
    opens com.siemieniuk.animals.core.locations to javafx.fxml;
    exports com.siemieniuk.animals.core.animals;
    opens com.siemieniuk.animals.core.animals to javafx.fxml;
    exports com.siemieniuk.animals.core.world_creation;
    opens com.siemieniuk.animals.core.world_creation to javafx.fxml;
    exports com.siemieniuk.animals.gui;
    opens com.siemieniuk.animals.gui to javafx.fxml;
    exports com.siemieniuk.animals.core.animals.preyrouter;
    opens com.siemieniuk.animals.core.animals.preyrouter to javafx.fxml;
    exports com.siemieniuk.animals.core.typing;
    opens com.siemieniuk.animals.core.typing to javafx.fxml;
    exports com.siemieniuk.animals.images;
    opens com.siemieniuk.animals.images to javafx.fxml;
}