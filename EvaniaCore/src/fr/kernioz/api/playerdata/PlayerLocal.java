package fr.kernioz.api.playerdata;

import java.util.Map;
import java.util.Set;

import fr.kernioz.Evania;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

public class PlayerLocal extends PlayerDataAbstract {

	public static PlayerLocal getPlayer(String name) {
		return Evania.get().getPlayerLocalManager().getPlayerLocal(name);
	}

	private Team team;


	public PlayerLocal(String player) {
		super(player);
	}

	public void inits() {
		if (this.team != null)
			this.team.addPlayer(Bukkit.getOfflinePlayer(getPlayerID()));
	}

	public Team getTeam() {
		return team;
	}

	public void unload() {
		if (this.team != null)
			this.team.unregister();
	}

	@Override
	public String get(String key) {
		return super.get(key);
	}

	@Override
	public Set<String> getKeys() {
		return super.getKeys();
	}

	@Override
	public Map<String, String> getValues() {
		return super.getValues();
	}

	@Override
	public boolean contains(String key) {
		return super.contains(key);
	}

	@Override
	public void set(String key, String value) {
		this.playerData.put(key, value);
	}

	@Override
	public void remove(String key) {
		playerData.remove(key);
	}

	@Override
	public void setInt(String key, int value) {
		set(key, String.valueOf(value));
	}

	@Override
	public void setBoolean(String key, boolean value) {
		set(key, String.valueOf(value));
	}

	@Override
	public void setDouble(String key, double value) {
		set(key, String.valueOf(value));
	}

	@Override
	public void setLong(String key, long value) {
		set(key, String.valueOf(value));
	}

	public void setPrefix(String prefix) {
		if (this.team != null)
		this.team.setPrefix(prefix);
	}

	public void setSuffix(String suffix) {
		if (this.team != null)
		this.team.setSuffix(suffix);
	}
}
