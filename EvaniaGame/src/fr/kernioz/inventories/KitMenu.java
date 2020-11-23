package fr.kernioz.inventories;

import fr.kernioz.util.ItemBuilder;
import fr.kernioz.util.gui.GuiScreen;
import fr.kernioz.util.nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitMenu extends GuiScreen {


    public KitMenu(Player p) {
        super("Kit", 6, p, false);
        build();
    }

    @Override
    public void drawScreen() {

       // setItemLine(1, new ProfileItem(getPlayer()).getStaticItem(), 5, 5);
        this.placeGlassMenu(getPlayer());
    }

    public void setItemLine(int id, ItemStack item, int line, int slot) {
        super.setItemLine(new NBTItem(item).setInteger("itemID", id).getItem(), line, slot);
    }

    @Override
    public void onOpen() {
        // TODO Auto-generated method stub

    }



    public void placeGlassMenu(Player player){

        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 1, 1);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 1, 2);

        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 1, 9);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 1, 8);

        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 2, 1);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 2, 9);



        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 5, 1);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 5, 9);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 6, 1);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 6, 2);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 6, 8);
        setItemLine(-1, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setTitle("§6").build(), 6, 9);



        setItemLine(1, zebi(Material.WOOD_SWORD, "§6Kit Paysan", "essentials.kit.paysan"), 3, 3);
        setItemLine(2, zebi(Material.STONE_SWORD, "§6Kit Ecuyer", "essentials.kit.ecuyer"), 3, 4);
        setItemLine(3, zebi(Material.IRON_SWORD, "§6Kit Chevalier", "essentials.kit.chevalier"), 3, 5);
        setItemLine(4, zebi(Material.GOLD_SWORD, "§6Kit Mage", "essentials.kit.mage"), 3, 6);
        setItemLine(5, zebi(Material.DIAMOND_SWORD, "§6Kit Duc", "essentials.kit.duc"), 3, 7);
        setItemLine(6, zebi(Material.BOW, "§6Kit Prince", "essentials.kit.prince"), 4, 3);
        setItemLine(7, zebi(Material.DIAMOND_HELMET, "§6Kit Roi", "essentials.kit.roi"), 4, 4);
        setItemLine(8, zebi(Material.NETHER_STAR, "§6Kit Ange", "essentials.kit.ange"), 4, 5);
        setItemLine(9, zebi(Material.FIREBALL, "§6Kit Démon", "essentials.kit.demon"), 4, 6);
        setItemLine(10, zebi(Material.TNT, "§6Kit Pillage", "essentials.kit.pillage"), 4, 7);
        setItemLine(11, zebi(Material.BREWING_STAND_ITEM, "§6Kit Alchimiste", "essentials.kit.alchimiste"), 5, 4);
        setItemLine(12, zebi(Material.BOAT, "§6Kit Explorateur", "essentials.kit.explorateur"), 5, 5);
        setItemLine(13, zebi(Material.WOOD_HOE, "§6Kit Farmeur", "essentials.kit.farmeur"), 5, 6);


    }

    public static String[] getSoon() {
        List<String> lores = new ArrayList<String>();

        lores.add("§7Vous ne possédez pas ce kit...");
        lores.add("§7Veuillez l'acheter sur la boutique ou notre site internet");

        return lores.toArray(new String[lores.size()]);
    }

    public static String[] getGood() {
        List<String> lores = new ArrayList<String>();

        lores.add("§7Vous possédez ce kit");
        lores.add("§7");
        lores.add("§a» Clic droit pour l'utiliser");

        return lores.toArray(new String[lores.size()]);
    }


    public ItemStack zebi(Material material, String name, String permissions){

        ItemStack itemStack;

        if(!getPlayer().hasPermission(permissions)){
            itemStack = new ItemStack(Material.BARRIER);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemMeta.setLore(Arrays.asList(getSoon()));
            itemStack.setItemMeta(itemMeta);
        } else {
            itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemStack.setItemMeta(itemMeta);
        }



        return itemStack;
    }


    @Override
    public void onClick(ItemStack item, InventoryClickEvent event) {

        event.setCancelled(true);

        NBTItem nbt = new NBTItem(item);
        if (!nbt.hasKey("itemID")) {
            return;
        }

        int itemID = nbt.getInteger("itemID");

        ClickType action = event.getClick();

        if(itemID == 1){
            getPlayer().chat("/kit paysan");
            return;
        }
        if(itemID == 2){
            getPlayer().chat("/kit ecuyer");
            return;
        }
        if(itemID == 3){
            getPlayer().chat("/kit chevalier");
            return;
        }
        if(itemID == 4){
            getPlayer().chat("/kit mage");
            return;
        }
        if(itemID == 5){
            getPlayer().chat("/kit duc");
            return;
        }
        if(itemID == 6){
            getPlayer().chat("/kit prince");
            return;
        }
        if(itemID == 7){
            getPlayer().chat("/kit roi");
            return;
        }
        if(itemID == 8){
            getPlayer().chat("/kit ange");
            return;
        }
        if(itemID == 9){
            getPlayer().chat("/kit demon");
            return;
        }
        if(itemID == 10){
            getPlayer().chat("/kit pillage");
            return;
        }
        if(itemID == 11){
            getPlayer().chat("/kit alchimiste");
            return;
        }
        if(itemID == 12){
            getPlayer().chat("/kit explorateur");
            return;
        }
        if(itemID == 13){
            getPlayer().chat("/kit farmeur");
            return;
        }


    }

    @Override
    public void onClose() {
        // TODO Auto-generated method stub

    }
}