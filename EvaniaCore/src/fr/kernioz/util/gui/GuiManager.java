package fr.kernioz.util.gui;


import fr.kernioz.Evania;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class GuiManager {
	
	@SuppressWarnings("rawtypes")
	static HashMap<String,Class> openGuis = new HashMap<String, Class>();

	public static GuiScreen openGui(GuiScreen gui) {
	
		openPlayer(gui.getPlayer(), gui.getClass());
		if (gui.isUpdate())
		new GuiTask(Evania.get(),gui.getPlayer(),gui).runTaskTimer(Evania.get(), 0,20);
		else {
			gui.open();
		}
		return gui;
	}
	
	@SuppressWarnings("rawtypes")
	public static void openPlayer(Player p,Class gui) {
		if (openGuis.containsKey(p.getName())) {
			openGuis.remove(p.getName());
			openGuis.put(p.getName(), gui);
		} else {
			openGuis.put(p.getName(), gui);
		}
	}
	
	public static void closePlayer(Player p) {
		if (openGuis.containsKey(p.getName())) {
			openGuis.remove(p.getName());
		}
	}
	
	public static boolean isPlayer(Player p) {
		if (openGuis.containsKey(p.getName())) return true;
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isOpened(Class clas) {
		for (Class cla : openGuis.values()) {
			if (cla.equals(clas)) return true;
		}
		return false;
	}
}
