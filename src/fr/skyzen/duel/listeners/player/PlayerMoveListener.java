package fr.skyzen.duel.listeners.player;

import com.connorlinfoot.titleapi.TitleAPI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent e) {
        final Player j = e.getPlayer();

        if (e.isCancelled())
            return;

        /**
         * Vérification du gamemode du joueur et si il tombe à Y = -4.
         **/
        if (j.getGameMode() != GameMode.SPECTATOR) {
            if (e.getTo().getBlockY() <= -4) {
                j.teleport((new Location(j.getWorld(), -8.257, 4.63656, 8.410, 90.1f, -1.5f)));
                TitleAPI.sendTitle(j, 20, 40, 20, "", "§7Ne fuiez pas le combat!");
            }
        }
    }

}
