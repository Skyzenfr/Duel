package fr.skyzen.duel.listeners;

import fr.skyzen.duel.Main;
import fr.skyzen.duel.game.State;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.swing.*;

public class DamageListeners implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        final Entity victim = e.getEntity();

        if (!Main.getInstance().isState(State.PLAYING)) {
            e.setCancelled(true);
            return;
        }

        if (victim instanceof Player) {
            Player j = (Player) victim;
            Player killer = j;
            if (j.getHealth() <= e.getDamage()) {
                e.setDamage(0);
                Main.getInstance().eliminate(j);
                for (Player pls : Bukkit.getOnlinePlayers()) {
                    pls.playSound(pls.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
                }
                Bukkit.broadcastMessage("§8[§6Duel§8] §a" + ((Player) victim).getDisplayName() + " §7vient de se suicider.");
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
                Main.getInstance().eliminate(player);
                for (Player pls : Bukkit.getOnlinePlayers()) {
                    pls.playSound(pls.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
                }
                if(((Player) victim).getDisplayName() == ((Player) victim).getDisplayName()){
                    Bukkit.broadcastMessage("§8[§6Duel§8] §a" + ((Player) victim).getDisplayName() + " §7vient de se suicider.");
                }else if (!(((Player) victim).getDisplayName() == ((Player) victim).getDisplayName())){
                    Bukkit.broadcastMessage("§8[§6Duel§8] §a" + ((Player) victim).getDisplayName() + " §7a été tué par §c" + killer.getDisplayName());
                }
            }
        }

    }

}
