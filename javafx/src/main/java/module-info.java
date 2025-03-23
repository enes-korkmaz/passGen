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