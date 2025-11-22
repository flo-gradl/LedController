package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This class handles the actual logic
 */
public class LedControllerImpl implements LedController {
    private final ApiService apiService;

// static ids if loop doesnt work
// private static final int[] GROUP_LED_IDS = { 10, 11, 12 };

    public LedControllerImpl(ApiService apiService)
    {
        this.apiService = apiService;
    }


    @Override
    public void demo() throws IOException
    {
        // Call `getLights`, the response is a json object in the form `{ "lights": [ { ... }, { ... } ] }`
        JSONObject response = apiService.getLights();
        // get the "lights" array from the response
        JSONArray lights = response.getJSONArray("lights");
        // read the first json object of the lights array
        JSONObject firstLight = lights.getJSONObject(0);
        // read int and string properties of the light
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

        return groupLeds;

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

}
