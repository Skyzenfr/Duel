package fr.skyzen.duel.manager;

import fr.skyzen.duel.Duel;
import fr.skyzen.duel.utils.ItemModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClassesMenu implements Listener {

    private Inventory inv;

    public ClassesMenu() {
        this.inv = Bukkit.createInventory(null, 36, "§7Sélecteur de Classes");

        //ON DEFINI LA PREMIERE LIGNE
        this.inv.setItem(11, ItemModifier.setText(ItemModifier.giveSkull("MHF_Question"), "§cProchainement.."));
        this.inv.setItem(12, ItemModifier.setText(ItemModifier.giveSkull("MHF_Question"), "§cProchainement.."));
        this.inv.setItem(13, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "§b§nTank", "", "§7Contenu du Kit:", "§e• Casque en fer", "§e• Plastron en maille", "§e• Pantalon en maille", "§e• Bottes en cuir", "§e• Epée en pierre", "", "§7Avantages:", "§dVous possèdez un bouclier."));
        this.inv.setItem(14, ItemModifier.setText(ItemModifier.giveSkull("MHF_Question"), "§cProchainement.."));
        this.inv.setItem(15, ItemModifier.setText(ItemModifier.giveSkull("MHF_Question"), "§cProchainement.."));

        //ON DEFINI LA DEUXIEME LIGNE
        this.inv.setItem(21, ItemModifier.setText(ItemModifier.giveSkull("MHF_Question"), "§cProchainement.."));
        this.inv.setItem(22, ItemModifier.setText(new ItemStack(Material.WOOL, 1), "§b§nDéfenseur", "", "§7Contenu du Kit:", "§e• Casque en cuir", "§e• Plastron en maille", "§e• Pantalon en cuir", "§e• Bottes en maille", "§e• Epée en fer", "", "§7Avantages:", "§dVous possèdez une pomme en or."));
        this.inv.setItem(23, ItemModifier.setText(ItemModifier.giveSkull("MHF_Question"), "§cProchainement.."));

        //ON FERME L'INVENTAIRE
        this.inv.setItem(35, ItemModifier.setText(new ItemStack(Material.ARROW, 1), "§fFermer l'inventaire", ""));
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        final Player j = e.getPlayer();

        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) || e.getItem() == null)
            return;
        if (e.getItem() != null && e.getItem().getType() != null && e.getItem().getType() == Material.NAME_TAG) {
            j.openInventory(inv);
        }
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent e) {
        final Player j = (Player) e.getWhoClicked();

        if (e.getInventory().getName().equalsIgnoreCase("§7Sélecteur de Classes")) {

            if (e.getCurrentItem() == null) return;
            e.setCancelled(true);
            j.closeInventory();

            if (e.getCurrentItem().getType() == Classes.DEFENSEUR.getIcon().getType()) {
                if (Duel.getInstance().kits.get(j) == Classes.DEFENSEUR) {
                    j.playSound(j.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    j.sendMessage("§8[§eClasses§8] §cVous avez déjà sélectionné cette classe.");
                } else {
                    j.playSound(j.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                    j.sendMessage("§8[§eClasses§8] §7Vous avez bien sélectionné la classe: §bDéfenseur");
                    Classes.DEFENSEUR.add(j);
                }

            } else if (e.getCurrentItem().getType() == Classes.TANK.getIcon().getType()) {
                if (Duel.getInstance().kits.get(j) == Classes.TANK) {
                    j.playSound(j.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    j.sendMessage("§8[§eClasses§8] §cVous avez déjà sélectionné cette classe.");
                } else {
                    j.playSound(j.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                    j.sendMessage("§8[§eClasses§8] §7Vous avez bien sélectionné la classe: §bTank");
                    Classes.TANK.add(j);
                }
            }

        } else {
            e.setCancelled(true);
        }

    }

}