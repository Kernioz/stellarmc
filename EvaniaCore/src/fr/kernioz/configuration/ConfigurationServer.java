package fr.kernioz.configuration;

import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.rank.Rank;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigurationServer {

    Evania evania;
    private FileConfiguration fileConfiguration;

    public ConfigurationServer(Evania evania){
        this.evania = evania;
        this.fileConfiguration = evania.getConfig();
        evania.saveDefaultConfig();
    }

    /*
       Récupération des informations concernant la base de
       données Redis.
     */

    public String getHost(){ return fileConfiguration.getString("kernioz.redis-server.host");}
    public String getPassword(){ return fileConfiguration.getString("kernioz.redis-server.password");}
    public String getPort(){ return fileConfiguration.getString("kernioz.redis-server.port");}



    public String getServer(){ return fileConfiguration.getString("kernioz.server-send"); }


    /*
        Ranks
     */

    public List<String> getPermissions(Rank hd) { return fileConfiguration.getStringList("kernioz.ranks."+hd.getName()+".permissions"); }
    public String getPrefix(Rank hd) { return fileConfiguration.getString("kernioz.ranks."+hd.getName()+".prefix").replace("&", "§"); }
    public String getPrefix(String hd) { return fileConfiguration.getString("kernioz.ranks."+hd+".prefix").replace("&", "§"); }
    public String getTab(Rank hd) { return fileConfiguration.getString("kernioz.ranks."+hd.getName()+".tab").replace("&", "§"); }
    public String getTab(String hd) { return fileConfiguration.getString("kernioz.ranks."+hd+".tab").replace("&", "§"); }

}
