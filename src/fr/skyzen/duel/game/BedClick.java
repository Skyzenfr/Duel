package fr.skyzen.duel.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class BedClick implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        final Player j = e.getPlayer();

        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) || e.getItem() == null)
            return;
        if (e.getItem() != null && e.getItem().getType() != null && e.getItem().getType() == Material.BED) {
            j.kickPlayer("§6PixelsPalace §f➽ §eRetour au hub...");
        }
    }

}
