package fr.kernioz.scoreboard;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import fr.kernioz.Evania;
import fr.kernioz.StellarGame;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.util.ColorScroll;
import fr.kernioz.util.tasksmanager.TaskManager;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Scoreboard {

    private StellarGame evania;

    public Scoreboard(StellarGame evania){
        this.evania = evania;
    }

    public void loadScoreboard(Player player){
        BPlayerBoard board = Netherboard.instance().createBoard(player, "§6§lSTELLARMC");
        ColorScroll ip = new ColorScroll(ChatColor.GOLD, "play.stellarmc.fr", "§f", "§e", "§e", false, false, 20);

        PlayerData playerData = Evania.get().getPlayerDataManager().get(player.getUniqueId().toString());

        TaskManager.scheduleSyncRepeatingTask("ScoreboardPlayer_"+player.getName(), () -> {

            if(FPlayers.getInstance().getById(player.getUniqueId().toString()).hasFaction()) {
                board.set("§f", 11);
                board.set("§f§l MOI ", 10);
                try {
                    board.set(" §7 Monnaie(s): §a"+ Economy.getMoney(player.getName()), 9);
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                }
                if(playerData.getStellarCoins() == 0){
                    board.set(" §7 StellarCoins: §b0", 8);
                } else {
                    board.set(" §7 StellarCoins: §b" + playerData.getStellarCoins(), 8);
                }

                board.set(" §7 Power: §c" + FPlayers.getInstance().getById(player.getUniqueId().toString()).getPowerRounded(), 7);
                board.set("§f§l " + FPlayers.getInstance().getById(player.getUniqueId().toString()).getTag(), 6);
                board.set("§7  Power: §a" + Factions.getInstance().getByTag(FPlayers.getInstance().getById(player.getUniqueId().toString()).getTag()).getPowerRounded(), 5);
                board.set("§7  Membres: §d" + Factions.getInstance().getByTag(FPlayers.getInstance().getById(player.getUniqueId().toString()).getTag()).getFPlayers().size(), 4);
            }else{
                board.remove(9);
                board.set("§f", 8);
                board.set("§f§l MOI ", 7);
                try {
                    board.set(" §7 Monnaie(s): §a"+ Economy.getMoney(player.getName()), 6);
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                }
                if(playerData.getStellarCoins() == 0){
                    board.set(" §7 StellarCoins: §b0", 5);
                } else {
                    board.set(" §7 StellarCoins: §b" + playerData.getStellarCoins(), 5);
                }

                board.set(" §7 Power: §c" + FPlayers.getInstance().getById(player.getUniqueId().toString()).getPowerRounded(), 4);
            }
            board.set("§b", 3);
            board.set("§7"+Bukkit.getOnlinePlayers().size() + " joueur(s)", 2);
            board.set(ip.next(), 1);
        }, 0,2);

    }
}
