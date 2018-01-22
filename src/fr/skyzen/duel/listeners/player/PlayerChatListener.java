package fr.skyzen.duel.listeners.player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Duel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void Chat(AsyncPlayerChatEvent e) {
        final Player j = e.getPlayer();

        e.setFormat("§7" + j.getDisplayName() + " §8➽§7 " + e.getMessage());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player j = e.getPlayer();
        e.setJoinMessage(null);

        TitleAPI.sendTabTitle(j, "§e§lSMASHS", "§fActualité, forum et support §b➔ §a§lSmashs.fr");

        for (Player pl : Bukkit.getOnlinePlayers()) {
            ActionBarAPI.sendActionBar(pl, "§a" + j.getDisplayName() + " §evient de rejoindre la partie §a(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player j = e.getPlayer();

        e.setQuitMessage(null);
        for (Player pl : Bukkit.getOnlinePlayers())
            ActionBarAPI.sendActionBar(pl, "§c" + j.getDisplayName() + " §ea quitté la partie §a(" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + Bukkit.getMaxPlayers() + ")");
    }

}
