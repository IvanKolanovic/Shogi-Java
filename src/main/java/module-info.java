module tvz.ikolanovic.shogi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires static lombok;
    requires org.slf4j;

    opens tvz.ikolanovic.shogi to javafx.fxml;
    exports tvz.ikolanovic.shogi;
    exports tvz.ikolanovic.shogi.controllers;
    exports tvz.ikolanovic.shogi.engine;
    exports tvz.ikolanovic.shogi.models;
    exports tvz.ikolanovic.shogi.models.utils;
    exports tvz.ikolanovic.shogi.models.pieces;


    opens tvz.ikolanovic.shogi.controllers to javafx.fxml;
}
