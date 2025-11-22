//package at.edu.c02.ledcontroller;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.Test;
//
//import java.io.IOException;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class LedControllerEndToEndTest {
//    @Test
//    public void end2end_setLight()throws Exception{
//
//        ApiService apiService = new ApiServiceImpl();
//        LedController ledController = new LedControllerImpl(apiService);
//
//        int ledId = 27;
//        String newColor = "blue";
//        boolean newState = true;
//
//        JSONObject setResponse = ledController.setLight(ledId, newColor, newState);
//
//        JSONObject lightsResponse = apiService.getLights();
//        JSONArray lights = lightsResponse.getJSONArray("lights");
//
//        JSONObject targetLd = null;
//        for(int i = 0; i < lights.length(); i++){
//            JSONObject led = lights.getJSONObject(i);
//            if(led.getInt("id") == ledId){
//                targetLd = led;
//                break;
//            }
//        }
//
//        assertNotNull("LED ID: " + ledId + "not found", targetLd);
//        assertEquals("Color error", newColor, targetLd.getString("color"));
//        assertEquals("State error", newState, targetLd.getBoolean("on"));
//    }
//}
