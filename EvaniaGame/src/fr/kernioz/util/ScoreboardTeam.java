package fr.kernioz.util;


import fr.kernioz.exceptions.TooManyCharacterException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardTeam {

    private Scoreboard scoreboard;
    private Team team;

    public ScoreboardTeam(String name, String prefix, String suffix) {
        this(name, prefix, suffix, Bukkit.getScoreboardManager().getMainScoreboard());

    }

    public ScoreboardTeam(String name, String prefix, String suffix, Scoreboard scoreboard) {

        this.scoreboard = scoreboard;

        this.team = scoreboard.getTeam(name);
        if (team == null) this.team = scoreboard.registerNewTeam(name);

        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(false);

        int prefixLength = 0;
        int suffixLength = 0;

        if (prefix != null) prefixLength = prefix.length();
        if (suffix != null) suffixLength = suffix.length();

        if (prefixLength + suffixLength >= 32) {

            try {
                throw new TooManyCharacterException("PREFIX & SUFFIX lengths are greater than 32");

            } catch (TooManyCharacterException e) {
                e.printStackTrace();

            }

        } else {

            if (prefix != null) team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
            if (suffix != null) team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));

        }

    }

    public void modifyNameTagVisibility(NameTagVisibility nameTagVisibility){
        team.setNameTagVisibility(nameTagVisibility);

    }

    public void addPlayer(Player player) {
        team.addEntry(player.getName());
        player.setScoreboard(scoreboard);

    }

    public void addPlayer(String name) {
        team.addEntry(name);

    }

    public void removePlayer(Player player) {
        team.removeEntry(player.getName());

    }

    public void removePlayer(String name) {
        team.removeEntry(name);

    }

    public void unregisterTeam() {
        team.unregister();
        System.out.println("[StellarGame] Remove Scoreboard");

}
}