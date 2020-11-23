package fr.kernioz.listeners;

import com.massivecraft.factions.FPlayers;
import fr.kernioz.Evania;
import fr.kernioz.StellarGame;
import fr.kernioz.api.playerdata.PlayerData;
import fr.kernioz.api.playerdata.rank.Rank;
import fr.kernioz.scheduler.WelcomeTask;
import fr.kernioz.scoreboard.Scoreboard;
import fr.kernioz.util.ScoreboardTeam;
import fr.kernioz.util.TitleUtils;
import fr.kernioz.util.tasksmanager.TaskManager;
import fr.watch54.display.holograms.HologramClient;
import fr.watch54.display.holograms.HologramServer;
import fr.watch54.display.interfaces.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerConnectionGame implements Listener {

    StellarGame stellarGame;
    private ScoreboardTeam scoreboardTeam;

    public PlayerConnectionGame(StellarGame stellarGame){
        this.stellarGame = stellarGame;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for(int i = 1; i < 30; i++){
            player.sendMessage("");
            i++;
        }


        event.setJoinMessage(this.stellarGame.getConfigurationServer().getMessage("player-join").replace("&", "§").replace("%player%", event.getPlayer().getName()));

        this.injectScoreboard(player);
        this.injectTablist(player);

        this.t(player);

        if(!player.hasPlayedBefore()){
            player.teleport(this.stellarGame.getConfigurationServer().spawnLocation());
            Bukkit.broadcastMessage(this.stellarGame.getConfigurationServer().getMessage("welcome").replace("&", "§").replace("%player%", player.getName()));
            WelcomeTask task = new WelcomeTask(stellarGame, player);
            task.runTaskTimer(stellarGame, 0L, 20L);
        }

    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(this.stellarGame.getConfigurationServer().getMessage("player-quit").replace("%player%", event.getPlayer().getName()));

        TaskManager.cancelTaskByName("ScoreboardPlayer_"+event.getPlayer().getName());
        Evania.get().getPlayerDataManager().unload(player.getUniqueId().toString());

        scoreboardTeam.removePlayer(player);
        scoreboardTeam.unregisterTeam();

    }

    private void injectScoreboard(Player player){
        Scoreboard hubScoreboard = new Scoreboard(stellarGame);
        hubScoreboard.loadScoreboard(player);
    }

    private void injectTablist(Player player){
        PlayerData playerData = Evania.get().getPlayerDataManager().get(player.getUniqueId().toString());
        if (FPlayers.getInstance().getById(player.getUniqueId().toString()).hasFaction()) {
            TitleUtils.sendTabTitle(player, "§6StellarMC\n§r\n§8■ §7Vous êtes sur le serveur §6" + this.stellarGame.getServer().getServerName() + " §8■\n§r\n§7Faction: §a" + FPlayers.getInstance().getById(player.getUniqueId().toString()).getTag() + " §8❘ §7StellarCoins: §b" + playerData.getStellarCoins() + "\n", "\n§8■ §7Achetez des crédits avec §fPayPal§8/§fAppel & SMS §7sur §b§nwww.stellarmc.fr§r\n§7Voter en jeu grâce à §f§l/vote");
        } else{
            TitleUtils.sendTabTitle(player, "§6StellarMC\n§r\n§8■ §7Vous êtes sur le serveur §6" + this.stellarGame.getServer().getServerName() + " §8■\n§r\n§7Faction: §aAucune §8❘ §7StellarCoins: §b" + playerData.getStellarCoins() + "\n", "\n§r\n§8■ §7Achetez des crédits avec §fPayPal§8/§fAppel & SMS §7sur §b§nwww.stellarmc.fr§r\n§7Voter en jeu grâce à §f§l/vote");
        }

        Rank rank = playerData.getRank();

        if (rank == Rank.FONDATEUR) {
          //  PrefixHub.setNameTag(player, "a"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("a"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.ADMIN){
            //PrefixHub.setNameTag(player, "b"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("b"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.RESPONSABLE){
           // PrefixHub.setNameTag(player, "c"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("c"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.MODERATEUR){
           // PrefixHub.setNameTag(player, "d"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("d"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.GUIDE){
           // PrefixHub.setNameTag(player, "e"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("e"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.DEMON){
           // PrefixHub.setNameTag(player, "f"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("f"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.ANGE){
           // PrefixHub.setNameTag(player, "g"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("g"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.ROI){
            //PrefixHub.setNameTag(player, "h"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("h"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.PRINCE){
           // PrefixHub.setNameTag(player, "i"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("i"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.DUC){
          //  PrefixHub.setNameTag(player, "j"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("j"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.MAGE){
          //  PrefixHub.setNameTag(player, "k"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("k"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.CHEVALIER){
           // PrefixHub.setNameTag(player, "l"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("l"+player.getName(), rank.getTab(), null);
        } else if (rank == Rank.ECUYER){
         //   PrefixHub.setNameTag(player, "m"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("m"+player.getName(), rank.getTab(), null);
        } else {
           // PrefixHub.setNameTag(player, "z"+player.getName(), rank.getTab(), "");
            scoreboardTeam = new ScoreboardTeam("z"+player.getName(), rank.getTab(), null);
        }
        scoreboardTeam.addPlayer(player);
    }

    public void t(Player player) {

        PlayerData pad = Evania.get().getPlayerDataManager().get(player.getUniqueId().toString());

        List<Text> pInfo = new ArrayList<>();
        pInfo.add(() -> "§6§lVotre profil");
        pInfo.add(() -> "§e================================");
        pInfo.add(() -> "§7Grade: §f" + pad.getRank().getPrefix());
        pInfo.add(() -> "§7Crédits: §b" + pad.getStellarCoins() + " EUR");
        pInfo.add(() -> "§7Eliminations/Morts: §a0§8/§c0");
        pInfo.add(() -> "§7Niveau : §e0");
        pInfo.add(() -> "§7Progression : §8[§7No data display§8] §6N/A%");
        Location location = new Location(Bukkit.getWorld("world"), 31.31, 82, -67.954);

        HologramClient hologramClient = this.stellarGame.getHologramManager().createClient(player, pInfo, location, true);

    }



}
