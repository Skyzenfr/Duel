package fr.skyzen.duel.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFoodLevelListener implements Listener {

    @EventHandler
    public void onChangeFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
