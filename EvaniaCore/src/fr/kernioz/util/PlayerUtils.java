package fr.kernioz.util;


import fr.kernioz.Evania;
import fr.kernioz.util.metadatas.Flags;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class PlayerUtils {

	public static void sendActionMessage(Player p, String msg) {
		IChatBaseComponent message = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(message, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	/**
	 * Active ou non la collision entre le joueur est les autres entitées
	 * 
	 * @param player
	 * @param value
	 */
	public static void setEntityCollision(Player player, Boolean value) {
		((CraftPlayer) player).getHandle().collidesWithEntities = value;
	}

	/**
	 * Inflige des dégâts à l'entité (sans tenir compte de l'armure) sans
	 * alarmer l'AntiCheat et en taggant cette dernière comme lastDamager
	 */
	public static void damage(Player damager, LivingEntity livingEntity, double amount) {
		if (Flags.hasFlag(livingEntity, Flags.godMode))
			return;
		if (livingEntity.isDead())
			return;
		EntityDamageEvent event = new EntityDamageEvent(livingEntity, null, amount);
//		 Need Java 8
//		 Map<DamageModifier, Double> modifiers = new EnumMap<DamageModifier,
//		 Double>(ImmutableMap.of(DamageModifier.BASE,
//		 Double.valueOf(amount)));
//		 Map<DamageModifier, ? extends Function<? super Double, Double>>
//		 modifierFunctions = new
//		 EnumMap<DamageModifier,? extends Function<? super Double,
//		 Double>(ImmutableMap.of(DamageModifier.BASE,
//		 0.0D));
//		 event = new EntityDamageEvent(livingEntity, null, modifiers,
//		 modifierFunctions);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			Flags.setStringFlag(livingEntity, Flags.lastDamager, damager.getName());
			//EntityUtils.damage(livingEntity, amount);
		}
	}

	/**
	 * Même chose que damage mais avec un effet de knockBack
	 * 
	 * @param damager
	 * @param livingEntity
	 * @param amount
	 * @param knockBackPower
	 * @deprecated pas terminé
	 */
	public static void damage(Player damager, LivingEntity livingEntity, double amount, double knockBackPower) {
		damage(damager, livingEntity, amount);
		livingEntity.setVelocity(new Vector(0, 0, 0));
	}

	/**
	 * Recupère le nom de la dernière entité a voir attaqué cette entité,
	 * utilisé actuellement pour attribué les kills dans certains jeux
	 * 
	 * @param entity
	 * @return
	 */
	public static String getLastDamagerName(Entity entity) {
		return Flags.readStringFlag(entity, Flags.lastDamager);
	}

	/**
	 * Force le nom de l'entité qui aura attaqué en dernier cette entité
	 * 
	 * @param entity
	 * @return
	 */
	public static void setLastDamagerName(Entity entity, String lastDamagerName) {
		Flags.setStringFlag(entity, Flags.lastDamager, lastDamagerName);
	}

	/**
	 * Change le tueur du joueur par la dernière personne qui l'a touché en
	 * VANILLA, dépend du flag lastDamager qui doit être posé sur les event
	 * de dégâts ou sorts, ignore le teamkill
	 * 
	 * @param player
	 */
	public static void changeKillerToLastDammager(Player player) {
		String lastDamagerName = Flags.readStringFlag(player, Flags.lastDamager);
		if (lastDamagerName != null) {
			Player killer = Bukkit.getPlayer(lastDamagerName);
			if (killer != null && !killer.getName().equals(player.getName())) {
				CraftPlayer craftPlayer = (CraftPlayer) player;
				craftPlayer.getHandle().killer = ((CraftPlayer) killer).getHandle();
			}
			Flags.removeFlag(player, Flags.lastDamager);
		}
	}

	/**
	 * Met un joueur à 0 comme s'il venait de se connecter Remet aussi la vie
	 * maximale à 20, levels et xp
	 * 
	 * @param player
	 */
	public static void razPlayer(Player player) {
		player.setMaxHealth(20);
		player.setExp(0);
		player.setLevel(0);
		player.setFallDistance(0);
		player.setFireTicks(0);
		clearFullInventory(player);
		resetPlayer(player);
	}

	/**
	 * Remet le joueur full life/food sans effets de potions
	 * 
	 * @param player
	 */
	public static void resetPlayer(Player player) {
		removePotionEffects(player);
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setSaturation(20);
	}

	/**
	 * Vide l'inventaire du joueur + armure
	 * 
	 * @param player
	 */
	public static void clearFullInventory(Player player) {
		player.closeInventory();
		player.getInventory().clear();
		player.setItemInHand(new ItemStack(Material.AIR));
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
	}

	/**
	 * Vide l'inventaire du joueur
	 * 
	 * @param player
	 */
	public static void clearInventory(Player player) {
		player.closeInventory();
		player.getInventory().clear();
		player.setItemInHand(new ItemStack(Material.AIR));
	}

	/**
	 * Retire tous les effets de potion du joueur
	 * 
	 * @param player
	 */
	public static void removePotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Répare toutes les armures du joueur
	 * 
	 * @param player
	 */
	public static void resetArmorDurability(Player player) {
		for (ItemStack itemStack : player.getInventory().getArmorContents()) {
			if (itemStack != null && itemStack.getType() != Material.AIR)
				itemStack.setDurability((short) -1);
		}
	}

	/**
	 * Remet à fond la dura de l'objet en main
	 * 
	 * @param player
	 */
	public static void resetItemInHandDurability(Player player) {
		ItemStack itemStack = player.getItemInHand();
		if (ItemStackUtils.isValid(itemStack)) {
			itemStack.setDurability((short) 1); // 1 mieux que 0 pour éviter le
												// spam barre pleine
			player.updateInventory(); // Si pas update le client considère
										// l'item endommagée
		}
	}

	/**
	 * Consummer l'objet en main, réduit de 1 le nombre de stack et détruit si
	 * 0
	 * 
	 * @param player
	 */
	public static void consumItemInHand(Player player) {
		PlayerInventory inv = player.getInventory();
		int amount = inv.getItemInHand().getAmount();
		if (amount == 1) {
			inv.setItemInHand(new ItemStack(Material.AIR));
			return;
		}
		amount--;
		inv.getItemInHand().setAmount(amount);
	}

	/**
	 * Force le respawn du joueur au bout de x ticks
	 * 
	 * @param player
	 */
	public static void sendForceRespawn(Player player, int ticks) {
		final String playerName = player.getName();
		Bukkit.getScheduler().runTaskLater(Evania.get(), new Runnable() {
			@Override
			public void run() {
				Player player = Bukkit.getPlayer(playerName);
				if (player != null && player.isDead() && player.isOnline()) {
					PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
					EntityPlayer ep = ((CraftPlayer) player).getHandle();
					if (ep.playerConnection != null && !ep.playerConnection.isDisconnected())
						ep.playerConnection.a(packet);
				}
			}
		}, ticks);
	}

}
