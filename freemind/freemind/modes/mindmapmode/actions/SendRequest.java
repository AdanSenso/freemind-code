package freemind.modes.mindmapmode.actions;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SendRequest {
	private static String baseURL="http://10.0.0.102:8000"; //Jameels computer where server runs
	//private static String baseURL="http://localhost:8000";
	
	
	public static String sendPost(String requestUrl,String jsonInputString) {
		
		try {
            URL url = new URL(baseURL+requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);


            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response code: " + responseCode);

            // Read response
            Scanner scanner = new Scanner(conn.getInputStream()).useDelimiter("\\A");
            String response = scanner.hasNext() ? scanner.next() : "";
            System.out.println("Response body: " + response);

            conn.disconnect();
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		
	}
	
	
	
	
	
    public static String sendPostWithFileAndNode(String requestUrl, String filePath, String nodeId) {
        try {
           
            // Build JSON body
            String jsonInputString = String.format(
                "{\"file_path\": \"%s\", \"node_id\": \"%s\"}",
                filePath, nodeId
            );

            return sendPost(requestUrl,jsonInputString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        
        public static String sendPostWithFile(String requestUrl, String filePath) {
            try {
                // Build JSON body
                String jsonInputString = String.format(
                    "{\"file_path\": \"%s\"}",
                    filePath
                );

                return sendPost(requestUrl, jsonInputString);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        public static String sendCustomPrompt(String requestUrl, String filePath, String userPrompt) {
            try {
                // Escape quotes in the prompt for safe JSON formatting
                String escapedPrompt = userPrompt.replace("\"", "\\\"");

                // Build JSON body
                String jsonInputString = String.format(
                    "{\"file_path\": \"%s\", \"user_prompt\": \"%s\"}",
                    filePath, escapedPrompt
                );

                return sendPost(requestUrl, jsonInputString);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


    
    
}
