package com.dematic.testassignment.helpers;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

    public FileIO() {}

    public JSONArray readFile(String path) {
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader file = new FileReader(path);
            return (JSONArray) jsonParser.parse(file);

        } catch(IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void writeToFile(JSONArray books, String path) {
        try (FileWriter file = new FileWriter(path)) {

            file.write(books.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
