
/*
  ArrayUtil exposes a set of helper methods for working with
  ReadableArray (by React Native), Object[]

  Modified from https://gist.github.com/mfmendiola/bb8397162df9f76681325ab9f705748b

 */

package com.indigoviolet.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;

import java.util.Map;
import java.lang.reflect.Array;

public class ArrayUtil {
  public static Object[] toArray(ReadableArray readableArray) {
    Object[] array = new Object[readableArray.size()];

    for (int i = 0; i < readableArray.size(); i++) {
      ReadableType type = readableArray.getType(i);

      switch (type) {
        case Null:
          array[i] = null;
          break;
        case Boolean:
          array[i] = readableArray.getBoolean(i);
          break;
        case Number:
          array[i] = readableArray.getDouble(i);
          break;
        case String:
          array[i] = readableArray.getString(i);
          break;
        case Map:
          array[i] = MapUtil.toMap(readableArray.getMap(i));
          break;
        case Array:
          array[i] = ArrayUtil.toArray(readableArray.getArray(i));
          break;
      }
    }

    return array;
  }

  public static WritableArray toWritableArray(Object[] array) {
    WritableArray writableArray = Arguments.createArray();

    for (int i = 0; i < array.length; i++) {
      Object value = array[i];

      if (value == null) {
        writableArray.pushNull();
      }
      if (value instanceof Boolean) {
        writableArray.pushBoolean((Boolean) value);
      }
      if (value instanceof Double) {
        writableArray.pushDouble((Double) value);
      }
      if (value instanceof Float) {
        writableArray.pushDouble(((Float) value).doubleValue());
      }
      if (value instanceof Integer) {
        writableArray.pushInt((Integer) value);
      }
      if (value instanceof String) {
        writableArray.pushString((String) value);
      }
      if (value instanceof Map) {
        writableArray.pushMap(MapUtil.toWritableMap((Map<String, Object>) value));
      }
      if (value.getClass().isArray()) {
        writableArray.pushArray(ArrayUtil.toWritableArray(getArray(value)));
      }
    }

    return writableArray;
  }

  // https://stackoverflow.com/a/5608477; handles primitive[] -> Object[]
  public static Object[] getArray(Object val){
    if (val instanceof Object[])
      return (Object[])val;
    int arrlength = Array.getLength(val);
    Object[] outputArray = new Object[arrlength];
    for(int i = 0; i < arrlength; ++i){
      outputArray[i] = Array.get(val, i);
    }
    return outputArray;
  }
}
