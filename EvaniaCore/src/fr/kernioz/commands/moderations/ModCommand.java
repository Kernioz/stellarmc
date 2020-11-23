package fr.kernioz.commands.moderations;

import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.api.playerdata.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        PlayerData playerData = Evania.get().getPlayerDataManager().get(player.getUniqueId().toString());

        if(!(commandSender instanceof Player)){
            System.out.print("StellarCore : Cette commande est exécutable que par les joueurs...");
            return true;
        }

        if(!player.hasPermission("kernioz.mod")){
            player.sendMessage("§4■ §cVous n'avez pas les permissions nécessaire pour exécuter cette commande.");
            return true;
        }

        if(!playerData.contains("ismod")){
            player.sendMessage("§dLe mode modérateur a été activé");
            playerData.setBoolean("ismod", true);
            return true;
        }

        player.sendMessage("§dLe mode modérateur a été désactivé");
        playerData.setBoolean("isMod", false);
        playerData.remove("ismod");

        return false;
    }
}
