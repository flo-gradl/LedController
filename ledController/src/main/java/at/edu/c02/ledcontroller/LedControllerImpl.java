package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This class handles the actual logic
 */

public class LedControllerImpl implements LedController {
    private final ApiService apiService;

    // Fixed group IDs we are allowed to control
    private static final int[] GROUP_LED_IDS = { 20, 21, 22, 23, 24, 25, 26, 27 };

    public LedControllerImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void demo() throws IOException {
        JSONObject response = apiService.getLights();
        JSONArray lights = response.getJSONArray("lights");
        JSONObject firstLight = lights.getJSONObject(0);
        System.out.println("First light id is: " + firstLight.getInt("id"));
        System.out.println("First light color is: " + firstLight.getString("color"));
    }

    @Override
    public JSONArray getGroupLeds(String group) throws IOException {
        JSONObject response = apiService.getLights();
        JSONArray lights = response.getJSONArray("lights");

        JSONArray groupLeds = new JSONArray();

        for (int i = 0; i < lights.length(); i++) {
            JSONObject led = lights.getJSONObject(i);

            if (led.has("groupByGroup") && !led.isNull("groupByGroup")) {
                JSONObject groupInfo = led.getJSONObject("groupByGroup");
                if (groupInfo.has("name") && group.equals(groupInfo.getString("name"))) {
                    groupLeds.put(led);
                }
            }
        }

        for (int i = 0; i < groupLeds.length(); i++) {
            JSONObject obj = groupLeds.getJSONObject(i);
            System.out.println("LED " + obj.get("id") + "is on: " + obj.get("on") + ". Color: " + obj.get("color") + ".");
        }

        return groupLeds;
    }

    @Override
    public void setLight(int id, String color, boolean state) throws IOException {
        apiService.setLight(id, color, state);
    }

    @Override
    public void turnOffAllLeds() throws IOException {
        JSONObject response = apiService.getLights();
        JSONArray lights = response.getJSONArray("lights");

        for (int i = 0; i < lights.length(); i++) {
            JSONObject light = lights.getJSONObject(i);
            int id = light.getInt("id");

            // current color
            String color = light.getString("color");

            apiService.setLightState(id, color, false);
        }
    }

    @Override
    public void lauflicht(String color) throws IOException {

        JSONArray groupLeds = getGroupLeds("B");

        for (int i = 0; i < groupLeds.length(); i++) {
            JSONObject obj = groupLeds.getJSONObject(i);
            int id = obj.getInt("id");
            apiService.setLight(id,color,true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // sauber abbrechen
            }
        }
    }
}