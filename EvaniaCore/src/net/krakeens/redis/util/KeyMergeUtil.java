package net.krakeens.redis.util;

public class KeyMergeUtil {
  public static String[] merge(String destKey, String[] keys) {
    String[] mergedKeys = new String[keys.length + 1];
    mergedKeys[0] = destKey;
    System.arraycopy(keys, 0, mergedKeys, 1, keys.length);
    return mergedKeys;
  }

  public static byte[][] merge(byte[] destKey, byte[][] keys) {
    byte[][] mergedKeys = new byte[keys.length + 1][];
    mergedKeys[0] = destKey;
    System.arraycopy(keys, 0, mergedKeys, 1, keys.length);
    return mergedKeys;
  }
}