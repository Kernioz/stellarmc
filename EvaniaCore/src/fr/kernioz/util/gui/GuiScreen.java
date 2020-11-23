package fr.kernioz.util.gui;

import fr.kernioz.Evania;
import fr.kernioz.util.ItemStackUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public abstract class GuiScreen implements Listener {

	Inventory inv;
	Player p;
	boolean update;

	String name;
	int size;

	public GuiScreen(String name, int size, Player p, boolean update) {
		this.name = name;
		this.size = size;
		this.p = p;
		this.update = update;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void build() {
		inv = Evania.get().getServer()
				.createInventory(p, size * 9, name);
	}

	public Player getPlayer() {
		return p;
	}

	public boolean isUpdate() {
		return this.update;
	}

	abstract public void drawScreen();

	public void open() {
		p.openInventory(inv);
		Evania.get().getTasksManager().addTask(() -> {
			drawScreen();
			p.updateInventory();
		});
		Evania.get().getServer().getPluginManager()
				.registerEvents(this, Evania.get());
		onOpen();
	}

	public void close() {
		p.closeInventory();
	}

	public Inventory getInventory() {
		return inv;
	}

	public void setItem(ItemStack item, int slot) {
		inv.setItem(slot, item);
	}

	public void setItem(int slot, ItemStack itemStack){
		inv.setItem(slot, itemStack);

	}

	public void addItem(ItemStack item) {
		inv.addItem(item);
	}

	public void setItemLine(ItemStack item, int line, int slot) {
		setItem(item, (line * 9 - 9) + slot - 1);
	}

	public void clearInventory() {
		inv.clear();
	}

	public void setFont(ItemStack item) {
		for (int i = 0; i < inv.getSize(); i++) {
			setItem(item, i);
		}
	}

	@EventHandler
	public void onPlayerInventory(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (!e.getClickedInventory().equals(inv)) return;
		if (!ItemStackUtils.isValid(e.getCurrentItem()))
			return;
		e.setCancelled(true);
		if (!e.getCurrentItem().hasItemMeta())
			return;
		if (!e.getCurrentItem().getItemMeta().hasDisplayName())
			return;

		onClick(e.getCurrentItem(), e);
	}

	public abstract void onOpen();

	public abstract void onClick(ItemStack item, InventoryClickEvent event);

	public abstract void onClose();

	@EventHandler
	public void onPlayerInventory(InventoryCloseEvent e) {
		onClose();
		if (!GuiManager.isOpened(this.getClass()))
			HandlerList.unregisterAll(this);
	}

}

