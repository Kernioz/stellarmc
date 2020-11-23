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

public class RegisterCommand implements CommandExecutor {

    private Evania evania;

    public RegisterCommand(Evania evania){
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

        if(args.length < 2){
            player.sendMessage("§8■ §7Utiliser: §6/register <mot de passe> <mot de passe>");
            return true;
        }

        if(!(this.evania.getServer().getServerName().contains("Auth"))){
            player.sendMessage("§4■ §7Vous êtes déjà déjà connecté...");
            return true;
        }

        if(playerData.contains("registered")){
            player.sendMessage("§4■ §7Vous êtes déjà enregistré...");
            return true;
        }

        String password = args[0];
        String repassword = args[1];

        if (!password.equals(repassword)) {
            player.sendMessage("§cErreur » Les mots de passes ne correspondent pas !");
            return true;
        }

        String encryptPassword = CryptWithMD5.cryptWithMD5(password);
        playerData.set("password", encryptPassword);
        playerData.setBoolean("registered", true);

        player.sendMessage("§8■ §7Vous avez été enregistré avec §asuccès§7.");
        player.sendMessage("§7 Vous allez être transféré au §eFaction§7...");

        this.evania.sendPlayer(player);

        return false;
    }
}
