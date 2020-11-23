package fr.kernioz.scheduler;

import fr.kernioz.StellarGame;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WelcomeTask extends BukkitRunnable {

    int timer = 2;
    StellarGame stellarGame;
    Player player;


    public WelcomeTask(StellarGame stellarGame, Player player){
        this.stellarGame = stellarGame;
        this.player = player;
    }

    @Override
    public void run(){

        if(timer == 0) {
            cancel();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1L, 1L);
        }

        timer--;
    }


}
