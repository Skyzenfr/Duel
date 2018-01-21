package fr.skyzen.duel.listeners;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessagesListeners implements Listener {

    private Main main;

    public MessagesListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void Chat(AsyncPlayerChatEvent e) {
        final Player j = e.getPlayer();

        e.setFormat("§7" + j.getDisplayName() + " §8➽§7 " + e.getMessage());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player j = e.getPlayer();

        TitleAPI.sendTabTitle(j, "§e§lPIXELSPALACE", "§fActualité, forum et support §b➔ §a§lPixelsPalace.fr");

        e.setJoinMessage(null);
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
