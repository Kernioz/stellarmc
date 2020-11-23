package fr.kernioz.configuration;

import fr.kernioz.Evania;
import fr.kernioz.StellarGame;
import fr.kernioz.api.playerdata.rank.Rank;
import fr.kernioz.util.ParseLocation;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigurationGame {

    StellarGame evania;
    private FileConfiguration fileConfiguration;

    public ConfigurationGame(StellarGame evania){
        this.evania = evania;
        this.fileConfiguration = evania.getConfig();
        evania.saveDefaultConfig();
    }

    public void reloadConfiguration(StellarGame plugin){
        this.fileConfiguration = plugin.getConfig();
        plugin.reloadConfig();
      //  plugin.onReload();
    }

    public Location spawnLocation(){
        String rawLocation = fileConfiguration.getString("kernioz.spawn-location");
        return ParseLocation.parseLocationWithDefaultMap(rawLocation);
    }

    public List<String> getLines(String hd) { return fileConfiguration.getStringList("kernioz.holographics." + hd.replace("§", "&")); }

    public String getMessage(String message){
        return this.fileConfiguration.getString("kernioz.message." + message);
    }
}
