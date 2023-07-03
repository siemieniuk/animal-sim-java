module com.siemieniuk.animals {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires lombok;

    opens siemieniuk.animals to javafx.fxml;
    exports siemieniuk.animals to javafx.graphics;
    exports siemieniuk.animals.hobhw_parser;
    opens siemieniuk.animals.hobhw_parser to javafx.fxml;
    exports siemieniuk.animals.math;
    opens siemieniuk.animals.math to javafx.fxml;
    exports siemieniuk.animals.controllers;
    opens siemieniuk.animals.controllers to javafx.fxml;
    exports siemieniuk.animals.core;
    opens siemieniuk.animals.core to javafx.fxml;
    exports siemieniuk.animals.core.locations;
    opens siemieniuk.animals.core.locations to javafx.fxml;
    exports siemieniuk.animals.core.animals;
    opens siemieniuk.animals.core.animals to javafx.fxml;
    exports siemieniuk.animals.core.world_creation;
    opens siemieniuk.animals.core.world_creation to javafx.fxml;
    exports siemieniuk.animals.gui;
    opens siemieniuk.animals.gui to javafx.fxml;
    exports siemieniuk.animals.core.animals.preyrouter;
    opens siemieniuk.animals.core.animals.preyrouter to javafx.fxml;
    exports siemieniuk.animals.core.typing;
    opens siemieniuk.animals.core.typing to javafx.fxml;
    exports siemieniuk.animals.images;
    opens siemieniuk.animals.images to javafx.fxml;
}