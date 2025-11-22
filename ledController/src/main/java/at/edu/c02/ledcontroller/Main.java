package at.edu.c02.ledcontroller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    /**
     * This is the main program entry point.
     * TODO: add new commands when implementing additional features.
     */
    public static void main(String[] args) throws IOException {
        ApiServiceImpl api = new ApiServiceImpl();
        LedController ledController = new LedControllerImpl(api);

        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(!input.equalsIgnoreCase("exit"))
        {
            System.out.println("=== LED Controller ===");
            System.out.println("Enter 'demo'       → send a demo request");
            System.out.println("Enter 'setled'     → set a specific LED color");
            System.out.println("Enter 'groupstatus'→ show status of group B");
            System.out.println("Enter 'status'     → show status of a specific LED");
            System.out.println("Enter 'off'        → turn **all LEDs off**");
            System.out.println("Enter 'exit'       → exit the program");
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
                JSONObject obj = api.getLight(Integer.parseInt(input));
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
        }
    }
}