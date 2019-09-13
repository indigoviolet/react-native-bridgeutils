/*
  MapUtil exposes a set of helper methods for working with
  ReadableMap (by React Native), Map<String, Object>

  Modified from https://gist.github.com/mfmendiola/bb8397162df9f76681325ab9f705748b
 */

package com.indigoviolet.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

public class MapUtil {
  public static Map<String, Object> toMap(ReadableMap readableMap) {
    Map<String, Object> map = new HashMap<>();
    ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      ReadableType type = readableMap.getType(key);

      switch (type) {
        case Null:
          map.put(key, null);
          break;
        case Boolean:
          map.put(key, readableMap.getBoolean(key));
          break;
        case Number:
          map.put(key, readableMap.getDouble(key));
          break;
        case String:
          map.put(key, readableMap.getString(key));
          break;
        case Map:
          map.put(key, MapUtil.toMap(readableMap.getMap(key)));
          break;
        case Array:
          map.put(key, ArrayUtil.toArray(readableMap.getArray(key)));
          break;
      }
    }

    return map;
  }

  public static WritableMap toWritableMap(Map<?, Object> map) {
    WritableMap writableMap = Arguments.createMap();
    Iterator iterator = map.entrySet().iterator();

    while (iterator.hasNext()) {
      Map.Entry pair = (Map.Entry)iterator.next();
      Object value = pair.getValue();
      String stringKey = String.valueOf(pair.getKey());

      if (value == null) {
        writableMap.putNull(stringKey);
      } else if (value instanceof Boolean) {
        writableMap.putBoolean(stringKey, (Boolean) value);
      } else if (value instanceof Double) {
        writableMap.putDouble(stringKey, (Double) value);
      } else if (value instanceof Float) {
        writableMap.putDouble(stringKey, ((Float) value).doubleValue());
      } else if (value instanceof Integer) {
        writableMap.putInt(stringKey, (Integer) value);
      } else if (value instanceof Long) {
        writableMap.putDouble(stringKey, ((Long) value).doubleValue());
      } else if (value instanceof String) {
        writableMap.putString(stringKey, (String) value);
      } else if (value instanceof Map) {
        writableMap.putMap(stringKey, MapUtil.toWritableMap((Map<?, Object>) value));
      } else if (value instanceof List) {
        writableMap.putArray(stringKey, ArrayUtil.toWritableArray(((List<?>) value).toArray()));
      } else if (value.getClass() != null && value.getClass().isArray()) {
        writableMap.putArray(stringKey, ArrayUtil.toWritableArray(ArrayUtil.getArray(value)));
      }

      iterator.remove();
    }

    return writableMap;
  }

}
