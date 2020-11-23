package fr.kernioz.listeners;

import fr.kernioz.StellarGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathGame implements Listener {


    StellarGame stellarGame;

    public PlayerDeathGame(StellarGame stellarGame){
        this.stellarGame = stellarGame;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player kill = p.getKiller();

        e.setDeathMessage(null);
        double rounded = Math.round(kill.getHealth() * 10) / 10;
        if (p instanceof Player && kill instanceof Player) {
            try {
                kill.sendMessage("§6+ 200 $ §7(§e§oÉlimination: " + p.getName() + "§7)");
                Bukkit.broadcastMessage("§c" + kill.getName() + " §7a tué §c" + p.getName() + " §8- §4" +  rounded + " ❤");
                kill.setHealth(20D);
                //pd.addDeaths(1);
                //padKiller.addKills(1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (p instanceof Player)  {
            Bukkit.broadcastMessage("§c" + kill.getName() + " §7est mort §8- §4" +  rounded + " ❤");
            //pd.addDeaths(1);
        }

    }

}
