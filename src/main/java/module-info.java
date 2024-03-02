module tvz.ikolanovic.shogi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires static lombok;

    opens tvz.ikolanovic.shogi to javafx.fxml;
    exports tvz.ikolanovic.shogi;
    exports tvz.ikolanovic.shogi.controllers;
    opens tvz.ikolanovic.shogi.controllers to javafx.fxml;
}
