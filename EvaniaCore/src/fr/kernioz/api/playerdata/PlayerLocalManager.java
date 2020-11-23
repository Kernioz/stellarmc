package fr.kernioz.api.playerdata;

import fr.kernioz.Evania;

import java.util.concurrent.ConcurrentHashMap;



public class PlayerLocalManager {

	protected ConcurrentHashMap<String, PlayerLocal> cachedData = new ConcurrentHashMap<>();
	
	public static PlayerLocalManager get() {
		return Evania.get().getPlayerLocalManager();
	}
	
	public PlayerLocal getPlayerLocal(String player) {
		player = player.toLowerCase();
		if (!cachedData.containsKey(player)) {
			PlayerLocal data = new PlayerLocal(player);
			cachedData.put(player, data);
			return data;
		}

		PlayerLocal data = cachedData.get(player);

		return data;
	}

	public void unload(String player) {
		player = player.toLowerCase();
		PlayerLocal pl = getPlayerLocal(player);
		pl.unload();
		cachedData.remove(player);
	}
}
