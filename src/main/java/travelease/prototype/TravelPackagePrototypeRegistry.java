package travelease.prototype;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import travelease.TravelItemAPI;

public class TravelPackagePrototypeRegistry {

    private static final Map<String, TravelItemAPI> registry = new HashMap<>();

    private TravelPackagePrototypeRegistry() {
    }

    public static void registerPrototype(String key, TravelItemAPI prototype) {
        registry.put(key, prototype);
    }

    @SuppressWarnings("unchecked")
    public static <T extends TravelItemAPI> T getPrototype(String key) {
        return (T) registry.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T extends TravelItemAPI> T getPrototypeClone(String key) {
        TravelItemAPI proto = registry.get(key);
        if (proto == null) {
            System.out.println("No prototype registered for key: " + key);
            return null;
        }
        return (T) proto.clone();
    }

    public static Set<String> getRegisteredKeys() {
        return Collections.unmodifiableSet(registry.keySet());
    }
}
