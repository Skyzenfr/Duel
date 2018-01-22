package fr.skyzen.duel.listeners.entity;

import fr.skyzen.duel.Duel;
import fr.skyzen.duel.manager.Equipe;
import fr.skyzen.duel.manager.GameStatus;
import fr.skyzen.duel.manager.Joueur;
import fr.skyzen.duel.manager.Win;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import sun.applet.Main;

import java.util.Map;

/**
 * Class à revoir dans le détail, lors des vérifications, optimisations et debug de codes.
 **/

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        final Entity victim = e.getEntity();

        if (!Duel.getInstance().isState(GameStatus.PLAYING)) {
            e.setCancelled(true);
            return;
        }

        if (victim instanceof Player) {
            Player j = (Player) victim;
            if (j.getHealth() <= e.getDamage()) {
                e.setDamage(0);
                eliminate(j);
                for (Player pls : Bukkit.getOnlinePlayers()) {
                    pls.playSound(pls.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
                }
                for (Map.Entry<Player, Joueur> entry : Duel.getInstance().pj.entrySet()) {
                    final Player playerd = entry.getKey();
                    if(Duel.getInstance().blue.containsPlayer(playerd)) {
                        Bukkit.broadcastMessage("§8[§6Duel§8] §9" + ((Player) victim).getDisplayName() + " §7vient d'être éliminé.");
                    }else if(Duel.getInstance().red.containsPlayer(playerd)) {
                        Bukkit.broadcastMessage("§8[§6Duel§8] §c" + ((Player) victim).getDisplayName() + " §7vient d'être éliminé.");
                    }
                }
            }
        }

    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent e) {
        final Entity victim = e.getEntity();

        if (victim instanceof Player) {
            Player player = (Player) victim;
            Entity damager = e.getDamager();
            Player killer = player;

            if (player.getHealth() <= e.getDamage()) {
                if (damager instanceof Player) killer = (Player) damager;

                if (damager instanceof Arrow) {
                    Arrow arrow = (Arrow) damager;
                    if (arrow.getShooter() instanceof Player) {
                        killer = (Player) arrow.getShooter();
                    }
                }
                e.setDamage(0);
                eliminate(player);
                for (Player pls : Bukkit.getOnlinePlayers()) {
                    pls.playSound(pls.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
                }
                for (Map.Entry<Player, Joueur> entry : Duel.getInstance().pj.entrySet()) {
                    final Player playerd = entry.getKey();
                    if(Duel.getInstance().blue.containsPlayer(playerd)) {
                        Bukkit.broadcastMessage("§8[§6Duel§8] §9" + ((Player) victim).getDisplayName() + " §7vient d'être éliminé.");
                    }else if(Duel.getInstance().red.containsPlayer(playerd)) {
                        Bukkit.broadcastMessage("§8[§6Duel§8] §c" + ((Player) victim).getDisplayName() + " §7vient d'être éliminé.");
                    }
                }
            }

        }
    }

    public void eliminate(Player player) {
        if (Duel.getInstance().getPlayers().contains(player)) Duel.getInstance().getPlayers().remove(player);
        player.setGameMode(GameMode.SPECTATOR);
        Win.CheckWin();
    }

}
