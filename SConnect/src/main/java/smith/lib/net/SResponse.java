/*
 * Copyright (C) 2023, Hussein Shakir (Dev. Smith)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package smith.lib.net;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


/**
 *  The powerful class that manage the result of the connection as JSON objects or arrays,
 *  or simple string response if needed.
 */
@SuppressWarnings({"unused"})
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
     * Deal with Array json response got from connection result.
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

    /**
     * Deal with Object json response got from connection result.
     */
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

        /**
         * @param key the key of the value needed.
         * @return the value as wild Object (Any) type.
         */
        public Object get(String key) {
            return map.get(key);
        }

        /**
         * @param key th key of the string value needed.
         * @return String value stored in that key.
         */
        public String getString(String key) {
            return Objects.requireNonNull(map.get(key)).toString();
        }

        /**
         * @param key th key of the Int value needed.
         * @return Int value stored in that key.
         */
        public int getInt(String key) {
            return Integer.parseInt(Objects.requireNonNull(map.get(key)).toString());
        }

        /**
         * @param key th key of the float value needed.
         * @return float value stored in that key.
         */
        public float getFloat(String key) {
            return Float.parseFloat(Objects.requireNonNull(map.get(key)).toString());
        }

        /**
         * @param key th key of the boolean value needed.
         * @return boolean value stored in that key.
         */
        public boolean getBoolean(String key) {
            return (boolean) Objects.requireNonNull(map.get(key));
        }

        /**
         * @param key the key of the SResponse.Map Object value needed.
         * @return SResponse.Map Object value stored in that key.
         */
        public Map getMap(String key) {
            return new Map(Objects.requireNonNull(map.get(key)).toString());
        }

        /**
         * @param key the key of the SResponse.Array Object value needed.
         * @return SResponse.Array Object value stored in that key.
         */
        public Array getArray(String key) {
            return new Array(Objects.requireNonNull(map.get(key)).toString());
        }

        /**
         * @return A set of keys as string set in the Map object.
         */
        public Set<String> keys() {
            return map.keySet();
        }

        /**
         * @return A list of values as List of Objects.
         */
        public List<Object> values() {
        	List<Object> values = new ArrayList<>();
            for (String key: keys()) values.add(map.get(key));
            return values;
        }

        /**
         * @param key the key you need to check its existence.
         * @return true if the key is already in the object.
         */
        public boolean hasKey(String key) {
            return map.get(key) != null;
        }

        /**
         * @param value the value you need to check its existence.
         * @return true if the value is already in the object.
         */
        public boolean hasValue(Object value) {
            return values().contains(value);
        }

        /**
         * Iterate through each item in the Array.
         * @param consumer A function that deal with the item's key and value when iterating through items.
         */
        public void forEach(BiConsumer<String, Object> consumer) {
        	this.map.forEach(consumer);
        }

        /**
         * @return The size of keys stored in that object.
         */
        public int size() {
            return map.size();
        }

        /**
         * @return true if the object is empty.
         */
        public boolean isEmpty() {
            return map.isEmpty();
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
         * @return the response as Map of string keys and object values.
         */
        public HashMap<String, Object> toMap() {
            return map;
        }
    }
}
