module Sushii {

    exports comp1206.sushi.server;
    exports comp1206.sushi.common;


    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.sothawo.mapjfx;
    requires slf4j.simple;
    requires slf4j.api;
    requires json;


    opens comp1206.sushi.server;
    opens comp1206.sushi.common;
}