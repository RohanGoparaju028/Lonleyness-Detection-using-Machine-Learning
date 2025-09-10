package com.mycompany.datapipeline;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
class Json {
  List<String> posts;
  public Json() {
      this.posts = new ArrayList<>();
  }
 public void Filter_JSON(String json) {
     JSONArray jsonArray = new JSONArray(json); // Handle array instead of object

    for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject post = jsonArray.getJSONObject(i);
        String title = post.getString("title");
        String text = post.getString("text").replace(",", " "); // Avoid issues with CSV formatting
        int upvotes = post.getInt("upvotes");
        String url = post.getString("url");
        int numComments = post.getInt("comments");

       this.posts.add(title + "," + text + "," + upvotes + "," + url + "," + numComments);
    }
} 
     
}
class JsonToCsv {
    public void writeToCsv(List<String> posts, String csvFilePath) {
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            // Write CSV header
            writer.append("Title,Text,Upvotes,URL,Comments\n");

            // Write each post as a CSV row
            for (String post : posts) {
                writer.append(post).append("\n");
            }
            System.out.println("CSV file created successfully: " + csvFilePath);
        } catch (IOException e) {
            System.out.println("Error in creating the CSV file: " + e.getMessage());
        }
    }
}

public class Datapipeline {
    
    public static void main(String[] args) {
        
        try{
            final Json json = new Json();
            
             var jsondata = new String(Files.readAllBytes(Paths.get("D:\\javaprograms\\DataPipeline\\datapipeline\\src\\main\\java\\com\\mycompany\\datapipeline\\r.json")));
            json.Filter_JSON(jsondata);
            final JsonToCsv jscsv = new JsonToCsv();
            String csvFilePath = "D:\\javaprograms\\DataPipeline\\datapipeline\\\\src\\main\\java\\com\\mycompany\\datapipeline\\output.csv";
            jscsv.writeToCsv(json.posts, csvFilePath);
                        
        } catch(IOException e) {
            e.getStackTrace();
        }
    }
}