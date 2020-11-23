package fr.kernioz.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ParseLocation {

    public static Location parseLocationWithDefaultMap(String rawLocation){
        return parseLocation("world", rawLocation);

    }

    public static Location parseLocation(String world, String rawLocation){

        String[] raw = rawLocation.split(", ");
        double x = Double.parseDouble(raw[0]);
        double y = Double.parseDouble(raw[1]);
        double z = Double.parseDouble(raw[2]);
        float yaw = Float.parseFloat(raw[3]);
        float pitch = Float.parseFloat(raw[4]);

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

    }

}
