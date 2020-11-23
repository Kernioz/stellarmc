/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.EnchantmentStorageMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package fr.kernioz.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private int amount;
    private short damage;
    private final Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
    private final List<String> lores = new ArrayList<String>();
    private Material material;
    private String title;

    public ItemBuilder(ItemStack item) {
        this(item.getType(), item.getAmount(), item.getDurability());
    }

    public ItemBuilder(Material material) {
        this(material, 1, (short)0);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (short)0);
    }

    public ItemBuilder(Material material, int amount, short damage) {
        this.material = material;
        this.amount = amount;
        this.damage = damage;
    }

    public ItemBuilder(Material material, short durability) {
        this(material, 1, durability);
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder addLores(List<String> lores) {
        this.lores.addAll(lores);
        return this;
    }

    public /* varargs */ ItemBuilder addLores(String ... lores) {
        this.lores.addAll(Arrays.asList(lores));
        return this;
    }

    public ItemStack build() {
        if (this.material == null) {
            throw new NullPointerException("Material cannot be null!");
        }
        ItemStack item = new ItemStack(this.material, this.amount, this.damage);
        if (this.material != Material.ENCHANTED_BOOK && !this.enchantments.isEmpty()) {
            item.addUnsafeEnchantments(this.enchantments);
        }
        ItemMeta meta = item.getItemMeta();
        if (this.material == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta mmeta = (EnchantmentStorageMeta)item.getItemMeta();
            for (Map.Entry<Enchantment, Integer> ench : this.enchantments.entrySet()) {
                mmeta.addStoredEnchant(ench.getKey(), ench.getValue().intValue(), true);
            }
            item.setItemMeta((ItemMeta)mmeta);
            meta = item.getItemMeta();
        }
        if (this.title != null) {
            meta.setDisplayName(this.title);
        }
        if (!this.lores.isEmpty()) {
            meta.setLore(this.lores);
        }
        item.setItemMeta(meta);
        return item;
    }

    public void clear() {
        this.lores.clear();
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDamage(short damage) {
        this.damage = damage;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
}

