package fr.kernioz;

import com.massivecraft.factions.FPlayers;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.commands.KitCommand;
import fr.kernioz.configuration.ConfigurationGame;
import fr.kernioz.listeners.PlayerChatListener;
import fr.kernioz.listeners.PlayerCommandGame;
import fr.kernioz.listeners.PlayerConnectionGame;
import fr.kernioz.listeners.PlayerDeathGame;
import fr.kernioz.managers.BroadcastManager;
import fr.kernioz.util.TitleUtils;
import fr.watch54.display.holograms.HologramServer;
import fr.watch54.display.interfaces.Text;
import fr.watch54.display.managers.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StellarGame extends JavaPlugin {

    private ConfigurationGame configurationServer = new ConfigurationGame(this);
    private HologramManager hologramManager;
    private BroadcastManager broadcastManager;

    public HologramManager getHologramManager() {
        return hologramManager;
    }
    public ConfigurationGame getConfigurationServer() { return configurationServer; }

    @Override
    public void onEnable() {

        this.hologramManager = new HologramManager(this);
        this.broadcastManager = new BroadcastManager(this);


        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerCommandGame(this), this);
        pluginManager.registerEvents(new PlayerConnectionGame(this), this);
        pluginManager.registerEvents(new PlayerDeathGame(this), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);


        getServer().getPluginCommand("kit").setExecutor(new KitCommand());
        getServer().getPluginCommand("kits").setExecutor(new KitCommand());

        new BukkitRunnable(){
            @Override
            public void run() {
               onEnableS();
        //        new LoadHolos(evania).classementDeath(); // bienvenue
            }
        }.runTaskLater(this, 60L);


        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerData playerData = Evania.get().getPlayerDataManager().get(p.getUniqueId().toString());
                    if (FPlayers.getInstance().getById(p.getUniqueId().toString()).hasFaction()) {
                        TitleUtils.sendTabTitle(p, "§6StellarMC\n§r\n§8■ §7Vous êtes sur le serveur §6" + getServer().getServerName() + " §8■\n§r\n§7Faction: §a" + FPlayers.getInstance().getById(p.getUniqueId().toString()).getTag() + " §8❘ §7StellarCoins: §b" + playerData.getStellarCoins() + "\n§r", "\n§8■ §7Achetez des crédits avec §fPayPal§8/§fAppel & SMS §7sur §b§nwww.stellarmc.fr§r\n§7Voter en jeu grâce à §f§l/vote");
                    } else{
                        TitleUtils.sendTabTitle(p, "§6StellarMC\n§r\n§8■ §7Vous êtes sur le serveur §6" + getServer().getServerName() + " §8■\n§r\n§7Faction: §aAucune §8❘ §7StellarCoins: §b" + playerData.getStellarCoins() + "\n", "§r\n§8■ §7Achetez des crédits avec §fPayPal§8/§fAppel & SMS §7sur §b§nwww.stellarmc.fr§r\n§7Voter en jeu grâce à §f§l/vote");
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 1000L);
    }

    public void onEnableS() {

        /*
            Bienvenue
         */

        List<Text> pInfo = new ArrayList<>();
        for(String bvn : this.getConfigurationServer().getLines("bienvenue")){
            pInfo.add(() -> bvn);
        }

        Location location = new Location(Bukkit.getWorld("world"), 1.354, 85.493, -10.300, 0.0F, 0.0F);
        HologramServer hdBVN = this.getHologramManager().createServer(pInfo, location, true);


        /*
           WarZone
         */

        List<Text> wz = new ArrayList<>();
        for(String wzserv : this.getConfigurationServer().getLines("warzone")){
            wz.add(() -> wzserv);
        }

        Location locationWz = new Location(Bukkit.getWorld("world"), 23.511 ,84 ,-86.518);
        HologramServer holoWz = this.getHologramManager().createServer(wz, locationWz, true);


    }

    @Override
    public void onDisable() {
        this.hologramManager.clear();
    }
}
