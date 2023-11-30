package university;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Helper
{
    //Adapted from: https://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value
    public static <T, E> HashSet<T> getKeysByValue(Map<T, E> map, E value) {
        HashSet<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
}
