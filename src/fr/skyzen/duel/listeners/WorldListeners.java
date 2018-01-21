package fr.skyzen.duel.listeners;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListeners implements Listener {

    private Main main;

    public WorldListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @Deprecated
    @EventHandler
    public void cancelPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryEvent(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void cancelDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDoorOpen(PlayerInteractEvent e) {

        //CLIQUE DROIT ET GAUCHE ?
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
            //BLOCK DE PORTE ?
            if ((e.getClickedBlock().getType() == Material.IRON_DOOR) || (e.getClickedBlock().getType() == Material.WOODEN_DOOR) || (e.getClickedBlock().getType() == Material.TRAP_DOOR)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent e) {
        final Player j = e.getPlayer();

        if (e.isCancelled())
            return;
        if (e.getTo().getBlockY() <= -4) {
            j.teleport((new Location(j.getWorld(), -8.257, 4.63656, 8.410, 90.1f, -1.5f)));
            TitleAPI.sendTitle(j, 20, 40, 20, "", "§7Ne fuiez pas le combat!");
        }
    }

}
