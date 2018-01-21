package fr.skyzen.duel.listeners;

import fr.skyzen.duel.Main;
import fr.skyzen.duel.game.Kits;
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

public class ScoreboardListener implements Listener {

    private Main main;

    public ScoreboardListener(Main main) {
        this.main = main;
    }

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
        scoreboard.setLine(8, "§6§lplay.pixelspalace.fr");

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

            if (e.getCurrentItem().getType() == Kits.DEFENSEUR.getIcon().getType()) {
                if (boards.containsKey(j)) {
                    boards.get(j).setLine(5, "§7Classe: §bDéfenseur");
                }

            } else if (e.getCurrentItem().getType() == Kits.TANK.getIcon().getType()) {
                if (boards.containsKey(j)) {
                    boards.get(j).setLine(5, "§7Classe: §bTank");
                }

            } else if (e.getCurrentItem().getType() == Kits.DEFAUT.getIcon().getType()) {
                if (boards.containsKey(j)) {
                    boards.get(j).setLine(5, "§7Classe: §bDéfaut");
                }
            }

        } else {
            e.setCancelled(true);
        }

    }
}
