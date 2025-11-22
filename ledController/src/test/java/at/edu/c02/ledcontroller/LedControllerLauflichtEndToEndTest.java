//package at.edu.c02.ledcontroller;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LedControllerLauflichtEndToEndTest {
//
//    @Test
//    public void testLauflichtEndToEnd() throws IOException, InterruptedException {
//
//        ApiServiceImpl api = new ApiServiceImpl();
//        LedControllerImpl controller = new LedControllerImpl(api);
//
//        JSONArray groupBefore = controller.getGroupLeds("B");
//
//        assertTrue(groupBefore.length() > 0,
//                "Gruppe B muss mindestens eine LED enthalten.");
//
//        String color = "yellow";
//
//        controller.lauflicht(color);
//
//        Thread.sleep(500);
//
//        JSONArray groupAfter = controller.getGroupLeds("B");
//
//        assertEquals(groupBefore.length(), groupAfter.length(),
//                "Die Anzahl der LEDs in Gruppe B sollte gleich bleiben.");
//
//        for (int i = 0; i < groupAfter.length(); i++) {
//
//            JSONObject led = groupAfter.getJSONObject(i);
//
//            assertEquals(color, led.getString("color"),
//                    "LED " + led.getInt("id") + " wurde nicht korrekt farbig gesetzt.");
//
//            assertTrue(led.getBoolean("on"),
//                    "LED " + led.getInt("id") + " sollte eingeschaltet sein.");
//        }
//
//        System.out.println("✔ Lauflicht-End-to-End-Test erfolgreich abgeschlossen!");
//    }
//}