package fr.kernioz.commands;


import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.api.playerdata.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SponsorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player)sender;
        PlayerData playerData = Evania.get().getPlayerDataManager().get(player.getUniqueId().toString());

        if(!player.hasPermission("kernioz.sponsor")){
            player.sendMessage("§cErreur : Vous n'avez pas les permissions nécessaire pour exécuter cette commande");
            return true;
        }

        if(args.length < 1){
            player.sendMessage("§8■ §7Utiliser: §6/sponsor <joueur>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage("§cErreur : Le joueur n'est pas connecté");
            return true;
        }

        PlayerData targetData = Evania.get().getPlayerDataManager().get(target.getUniqueId().toString());
        if(targetData.getRank() == Rank.DUC || targetData.getRank() == Rank.ANGE || targetData.getRank() == Rank.DEMON || targetData.getRank() == Rank.ROI || targetData.getRank() == Rank.PRINCE || targetData.getRank() == Rank.YOUTUBEUR || targetData.getRank() == Rank.PARTENAIRE || targetData.getRank() == Rank.GUIDE || targetData.getRank() == Rank.MODERATEUR || targetData.getRank() == Rank.RESPONSABLE || targetData.getRank() == Rank.ADMIN || targetData.getRank() == Rank.FONDATEUR){
            player.sendMessage("§cErreur : Ce joueur est supérieur au grade du Sponsor... (VIP+ ou ElyVIP)");
            return true;
        }

        if(playerData.contains("sponsorFinish")){
            player.sendMessage("§cErreur : Vous avez déjà utilisé le Sponsor ce mois-ci !");
          //  player.sendMessage("§cTemps de récupération : " + TimeUtils.minutesToDayHoursMinutes((int) ((Math.abs(playerData.getSponsorTime()-System.currentTimeMillis())/1000)/60)));
            return true;
        }

        /*

         */
        playerData.setSponsorTime(31);
        targetData.setRankTime(Rank.DUC, 31);
        player.sendMessage("§6■ §7Vous avez utilisé votre sponsor ! §a" + target.getName() + " §7a bien reçu son grade §eVIP §7pendant §bun mois§7.");
        target.sendMessage("§6■ §7Félicitations ! " + playerData.getRank().getPrefix() + player.getName() + " §7vous a offert le grade §eVIP §7pendant §bun mois §7!");

        return false;
    }

}
