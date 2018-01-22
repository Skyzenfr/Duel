package fr.skyzen.duel.runnable;

/**
 * Class à revoir dans le détail, lors des vérifications, optimisations et debug de codes.
 **/

import fr.skyzen.duel.manager.Classes;
import fr.skyzen.duel.utils.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardRunnable implements Listener {

    public Map<Player, ScoreboardSign> boards = new HashMap<>();

    @EventHandler
    public void onJoinSetboard(PlayerJoinEvent e) {
        final Player j = e.getPlayer();

        for (Map.Entry<Player, ScoreboardSign> sign : boards.entrySet()) {
            sign.getValue().setLine(3, "§7Joueurs: §e" + Bukkit.getOnlinePlayers().size() + "§8/§e" + Bukkit.getMaxPlayers());
        }

        ScoreboardSign scoreboard = new ScoreboardSign(j, "§6§lDuel");
        scoreboard.create();

        scoreboard.setLine(2, "     ");
        scoreboard.setLine(3, "§7Joueurs: §e" + Bukkit.getOnlinePlayers().size() + "§8/§e" + Bukkit.getMaxPlayers());
        scoreboard.setLine(4, "   ");
        scoreboard.setLine(5, "§7Classe: §bDéfaut");
        scoreboard.setLine(6, "§7Carte: §bSpace");
        scoreboard.setLine(7, " ");
        scoreboard.setLine(8, "§6§lplay.smashs.fr");

        boards.put(j, scoreboard);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player j = e.getPlayer();

        for (Map.Entry<Player, ScoreboardSign> sign : boards.entrySet()) {
            sign.getValue().setLine(3, "§7Joueurs: §e" + (Bukkit.getOnlinePlayers().size() - 1) + "§8/§e" + Bukkit.getMaxPlayers());
        }

        if (boards.containsKey(j)) {
            boards.get(j).destroy();
        }
        e.setQuitMessage(null);
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent e) {
        final Player j = (Player) e.getWhoClicked();

        if (e.getInventory().getName().equalsIgnoreCase("§7Sélecteur de Classes")) {

            if (e.getCurrentItem() == null) return;
            e.setCancelled(true);
            j.closeInventory();

            if (e.getCurrentItem().getType() == Classes.DEFENSEUR.getIcon().getType()) {
                if (boards.containsKey(j)) {
                    boards.get(j).setLine(5, "§7Classe: §bDéfenseur");
                }

            } else if (e.getCurrentItem().getType() == Classes.TANK.getIcon().getType()) {
                if (boards.containsKey(j)) {
                    boards.get(j).setLine(5, "§7Classe: §bTank");
                }

            } else if (e.getCurrentItem().getType() == Classes.DEFAUT.getIcon().getType()) {
                if (boards.containsKey(j)) {
                    boards.get(j).setLine(5, "§7Classe: §bDéfaut");
                }
            }

        } else {
            e.setCancelled(true);
        }

    }
}
