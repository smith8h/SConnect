package smith.lib.net;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.json.*;

public class SResponse {
    private Map object;
    private Array array;
    private String response;

    public SResponse(String response) {
        this.response = response;
        try { object = new Map(response); }
        catch (Exception e) {
            try { array = new Array(response); }
            catch (Exception ee) {}
        }
    }

    public boolean isJSON() {
        return object != null || array != null;
    }

    public boolean isMap() {
        return object != null;
    }

    public boolean isArray() {
        return array != null;
    }

    @Override
    public String toString() {
        return response;
    }

    public Map getMap() {
        return object;
    }

    public Array getArray() {
        return array;
    }

    public class Array {

        private List<Object> list = new ArrayList<>();
        private String response;

        public Array(String json) {
            response = json;
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.get(i));
                }
            } catch (Exception e) {}
        }

        public Object get(int index) {
            return list.get(index);
        }

        public String getString(int index) {
            return list.get(index).toString();
        }

        public int getInt(int index) {
            return Integer.valueOf(list.get(index).toString());
        }

        public float getFloat(int index) {
            return Float.valueOf(list.get(index).toString());
        }

        public boolean getBoolean(int index) {
            return (boolean) list.get(index);
        }

        public Map getMap(int index) {
            return new Map(list.get(index).toString());
        }

        public Array getArray(int index) {
            return new Array(list.get(index).toString());
        }

        public boolean contains(Object object) {
            return list.contains(object);
        }

        public int size() {
            return list.size();
        }
        
        public boolean isEmpty() {
            return list.isEmpty();
        }
        
        public void forEach(Consumer<Object> consumer) {
        	this.list.forEach(consumer);
        }

        @Override
        public String toString() {
            return response;
        }

        public List<Object> toList() {
            return list;
        }
    }

    public class Map {

        private HashMap<String, Object> map = new HashMap<>();
        private String response;

        public Map(String json) {
            response = json;
            try {
                JSONObject object = new JSONObject(json);
                Iterator<String> keys = object.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    map.put(key, object.get(key));
                }
            } catch (Exception e) {}
        }

        public Object get(String key) {
            return map.get(key);
        }

        public String getString(String key) {
            return map.get(key).toString();
        }

        public int getInt(String key) {
            return Integer.valueOf(map.get(key).toString());
        }

        public float getFloat(String key) {
            return Float.valueOf(map.get(key).toString());
        }

        public boolean getBoolean(String key) {
            return (boolean) map.get(key);
        }

        public Map getMap(String key) {
            return new Map(map.get(key).toString());
        }

        public Array getArray(String key) {
            return new Array(map.get(key).toString());
        }

        public Set<String> keys() {
            return map.keySet();
        }
        
        public List<Object> values() {
        	List<Object> values = new ArrayList<>();
            for (String key: keys()) values.add(map.get(key));
            return values;
        }

        public boolean hasKey(String key) {
            return map.get(key) != null;
        }
        
        public boolean hasValue(Object value) {
            return values().contains(value);
        }
        
        public void forEach(BiConsumer<String, Object> consumer) {
        	this.map.forEach(consumer);
        }

        public int size() {
            return map.size();
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public String toString() {
            return response;
        }

        public HashMap<String, Object> toMap() {
            return map;
        }
    }
}
