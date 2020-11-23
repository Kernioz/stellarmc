package fr.kernioz.listeners;

import com.massivecraft.factions.FPlayers;
import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.api.playerdata.rank.Rank;
import fr.kernioz.commands.moderations.SlowCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerChatListener implements Listener {

    private Map<String, Long> lastSpeak = new HashMap<>();


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = Evania.get().getPlayerDataManager().get(player.getUniqueId().toString());
        Rank rank = playerData.getRank();

        event.setCancelled(true);
        if(SlowCommand.slow > 0){
            Long lastSpeakingStamp = lastSpeak.get(player.getName());

            if(lastSpeakingStamp == null) lastSpeakingStamp = 0L;
            long result = System.currentTimeMillis() - lastSpeakingStamp;

            if(result < (SlowCommand.slow * 1000) && !(player.hasPermission("kernioz.slow"))){
                player.sendMessage("§7§oLe chat est sous Slow, un message toutes les §e" + SlowCommand.slow + " seconde(s)§7§o.");
                event.setCancelled(true);
                return;
            }

            ChatColor afterMessage = ChatColor.WHITE;
            if(playerData.contains("ismod")) afterMessage = ChatColor.AQUA;

            String format;
            if(FPlayers.getInstance().getById(player.getUniqueId().toString()).hasFaction()){
                format = "§8[§f" + FPlayers.getInstance().getById(player.getUniqueId().toString()).getTag() + "§8] " + rank.getPrefix() + player.getName() + ": " + afterMessage + event.getMessage();
            } else {
                format = rank.getPrefix() + player.getName() + ": " + afterMessage + event.getMessage();
            }

            for(Player onlinePlayer : Bukkit.getOnlinePlayers()){

                if(event.getMessage().contains(onlinePlayer.getName())){

                    if(onlinePlayer.equals(player)) continue;

                    onlinePlayer.sendMessage(format.replace(onlinePlayer.getName(), "§3@" + onlinePlayer.getName() + afterMessage));
                    onlinePlayer.playNote(onlinePlayer.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.A));

                } else {
                    onlinePlayer.sendMessage(format);
                }

            }
            lastSpeak.put(player.getName(), System.currentTimeMillis());
            return;
        }

        ChatColor afterMessage = ChatColor.WHITE;
        if(playerData.contains("ismod")) afterMessage = ChatColor.AQUA;

        String format;
        if(FPlayers.getInstance().getById(player.getUniqueId().toString()).hasFaction()){
            format = "§8[§f" + FPlayers.getInstance().getById(player.getUniqueId().toString()).getTag() + "§8] " + rank.getPrefix() + player.getName() + ": " + afterMessage + event.getMessage();
        } else {
            format = rank.getPrefix() + player.getName() + ": " + afterMessage + event.getMessage();
        }

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){

            if(event.getMessage().contains(onlinePlayer.getName())){

                if(onlinePlayer.equals(player)) continue;

                onlinePlayer.sendMessage(format.replace(onlinePlayer.getName(), "§3@" + onlinePlayer.getName() + afterMessage));
                onlinePlayer.playNote(onlinePlayer.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.A));

            } else {
                onlinePlayer.sendMessage(format);

            }

        }

    }

}
