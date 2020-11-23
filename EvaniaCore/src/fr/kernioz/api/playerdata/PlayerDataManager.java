package fr.kernioz.api.playerdata;

import java.util.concurrent.ConcurrentHashMap;

import fr.kernioz.Evania;


public class PlayerDataManager {

	protected ConcurrentHashMap<String, PlayerData> cachedData = new ConcurrentHashMap<> ();
	private final Evania evania;

	public PlayerDataManager(Evania evania) {
		this.evania = evania;

	}

	public PlayerData get(String player) {
		return get(player, false);
	}
	
	public PlayerData get(String player, boolean forceRefresh) {
		player = player.toLowerCase();
		if (!cachedData.containsKey(player)) {
			PlayerData data = new PlayerData(player, this, evania);
			cachedData.put(player, data);
			return data;
		}

		PlayerData data = cachedData.get(player);
		data.updateData();
		if (forceRefresh) {
			data.updateData();
			return data;
		}

		data.refreshIfNeeded();
		return data;
	}

	public void update(String player) {
		player = player.toLowerCase();
		if (!cachedData.containsKey(player)) {
			PlayerData data = new PlayerData(player, this, evania);
			cachedData.put(player, data);
			return;
		}

		PlayerData data = cachedData.get(player);
		data.updateData();
	}

	public void unload(String player) {
		player = player.toLowerCase();
		cachedData.remove(player);
	}
}
