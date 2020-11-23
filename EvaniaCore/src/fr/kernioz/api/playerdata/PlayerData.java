package fr.kernioz.api.playerdata;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import fr.kernioz.Evania;
import fr.kernioz.api.playerdata.rank.Rank;
import net.krakeens.redis.jedis.Jedis;
import org.bukkit.entity.Player;



public class PlayerData extends PlayerDataAbstract {

	private final PlayerDataManager manager;
	private final Evania plugin;
	Player player;
	private Date lastRefresh = null;

	public PlayerData(String player, PlayerDataManager manager, Evania evania) {
		super(player);
		this.manager = manager;
		this.plugin = evania;
		updateData();
	}

	protected void updateData() {
		Jedis jedis = plugin.getConnector().getResource();
		Map<String, String> data = jedis.hgetAll("player:" + playerID);
		jedis.close();
		this.playerData = data;
		this.lastRefresh = new Date();
	}

	protected void refreshIfNeeded() {
		if (lastRefresh == null || (lastRefresh.getTime() + (1000 * 60 * 5)) < System.currentTimeMillis()) {
			Evania.get().getTasksManager().addTask(this::updateData);
		}
	}

	@Override
	public String get(String key) {
		refreshIfNeeded();
		return super.get(key);
	}

	@Override
	public Set<String> getKeys() {
		refreshIfNeeded();
		return super.getKeys();
	}

	@Override
	public Map<String, String> getValues() {
		refreshIfNeeded();
		return super.getValues();
	}

	@Override
	public boolean contains(String key) {
		refreshIfNeeded();
		return super.contains(key);
	}

	@Override
	public void set(String key, String value) {
		this.playerData.put(key, value);

		plugin.getTasksManager().addTask(() -> {
			Jedis jedis = plugin.getConnector().getResource();
			jedis.hset("player:" + playerID, key, value);
			jedis.close();
		});
	}

	@Override
	public void remove(String key) {
		playerData.remove(key);
		plugin.getTasksManager().addTask(() -> {
			Jedis jedis = plugin.getConnector().getResource();
			jedis.hdel("player:" + playerID, key);
			jedis.close();
		});
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

	public double getStellarCoins() {
		if (!contains("stellarcoins"))
			setDouble("stellarcoins", 0);
		return getDouble("stellarcoins");
	}

	public Rank getRank() {
		if (!contains("rank"))
			set("rank", Integer.toString(0));
		return Rank.get(getInt("rank"));
	}

	public double getBooster() {
		if (!contains("booster"))
			return 0;
		return getDouble("booster");
	}

	/*
	   Set a specific
	 */

	public void setRank(int rank) { set("rank", Integer.toString(rank)); }
	public void setRank(Rank rank) { set("rank", Integer.toString(rank.getID()));if (contains("rankFinish")) { remove("rankFinish"); } }


	/*
	   Différent Monnaies
	 */


	public double increaseCredits(double credits){
		Jedis jedis = plugin.getConnector().getResource();
		double newValue = Double.parseDouble(jedis.hget("player:" + playerID, "stellarcoins"))+credits;
		jedis.hset("player:" + playerID, "stellarcoins", String.valueOf(newValue));
		jedis.close();

		playerData.put("stellarcoins", String.valueOf(newValue));
		return newValue;
	}

	public double decreaseCredits(double decrBy) {
		return increaseCredits(-decrBy);
	}

	public void creditCoins(final double famount) {
		new Thread(() -> {
			double amount = famount;
			double result = increaseCredits(amount);

		}, "CreditCoinsThread").start();
	}

	public void removeCoins(final double famount) {
		new Thread(() -> {
			double result = decreaseCredits(famount);
		}, "WithdrawCoinsThread").start();
	}




	/*
	  Concernant le time
	 */

	public void setSponsorTime(long days) {
		if (contains("sponsorFinish"))
			if (getLong("sponsorFinish") == 0)
				set("sponsorFinish", Long.toString(System.currentTimeMillis() + days * 86400000));
			else
				set("sponsorFinish", Long.toString(getLong("sponsorFinish") + days * 86400000));
		else
			set("sponsorFinish", Long.toString(System.currentTimeMillis() + days * 86400000));
	}

	public long getSponsorTime() {
		if (contains("sponsorFinish"))
			return getLong("sponsorFinish");
		return 0;
	}

	public void setRankTime(Rank rank, long days) {
		set("rank", Integer.toString(rank.getID()));
		if (contains("rankFinish"))
			if (getLong("rankFinish") == 0)
				set("rankFinish", Long.toString(System.currentTimeMillis() + days * 86400000));
			else
				set("rankFinish", Long.toString(getLong("rankFinish") + days * 86400000));
		else
			set("rankFinish", Long.toString(System.currentTimeMillis() + days * 86400000));
	}

	public long getRankFinish() {
		if (contains("rankFinish"))
			return getLong("rankFinish");
		return 0;
	}

	public long getBoosterFinish() {
		if (contains("boosterFinish"))
			return getLong("boosterFinish");
		return 0;
	}

	public void setBoosterTime(long days) {
		setDouble("booster", 0.25);

		if (contains("boosterFinish"))
			if (getLong("boosterFinish") == 0)
				setLong("boosterFinish", System.currentTimeMillis() + days * 86400000);
			else
				setLong("boosterFinish", getLong("boosterFinish") + days * 86400000);
		else
			setLong("boosterFinish", System.currentTimeMillis() + days * 86400000);
	}
}
