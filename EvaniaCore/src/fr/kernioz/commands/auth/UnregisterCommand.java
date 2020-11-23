package fr.kernioz.commands.auth;

import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.util.CryptWithMD5;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnregisterCommand implements CommandExecutor {

    private Evania evania;

    public UnregisterCommand(Evania evania){
        this.evania = evania;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player)commandSender;

        if(!(commandSender instanceof Player)){
            System.out.print("StellarCore : Cette commande est exécutable que par les joueurs...");
            return true;
        }

        if(!player.hasPermission("kernioz.unregister")){
            player.sendMessage("§4■ §cVous n'avez pas les permissions nécessaire pour exécuter cette commande.");
            return true;
        }

        if(args.length < 1){
            player.sendMessage("§8■ §7Utiliser: §6/unregister <joueur>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage("§4■ §7Le joueur n'est pas connecté");
            return true;
        }

        PlayerData playerTarget = this.evania.getPlayerDataManager().get(target.getUniqueId().toString());

        if(!playerTarget.contains("registered")){
            player.sendMessage("§4■ §7Ce joueur n'est pas inscrit");
            return true;
        }

        playerTarget.remove("registered");
        playerTarget.remove("password");

        player.sendMessage("§8■ §7Vous avez retiré les informations concernant l'AuthMe au joueur §e" + target.getName() + " §7!");
        target.sendMessage("§7Vous avez été dé-enregistrer du serveur.");
        return false;
    }
}
