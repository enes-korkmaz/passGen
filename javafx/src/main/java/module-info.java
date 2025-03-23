module devtools.javafx {
  requires org.slf4j;
  requires devtools.apis;
  requires devtools.components;
  requires javafx.controls;
  requires javafx.fxml;
  opens de.hhn.it.devtools.javafx.passGen.view to javafx.fxml;
  exports de.hhn.it.devtools.javafx.passGen.view;
  exports de.hhn.it.devtools.javafx;
  exports de.hhn.it.devtools.javafx.controllers;
  opens de.hhn.it.devtools.javafx.controllers to javafx.fxml;
}