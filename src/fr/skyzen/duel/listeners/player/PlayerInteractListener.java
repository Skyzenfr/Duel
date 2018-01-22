package fr.skyzen.duel.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerDoorOpen(PlayerInteractEvent e) {

        /**
         * Vérification si c'est un clique droit ou gauche.
         **/
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
            /**
             * Vérification si c'est un clique sur une trape.
             **/
            if ((e.getClickedBlock().getType() == Material.IRON_DOOR) || (e.getClickedBlock().getType() == Material.WOODEN_DOOR) || (e.getClickedBlock().getType() == Material.TRAP_DOOR)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BedClick(PlayerInteractEvent e) {
        final Player j = e.getPlayer();

        /**
         * Vérification si c'est un clique droit ou gauche.
         **/
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) || e.getItem() == null)
            return;
        /**
         * Vérification si c'est un clique sur un lit.
         **/
        if (e.getItem() != null && e.getItem().getType() != null && e.getItem().getType() == Material.BED) {
            j.kickPlayer("§6PixelsPalace §f➽ §eRetour au hub...");
        }
    }

}
