package fr.kernioz.listeners;

import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.api.playerdata.rank.Rank;
import fr.kernioz.managers.PermissionsManager;
import fr.kernioz.util.TitleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    private Evania elarya;

    public PlayerConnectionListener(Evania elarya){
        this.elarya = elarya;
    }

    @EventHandler
    public void onJoinPlayer(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = this.elarya.getPlayerDataManager().get(player.getUniqueId().toString());



        if(this.elarya.getServer().getServerName().contains("Auth")){
            TitleUtils.sendTabTitle(player, "§6StellarMC\n§r\n§8■ §7Vous êtes sur le serveur §6" + this.elarya.getServer().getServerName() + " §8■", "§8■ §7Achetez des crédits avec §fPayPal§8/§fAppel & SMS §7sur §b§nwww.stellarmc.fr");
            if(playerData.contains("registered")){
                player.sendMessage("§7Ah, §e" + player.getName() + " §7enfin de retour !");
                player.sendMessage("§8■ §7Utiliser: §6/login <mot de passe>");
            } else {
                player.sendMessage("§7Bienvenue sur §b§lStellarMC");
                player.sendMessage("§8■ §7Utiliser: §6/register <mot de passe> <mot de passe>");
            }
        }

        event.setJoinMessage(null);

        if(this.elarya.getServer().getServerName().contains("Faction")){
            PermissionsManager permissionsManager = new PermissionsManager(player);
            for(String permissionsGroup : this.elarya.getConfigurationServer().getPermissions(playerData.getRank())){
                permissionsManager.addPermission(permissionsGroup);
           //     System.out.println("permissions : " + permissionsGroup);
            }
            System.out.println("[StellarCore] Permissions ajouté au joueur " + player.getName());
        }
        System.out.println("[StellarCore] L'utilisateur " + player.getName() + " a été ajouté.");
    }

    @EventHandler
    public void onQuitPlayer(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = this.elarya.getPlayerDataManager().get(player.getUniqueId().toString());

        if(playerData.contains("logged")){
            playerData.remove("logged");
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(this.elarya.getServer().getServerName().contains("Auth")){
            Location from2 = event.getFrom();
            Location to2 = event.getTo();
            if (from2.getBlockX() != to2.getBlockX() || from2.getBlockZ() != to2.getBlockZ() || from2.getBlockY() != to2.getBlockY()) {
                event.getPlayer().teleport(from2);
            }
        }
    }

}
