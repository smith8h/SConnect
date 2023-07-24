package smith.lib.net;

import androidx.annotation.NonNull;
import java.util.*;
import java.util.function.*;
import org.json.*;


/**
 *  The powerful class that manage the result of the connection as JSON objects or arrays,
 *  or simple string response if needed.
 */
@SuppressWarnings({"Unused"})
public class SResponse {
    private Map object;
    private Array array;
    private final String response;

    public SResponse(String response) {
        this.response = response;
        try { object = new Map(response); }
        catch (Exception e) {
            try { array = new Array(response); }
            catch (Exception ignored) {}
        }
    }

    /**
     * Check the response for JSON validity.
     * @return true if the response is json in general.
     */
    public boolean isJSON() {
        return object != null || array != null;
    }

    /**
     * Check the response for JSON Object validity.
     * @return true if the response is json object.
     */
    public boolean isMap() {
        return object != null;
    }

    /**
     * Check the response for JSON Array validity.
     * @return true if the response is json array.
     */
    public boolean isArray() {
        return array != null;
    }

    /**
     * @return the response as it received from connection result.
     */
    @NonNull
    @Override
    public String toString() {
        return response;
    }

    /**
     * @return the response as JSON Object.
     */
    public Map getMap() {
        return object;
    }

    /**
     * @return the response as JSON Array.
     */
    public Array getArray() {
        return array;
    }

    /**
     * Deal with array json response got from connection result.
     */
    public class Array {

        private final List<Object> list = new ArrayList<>();
        private final String response;

        public Array(String json) {
            response = json;
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.get(i));
                }
            } catch (Exception ignored) {}
        }

        /**
         * @param index the index of Object item
         * @return Wild Object (Any) at that index.
         */
        public Object get(int index) {
            return list.get(index);
        }

        /**
         * @param index the index of Object item
         * @return string value at that index.
         */
        public String getString(int index) {
            return list.get(index).toString();
        }

        /**
         * @param index the index of Object item
         * @return int value at that index.
         */
        public int getInt(int index) {
            return Integer.parseInt(list.get(index).toString());
        }

        /**
         * @param index the index of Object item
         * @return float value at that index.
         */
        public float getFloat(int index) {
            return Float.parseFloat(list.get(index).toString());
        }

        /**
         * @param index the index of Object item
         * @return boolean value at that index.
         */
        public boolean getBoolean(int index) {
            return (boolean) list.get(index);
        }

        /**
         * @param index the index of Object item
         * @return SResponse.Map object at that index.
         */
        public Map getMap(int index) {
            return new Map(list.get(index).toString());
        }

        /**
         * @param index the index of Object item
         * @return SResponse.Array object at that index.
         */
        public Array getArray(int index) {
            return new Array(list.get(index).toString());
        }

        /**
         * @param object any value
         * @return true if that value exist in Array object.
         */
        public boolean contains(Object object) {
            return list.contains(object);
        }

        /**
         * @return The size of the Array.
         */
        public int size() {
            return list.size();
        }

        /**
         * @return true if the Array is empty.
         */
        public boolean isEmpty() {
            return list.isEmpty();
        }

        /**
         * Iterate through each item in the Array.
         * @param consumer A function that deal with the item when iterating through items.
         */
        public void forEach(Consumer<Object> consumer) {
        	this.list.forEach(consumer);
        }

        /**
         * @return the response as it received from connection result.
         */
        @NonNull
        @Override
        public String toString() {
            return response;
        }

        /**
         * @return Array object as List of Objects, no matter what the values inside it.
         */
        public List<Object> toList() {
            return list;
        }
    }

    public class Map {

        private final HashMap<String, Object> map = new HashMap<>();
        private final String response;

        public Map(String json) {
            response = json;
            try {
                JSONObject object = new JSONObject(json);
                Iterator<String> keys = object.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    map.put(key, object.get(key));
                }
            } catch (Exception ignored) {}
        }

        public Object get(String key) {
            return map.get(key);
        }

        public String getString(String key) {
            return Objects.requireNonNull(map.get(key)).toString();
        }

        public int getInt(String key) {
            return Integer.parseInt(Objects.requireNonNull(map.get(key)).toString());
        }

        public float getFloat(String key) {
            return Float.parseFloat(Objects.requireNonNull(map.get(key)).toString());
        }

        public boolean getBoolean(String key) {
            return (boolean) map.get(key);
        }

        public Map getMap(String key) {
            return new Map(Objects.requireNonNull(map.get(key)).toString());
        }

        public Array getArray(String key) {
            return new Array(Objects.requireNonNull(map.get(key)).toString());
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

        @NonNull
        @Override
        public String toString() {
            return response;
        }

        public HashMap<String, Object> toMap() {
            return map;
        }
    }
}
