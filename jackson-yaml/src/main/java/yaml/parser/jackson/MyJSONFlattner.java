package yaml.parser.jackson;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyJSONFlattner {

  public static void listJson(JSONObject json) {
    listJSONObject("", json);
  }

  private static void listObject(String parent, Object data) {
    if (data instanceof JSONObject) {
      listJSONObject(parent, (JSONObject)data);
    } else if (data instanceof JSONArray) {
      listJSONArray(parent, (JSONArray) data);
    } else {
      listPrimitive(parent, data);
    }    
  }

  private static void listJSONObject(String parent, JSONObject json) {
    Iterator it = json.keys();
    while (it.hasNext()) {
      String key = (String)it.next();
      Object child = json.get(key);
      String childKey = parent.isEmpty() ? key : parent + "." + key;
      listObject(childKey, child);
    }
  }

  private static void listJSONArray(String parent, JSONArray json) {
    for (int i = 0; i < json.length(); i++) {
      Object data = json.get(i);
      listObject(parent + "[" + i + "]", data);
    }
  }

  private static void listPrimitive(String parent, Object obj) {
    System.out.println(parent + ":"  + obj);
  }

  public static void main(String[] args) {
    String data = "{\"store\":{\"book\":[{\"category\":\"reference\",\"author\":\"NigelRees\",\"title\":\"SayingsoftheCentury\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"HermanMelville\",\"title\":\"MobyDick\",\"isbn\":\"0-553-21311-3\",\"price\":8.99},],\"bicycle\":{\"color\":\"red\",\"price\":19.95}},\"expensive\":10}";
    JSONObject json = new JSONObject(data);    
    //System.out.println(json.toString(2));
    listJson(json);
  }

}