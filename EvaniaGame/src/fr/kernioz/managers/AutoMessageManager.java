package fr.kernioz.managers;

import fr.kernioz.StellarGame;
import fr.kernioz.util.PlayerUtils;
import fr.kernioz.util.tasksmanager.TaskManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutoMessageManager {

    protected StellarGame plugin;

    protected List<String> messageList;
    protected Iterator<String> messageIterator;
    protected int time = 0;
    protected String message;

    public AutoMessageManager(StellarGame plugin) {
        this.plugin = plugin;

        this.messageList = new ArrayList<>();
        messageList.add("§7Bienvenue sur §6§lStellarMC");
        messageList.add("§7Suivez-nous sur notre Twitter §b@StellarMC");
        messageList.add("§7Rejoignez notre serveur Discord : §ddiscord.stellarmc.fr");
        messageList.add("§7Jetez un coup d'oeil à notre site web : §awww.stellarmc.fr");
        this.messageIterator = messageList.iterator();

        TaskManager.scheduleSyncRepeatingTask("BroadcastsMessage", this::update, 0, 100);

    }

    public void update(){
        time--;

        if(time <= 0){
            if(!messageIterator.hasNext()){
                this.messageIterator = messageList.iterator();

            }

            this.message = messageIterator.next();
            time = 150;

        }

        if (time < 4){
            Bukkit.getOnlinePlayers().forEach(player -> PlayerUtils.sendActionMessage(player, message));

        }

    }

}
