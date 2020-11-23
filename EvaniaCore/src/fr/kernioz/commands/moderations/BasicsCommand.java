package fr.kernioz.commands.moderations;

import fr.kernioz.Evania;

import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.api.playerdata.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicsCommand implements CommandExecutor {

    private Evania evania;

    public BasicsCommand(Evania evania) {
        this.evania = evania;
    }



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player p = (Player)commandSender;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String string = simpleDateFormat.format(date);

        if(!(commandSender instanceof Player)){
            System.out.print("StellarCore : Cette commande est exécutable que par les joueurs...");
            return true;
        }

        if (!p.hasPermission("kernioz.admin")){
            p.sendMessage("§4■ §cVous n'avez pas les permissions nécessaire pour exécuter cette commande.");
            return true;
        }
        if (args.length == 0){

            p.sendMessage("§6StellarMC §8» §7Utiliser §b/basics help §7pour en savoir plus.");

        }

        if (args.length >= 1){
            if (args[0].equalsIgnoreCase("setgrade")){
                if (args.length == 1) {
                    p.sendMessage("§6StellarMC §8» §7Utilisez §b/basics setgrade joueur grade §7pour attribuer un rang.");
                } else {

                    Player t = Bukkit.getPlayer(args[1]);
                    String rank = args[2];
                    PlayerData padt = this.evania.getPlayerDataManager().get(t.getUniqueId().toString());

                    if (Bukkit.getPlayer(args[1]) == null){
                        p.sendMessage("§cErreur : §cLe joueur '"+args[1]+"' §c est §chors ligne.");
                    } else if (padt.getRank().getName() == Rank.get(rank).getName()){
                        p.sendMessage("§cErreur : §cLe joueur ciblé possède §cdéjà le rang souhaité !");
                    } else if (Rank.get(rank) == null){
                        p.sendMessage("§cErreur : §c'"+rank+"' §cest inexistant !");
                    } else {
                        padt.setRank(Rank.get(rank));
                        p.sendMessage("§6StellarMC §8» §e"+t.getName()+ " §7possède maintenant les droits de §d"+rank+" §7!");
                    }


                }
            }
            if (args[0].equalsIgnoreCase("reload")){

                p.sendMessage("§6StellarMC §8» §7La commande a été §cdésactivé§7 (reset de map, chargement des joueurs...)");

            }

            if (args[0].equalsIgnoreCase("gradelist")){
                p.sendMessage("§6StellarMC §8» §fRang disponible sur le serveur.");
                p.sendMessage("§r");
                for (Rank ranks : Rank.values()){

                    p.sendMessage("§7 - §b"+ ranks.getID() + " - " + ranks.toString());

                }

            }
            if (args[0].equalsIgnoreCase("reboot")){

                p.sendMessage("§6StellarMC §8» §7Le serveur va redémarrer, StellarCore est entrain de sauvegarder les §bdonnées§7 des §ajoueurs§7!");
                Bukkit.broadcastMessage("§7§m--*---------------------------------------------*--");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§8» §7Le serveur §d" + Bukkit.getServerId() + "§7 va §eredémarrer§7.");
                Bukkit.broadcastMessage("§8» §7Temps restant avant le redémarrage : §e30 secondes§7.");
                Bukkit.broadcastMessage("§8» §cVous serez automatiquement téléportés à un hub.");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§7§m--*---------------------------------------------*--");
              //  RestartTask.reboot();

            }
            if (args[0].equalsIgnoreCase("help")){

                p.sendMessage("§7§m--*---------------------------------------------*--");
                p.sendMessage("");
                p.sendMessage("§8» §d/basics setgrade §7permet d'attribuer un rang à un joueur!");
                p.sendMessage("§8» §d/basics gradelist §7permet de check les grades disponible!");
               // p.sendMessage("§8» §d/basics stop §7permet de redémarrer le serveur où vous êtes.");
                p.sendMessage("§8» §d/basics reboot §7permet de redémarrer le serveur où vous êtes.");
                p.sendMessage("§8» §d/basics players §7permet d'afficher tout les joueurs connectés sur le serveur où vous êtes.");
                p.sendMessage("§8» §d/basics removecredits §7permet de retirer des crédits à un joueur.");
                p.sendMessage("§8» §d/basics addcredits §7permet d'ajouter des crédits à un joueur.");
                p.sendMessage("");
                p.sendMessage("§7§m--*---------------------------------------------*--");

            }
            if (args[0].equalsIgnoreCase("players")){

                p.sendMessage("§6StellarMC §8» §7Chargement des données de §b"+Bukkit.getOnlinePlayers().size()+" §7joueurs...");
                for (Player players : Bukkit.getOnlinePlayers()){
                    PlayerData padp = this.evania.getPlayerDataManager().get(players.getUniqueId().toString());
                    p.sendMessage("§6[Basics] §a"+players.getName()+" §7- §f"+padp.getRank()+" §7- §7§o"+string);
                }

            }
            if (args[0].equalsIgnoreCase("addcredits")){

                if (args.length == 1) {
                    p.sendMessage("§6StellarMC §8» §7Utiliser §e/basics addcredits joueur montant §7pour attribuer un nombre de crédits.");
                } else {
                    Player t = Bukkit.getPlayer(args[1]);
                    double montant = Double.valueOf(args[2]);

                    if (Bukkit.getPlayer(args[1]) == null) {
                        p.sendMessage("§cErreur : §cLe joueur '"+args[1]+"' §c est §chors ligne.");
                    } else if (montant < 1){
                        p.sendMessage("§6StellarMC §8» §7Veuillez mettre un chiffre. §e0.0 §cn'est pas accepté.");
                    } else {
                        PlayerData padp = this.evania.getPlayerDataManager().get(t.getUniqueId().toString());

                        padp.creditCoins(montant);

                        double result = (padp.getStellarCoins() + montant);
                        p.sendMessage("§6StellarMC §8» §f"+t.getName()+ " §7posséde maintenant §b"+result+" StellarCoins §7!");


                    }


                }

            }
            if (args[0].equalsIgnoreCase("removecredits")){

                if (args.length == 1) {
                    p.sendMessage("§6Evania §8» §7Utiliser §e/basics removecredits joueur montant §7pour retirer un nombre de crédits.");
                } else {
                    Player t = Bukkit.getPlayer(args[1]);
                    double montant = Double.valueOf(args[2]);

                    if (Bukkit.getPlayer(args[1]) == null) {
                        p.sendMessage("§cErreur : §cLe joueur '"+args[1]+"' §c est §chors ligne.");
                    } else if (montant < 1){
                        p.sendMessage("§6StellarMC §8» §7Veuillez mettre un chiffre. §e0.0 §cn'est pas accepté.");
                    } else {
                        PlayerData padp = this.evania.getPlayerDataManager().get(t.getUniqueId().toString());

                        padp.removeCoins(montant);
                        double result = (padp.getStellarCoins() - montant);
                        p.sendMessage("§6StellarMC §8» §f"+t.getName()+ " §7posséde maintenant §b"+result+" StellarCoins §7!");

                    }


                }

            }
        }

        return false;
    }
}
