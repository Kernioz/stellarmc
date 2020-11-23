package fr.kernioz.commands.auth;

import com.avaje.ebean.annotation.Encrypted;
import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.util.CryptWithMD5;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.events.Event;

public class LoginCommand implements CommandExecutor {

    private Evania evania;

    public LoginCommand(Evania evania){
        this.evania = evania;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player)commandSender;

        PlayerData playerData = this.evania.getPlayerDataManager().get(player.getUniqueId().toString());

        if(!(commandSender instanceof Player)){
            System.out.print("StellarCore : Cette commande est exécutable que par les joueurs...");
            return true;
        }

        if(!playerData.contains("registered")){
            player.sendMessage("§7Votre compte n'existe pas, veuillez vous enregistrer à l'aide de:");
            player.sendMessage("§8■ §7Utiliser: §6/register <mot de passe> <mot de passe>");
            return true;
        }

        if(args.length < 1){
            player.sendMessage("§8■ §7Utiliser: §6/login <mot de passe>");
            return true;
        }

        if(!(this.evania.getServer().getServerName().contains("Auth"))){
            player.sendMessage("§4■ §7Vous êtes déjà déjà connecté...");
            return true;
        }

        if(playerData.contains("logged")){
            player.sendMessage("§4■ §7Vous êtes déjà connecté");
            return true;
        }

        String password = args[0];
        if (CryptWithMD5.cryptWithMD5(password).equals(playerData.get("password"))) {
            player.sendMessage("§8■ §7Connexion avec §asuccès§7.");
            player.sendMessage("§7 Vous allez être transféré au §eFaction§7...");
            playerData.setBoolean("logged", true);
            this.evania.sendPlayer(player);
        } else {
            player.sendMessage("§cErreur » Le mot de passe ne correspond pas...");
            return true;
        }
        return false;
    }
}
