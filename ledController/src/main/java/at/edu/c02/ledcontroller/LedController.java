package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public interface LedController {
    void demo() throws IOException;
    JSONArray getGroupLeds(String group) throws IOException;
    JSONObject setLight(int id, String color, boolean state) throws IOException;

    void turnOffAllLeds() throws IOException;

}
