package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class LedControllerTest {
    /**
     * This test is just here to check if tests get executed. Feel free to delete it when adding your own tests.
     * Take a look at the stack calculator tests again if you are unsure where to start.
     */
    @Test
    public void TestGetLights() throws Exception {

        ApiService apiService = mock(ApiService.class);

        // 2) JSON bauen, das der Mock zurückgeben soll
        //{
        //"lights": [
        //{
        //"id": 1, // ID, mit der diese LED identifiziert wird
        //"color": "#fff", // Aktuelle Farbe
        //"on": true, // `true`, wenn die LED eingeschalten ist
        //"groupByGroup": {
        //"name": "B" // Name der Gruppe, der diese LED gehört
        //}
        //},
        //{
        //"id": 2, // ID, mit der diese LED identifiziert wird
        //"color": "#fff", // Aktuelle Farbe
        //"on": false, // `true`, wenn die LED eingeschalten ist
        //"groupByGroup": {
        //"name": "B" // Name der Gruppe, der diese LED gehört
        //}
        //},


        JSONArray lights = new JSONArray();

        JSONObject group = new JSONObject();
        group.put("name", "B");

        JSONObject light1 = new JSONObject();
        light1.put("id", 1);
        light1.put("color", "#fff");
        light1.put("on", true);
        light1.put("groupByGroup", group);

        lights.put(light1);

        JSONObject light2 = new JSONObject();
        light2.put("id", 2);
        light2.put("color", "#fff");
        light2.put("on", false);
        light2.put("groupByGroup", group);

        lights.put(light2);

        JSONObject response = new JSONObject();
        response.put("lights", lights);

        // Wenn getGroupLeds() aufgerufen wird, dieses JSON zurückgeben
        when(apiService.getLights()).thenReturn(response);

        // 3) Controller mit dem Mock erstellen
        LedControllerImpl controller = new LedControllerImpl(apiService);

        JSONArray result = controller.getGroupLeds("B");

        //Anzahl prüfen
        assertEquals(2, result.length());

        //LED mit ID 2 finden
        JSONObject led2 = null;
        for (int i = 0; i < result.length(); i++) {
            JSONObject led = result.getJSONObject(i);
            if (led.getInt("id") == 2) {
                led2 = led;
                break;
            }
        }

        assertNotNull("LED mit ID 2 wurde nicht gefunden!", led2);

        //Felder prüfen
        assertEquals("#fff", led2.getString("color"));
        assertFalse(led2.getBoolean("on"));
        assertEquals("B", led2.getJSONObject("groupByGroup").getString("name"));

        verify(apiService, times(1)).getLights();

    }

    @Test
    public void TestAllLEDOFF() throws Exception {

        ApiService apiService = mock(ApiService.class);

        JSONArray lights = new JSONArray();

        JSONObject led1 = new JSONObject();
        led1.put("id", 1);
        led1.put("on", true);
        lights.put(led1);

        JSONObject led2 = new JSONObject();
        led2.put("id", 2);
        led2.put("on", true);
        lights.put(led2);

        JSONObject response = new JSONObject();
        response.put("lights", lights);

        when(apiService.getLights()).thenReturn(response);

        LedControllerImpl controller = new LedControllerImpl(apiService);

        controller.turnOffAllLeds();

        verify(apiService).setLight(1, false);
        verify(apiService).setLight(2, false);

        verify(apiService, times(1)).getLights();
        verifyNoMoreInteractions(apiService);


    }
}
