package fr.kernioz.util.gui;

import fr.kernioz.Evania;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiTask extends BukkitRunnable {
 
    @SuppressWarnings("unused")
	private final Evania plugin;
    private final Player p;
    private final GuiScreen gui;
 
    public GuiTask(Evania plugin, Player p, GuiScreen gui) {
        this.plugin = plugin;
        this.p = p;
        this.gui = gui;
        gui.open();
    }
 
    @Override
    public void run() {
    	
    	if (!gui.getInventory().getViewers().contains(p)) {
    		this.cancel();
    		return;
    	}

        Evania.get().getServer().getPluginManager().callEvent(new GuiUpdateEvent(p,gui,false));
    	gui.drawScreen();

    }
 
}
