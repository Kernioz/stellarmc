package fr.kernioz.listeners;

import fr.kernioz.StellarGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandGame implements Listener {

    StellarGame stellarGame;
    public PlayerCommandGame(StellarGame stellarGame){
        this.stellarGame = stellarGame;
    }


    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && event.getMessage().split(" ")[0].contains("reload")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Cette fonctionnalité est désactivé par le plugin StellarGame cause de contraintes techniques (reset de map, base de données Redis).");
        }
        if (player.isOp() && event.getMessage().split(" ")[0].contains("rl")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Cette fonctionnalité est désactivé par le plugin StellarGame cause de contraintes techniques (reset de map, base de données Redis).");
        }
        if (player.isOp() && event.getMessage().split(" ")[0].contains("bukkit:rl")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Cette fonctionnalité est désactivé par le plugin StellarGame cause de contraintes techniques (reset de map, base de données Redis).");
        }
        if (player.isOp() && event.getMessage().split(" ")[0].contains("bukkit:reload")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Cette fonctionnalité est désactivé par le plugin StellarGame cause de contraintes techniques (reset de map, base de données Redis).");
        }
    }

}
