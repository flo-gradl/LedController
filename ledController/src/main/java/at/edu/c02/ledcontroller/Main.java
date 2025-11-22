package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    /**
     * This is the main program entry point. TODO: add new commands when implementing additional features.
     */
    public static void main(String[] args) throws IOException {
        ApiServiceImpl api = new ApiServiceImpl();
        LedController ledController = new LedControllerImpl(api);

        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(!input.equalsIgnoreCase("exit"))
        {
            System.out.println("=== LED Controller ===");
            System.out.println("Enter 'demo' to send a demo request");
            System.out.println("Enter 'exit' to exit the program");
            System.out.println("Enter 'groupstatus' to get the status of all LEDs of one specific group");
            System.out.println("Enter 'status' to get the status of one specific LED");
            System.out.println("Enter 'setLed' to set a specific LED color");
            System.out.println("Enter 'off' to turn off all LEDs");
            System.out.println("Enter 'spinningled' to set a spinning LED profile");
            input = reader.readLine();
            if(input.equalsIgnoreCase("demo")) {
                ledController.demo();
            } else if(input.equalsIgnoreCase("setled")){
                try{
                    System.out.println("Which LED (ID)?");
                    String idLed = reader.readLine();
                    int ledId = Integer.parseInt(idLed.trim());

                    System.out.println("Which color?");
                    String color = reader.readLine().trim();

                    ledController.setLight(ledId, color, true);

                    System.out.println("LED color set!");
                }catch(NumberFormatException e){
                    System.out.println("Invalid LED ID");
                }catch (IOException e){
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (input.equalsIgnoreCase("groupstatus")) {
                ledController.getGroupLeds("B");
            } else if (input.equalsIgnoreCase("status")) {
                System.out.println("Please specify LED ID:");
                input = reader.readLine();

                JSONObject response = api.getLight(Integer.parseInt(input));
                JSONArray arr = response.getJSONArray("lights");

                if (arr.length() == 0) {
                    System.out.println("No LED found with that ID.");
                    continue;
                }

                JSONObject obj = arr.getJSONObject(0);

                System.out.println("LED " + obj.get("id") + " is on: " + obj.get("on")
                        + ". Color: " + obj.get("color"));

            } else if (input.equalsIgnoreCase("off")) {
                try {
                    ledController.turnOffAllLeds();
                    System.out.println("✅ All LEDs turned off.");
                } catch (IOException e) {
                    System.out.println("❌ Error turning off LEDs: " + e.getMessage());
                }
            }

            else if (input.equalsIgnoreCase("spinningled")) {
                    System.out.println("Which color?");
                    String color = reader.readLine().trim();

                    System.out.println("How many turns?");
                    String nrofturn = reader.readLine();
                    int nrofTurns = Integer.parseInt(nrofturn.trim());

                    for(int i = 0; i < nrofTurns; i++) {
                        ledController.lauflicht(color);
                       //ledController.turnOffAllLeds();
                    }
            }
        }
    }
}

