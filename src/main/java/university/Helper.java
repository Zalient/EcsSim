package university;


import java.util.*;

public class Helper {
    // Adapted from: https://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value
    // This is used because the mapping might be many-to-one, in which case I would get a set of
    // keys from one value
    public static <T, E> HashSet<T> getKeysByValue(Map<T, E> map, E value) {
        HashSet<T> keys = new HashSet<>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
}

