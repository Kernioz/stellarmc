package fr.kernioz.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.kernioz.Evania;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class LagCommand implements CommandExecutor {
		
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			String string = simpleDateFormat.format(date);
			sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "-------------------------------------------");
			sender.sendMessage(ChatColor.DARK_GRAY + "  ■ " + ChatColor.GRAY + " Nom du serveur: §b" + Evania.get().getServer().getServerName() + " " + ChatColor.YELLOW + string);
			sender.sendMessage("");
			int ping = ((CraftPlayer) player).getHandle().ping;
			String prefix = "§c";
			if (ping <= 100) prefix = "§a";
			else if (ping > 100 && ping < 500) prefix = "§e";
			sender.sendMessage("  §7§oVotre latence indique le temps le réponse avec le serveur.");
			sender.sendMessage(ChatColor.DARK_GRAY + "  ■ " + ChatColor.GRAY + " Votre latence: " + prefix + ping + " ms");
			sender.sendMessage("");
			sender.sendMessage("  §7§oLes TPS indiquent la qualité du serveur. §7(§a20 §7= §bParfait§7)");
			sender.sendMessage(ChatColor.DARK_GRAY + "  ■ " + ChatColor.GRAY + " TPS des dernières 1m, 5m, 15m : §a" + getTPS());
			sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "-------------------------------------------");
		}
		
		
		// TODO Auto-generated method stub
		return false;
	}
	
	private String getTPS() {
		StringBuilder sb = new StringBuilder("");
		double[] tps = MinecraftServer.getServer().recentTps;
		for (int i = 0; i < 3; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(format(tps[i]));
		}
		return sb.toString();
	}
	
	
	private String format(double tps) {
		return (tps > 19.5D ? ChatColor.GREEN : tps > 19D ? ChatColor.YELLOW : ChatColor.RED).toString()
				+ (tps > 20.0D ? "*" : "") + Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D);
	} 

}
