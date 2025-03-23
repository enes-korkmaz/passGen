/**
 * This file is part of PassGen.
 *
 * Copyright (c) 2025 Enes Korkmaz, Nico Staudacher, Nadine Schoch and Nazanin Golalizadeh
 *
 * PassGen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License Version 3 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package de.hhn.it.devtools.javafx;

import de.hhn.it.devtools.javafx.controllers.Controller;
import de.hhn.it.devtools.javafx.controllers.RootController;
import de.hhn.it.devtools.javafx.modules.Module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(Main.class);
  private final int WIDTH = 1280;

  private final int HEIGHT = 720;
  private RootController rootController;
  private Map<String, Module> moduleMap;

  public Main() {
    moduleMap = new HashMap<>();
  }

  /**
   * the main method.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    System.out.println("java version: " + System.getProperty("java.version"));
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Root2.fxml"));

    final Parent root = loader.load();
    rootController = loader.getController();

    primaryStage.setTitle("JavaFX UI");
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    primaryStage.setMinWidth(WIDTH);
    primaryStage.setMinHeight(HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();

    addModule("PassGenService");
  }

  @Override
  public void stop() {
    logger.info("stop: Shutting down");
    // This is automatically called then you terminate the application using the window controls
    // ("x it out", "quit it", ...). It does not get called when you terminate the application
    // using control-C or an OS command like "kill".

    rootController.shutdown();
  }

  private void addModule(String name) {
    try {
      logger.info("addModule: Loading Module: \"" + name + "\"!");
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
      Node content = loader.load();
      Controller controller = loader.getController();
      Module module = new Module(name, controller, content);
      rootController.addModule(module);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(8);
    }
  }
}
