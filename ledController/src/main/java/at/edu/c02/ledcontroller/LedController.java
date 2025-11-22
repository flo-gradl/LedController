package at.edu.c02.ledcontroller;

import org.json.JSONArray;

import java.io.IOException;

public interface LedController {
    void demo() throws IOException;

    JSONArray getGroupLeds(String group) throws IOException;

    void turnOffAllLeds() throws IOException;

    void setLight(int id, String color, boolean state) throws IOException;
    void lauflicht(String color) throws IOException;
}
