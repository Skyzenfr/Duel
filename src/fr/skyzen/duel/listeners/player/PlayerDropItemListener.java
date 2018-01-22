package fr.skyzen.duel.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void cancelDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

}
