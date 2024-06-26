module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires ormlite.jdbc;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;


    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    opens at.ac.fhcampuswien.fhmdb.Database to com.google.gson, ormlite.jdbc; // Erlauben Sie Gson, auf die Movie-Klasse zuzugreifen

}

