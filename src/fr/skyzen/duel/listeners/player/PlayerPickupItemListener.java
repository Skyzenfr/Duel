package fr.skyzen.duel.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPickupItemListener implements Listener {

    @Deprecated
    @EventHandler
    public void cancelPickup(PlayerPickupItemEvent e) {

        /**
         *  Détection d'une flèche.
         */
        ItemStack itemPickup = e.getItem().getItemStack();
        if (itemPickup == null)
            return;
        if (itemPickup.getType().equals(Material.AIR))
            return;
        if (itemPickup.getType().equals(Material.ARROW)) {
            e.setCancelled(false);
            return;
        } else {
            e.setCancelled(true);
        }

    }

}
