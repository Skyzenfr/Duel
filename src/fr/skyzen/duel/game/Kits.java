package fr.skyzen.duel.game;

import fr.skyzen.duel.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public enum Kits {

    DEFAUT(13, "§bDéfaut", new ItemStack(Material.WOOD_SWORD, 1)),
    TANK(21, "§bTank", new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1)),
    DEFENSEUR(22, "§bDéfenseur", new ItemStack(Material.WOOL, 1));

    public ArrayList<ItemStack> items;
    public int slot;
    public String kitName;
    public ItemStack icon;

    Kits(int slot, String kitName, ItemStack icon) {
        this.slot = slot;
        this.kitName = kitName;
        this.icon = icon;
    }

    public int getSlot() {
        return slot;
    }

    public String getKitName() {
        return kitName;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public ItemStack getItem() {

        icon.getItemMeta().setDisplayName(kitName);
        icon.setItemMeta(icon.getItemMeta());

        return icon;
    }

    public void add(Player player) {
        if (Main.getInstance().kits.containsKey(player)) {
            Main.getInstance().kits.remove(player);
        }
        Main.getInstance().kits.put(player, this);

    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

}
