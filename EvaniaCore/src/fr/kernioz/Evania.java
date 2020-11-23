package fr.kernioz;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.kernioz.commands.LagCommand;
import fr.kernioz.commands.SponsorCommand;
import fr.kernioz.commands.auth.LoginCommand;
import fr.kernioz.commands.auth.RegisterCommand;
import fr.kernioz.commands.auth.UnregisterCommand;
import fr.kernioz.commands.moderations.*;
import fr.kernioz.connection.DatabaseConnector;
import fr.kernioz.connection.SingleDatabaseConnector;
import fr.kernioz.api.playerdata.PlayerDataManager;
import fr.kernioz.api.playerdata.PlayerLocalManager;

import fr.kernioz.configuration.ConfigurationServer;
import fr.kernioz.listeners.PlayerConnectionListener;
import fr.kernioz.util.logger.Log4JFilter;
import fr.kernioz.util.logger.LoginFilter;
import fr.kernioz.util.tasksmanager.TasksExecutor;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.scheduler.BukkitRunnable;

import javax.persistence.Basic;
import java.util.logging.Filter;

public class Evania extends JavaPlugin {

    private static Evania evania;
    private ConfigurationServer configurationServer = new ConfigurationServer(this);

    private TasksExecutor tasksManager;
    private DatabaseConnector connector;

    private PlayerDataManager playerDataManager;
    private PlayerLocalManager playerLocalManager;

    public DatabaseConnector getConnector() {
        return connector;
    }
    public TasksExecutor getTasksManager() {
        return tasksManager;
    }
    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
    public PlayerLocalManager getPlayerLocalManager() {
        return playerLocalManager;
    }
    public static Evania get() {
        return evania;
    }
    public ConfigurationServer getConfigurationServer() { return configurationServer; }

    @Override
    public void onLoad() {
        evania = this;
        initializeDatabase();
    }

    @Override
    public void onEnable() {
        System.out.println("[StellarCore] Le plugin est actif");
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerConnectionListener(this), this);

        getLogger().setFilter((Filter)new LoginFilter());
        Bukkit.getLogger().setFilter((Filter)new LoginFilter());
        java.util.logging.Logger.getLogger("Minecraft").setFilter((Filter)new LoginFilter());
        getLogger().setFilter((Filter)new LoginFilter());
        try {
            Class.forName("org.apache.logging.log4j.core.Filter");
            setLog4JFilter();
        } catch (ClassNotFoundException|NoClassDefFoundError classNotFoundException) {}

        /*
           Commands
         */

        getCommand("lag").setExecutor(new LagCommand());
        getCommand("sponsor").setExecutor(new SponsorCommand());
        getCommand("unregister").setExecutor(new UnregisterCommand(this));
        getCommand("register").setExecutor(new RegisterCommand(this));
        getCommand("reg").setExecutor(new RegisterCommand(this));
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("l").setExecutor(new LoginCommand(this));

        /*
          Modération

         */

        getCommand("basics").setExecutor(new BasicsCommand(this));

        getCommand("mod").setExecutor(new ModCommand());
        getCommand("tp").setExecutor(new TPCommand());
        getCommand("tph").setExecutor(new TPHCommand());
        getCommand("slow").setExecutor(new SlowCommand(this));

        Bukkit.getMessenger().registerOutgoingPluginChannel((org.bukkit.plugin.Plugin)this, "BungeeCord");


        /*
           Serveur Auth

         */

        new BukkitRunnable(){
            @Override
            public void run() {
                if(getServer().getServerName().contains("Auth")){
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.hidePlayer(player);
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        connector.disable();
        System.out.println("[StellarCore] Le plugin n'est plus actif");
    }



    private void initializeDatabase(){
        /*
           La connexion à la base de données Redis.
         */

        String host = this.configurationServer.getHost();
        String password = this.configurationServer.getPassword();


        connector = new SingleDatabaseConnector(this, host, password);
        tasksManager = new TasksExecutor();
        new Thread(tasksManager, "ExecutorThread").start();

        /*
           Serveur Manager
         */

        playerLocalManager = new PlayerLocalManager();
        playerDataManager = new PlayerDataManager(this);

    }

    private void setLog4JFilter() {
        Bukkit.getScheduler().scheduleSyncDelayedTask((org.bukkit.plugin.Plugin)this, () -> {
            Logger coreLogger = (Logger) LogManager.getRootLogger();
            coreLogger.addFilter((org.apache.logging.log4j.core.Filter) new Log4JFilter());
        });
    }

    public void sendPlayer(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(this.configurationServer.getServer());
        player.sendPluginMessage((org.bukkit.plugin.Plugin)this, "BungeeCord", out.toByteArray());
    }

}
