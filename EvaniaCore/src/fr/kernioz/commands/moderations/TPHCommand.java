package fr.kernioz.commands.moderations;

import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.api.playerdata.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPHCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player)commandSender;
        PlayerData playerData = Evania.get().getPlayerDataManager().get(player.getUniqueId().toString());

        if(!(commandSender instanceof Player)){
            System.out.print("StellarCore : Cette commande est exécutable que par les joueurs...");
            return true;
        }

        if(!player.hasPermission("kernioz.tph")){
            player.sendMessage("§4■ §cVous n'avez pas les permissions nécessaire pour exécuter cette commande.");
            return true;
        }

        if(args.length < 1){
            player.sendMessage("§8■ §7Utiliser: §6/tph <joueur>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage("§cErreur : Le joueur n'est pas connecté");
            return true;
        }

        target.teleport(player.getLocation());
        player.sendMessage("§8■ §7Téléportation du joueur §e" + target.getName());
        return false;
    }
}
