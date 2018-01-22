package fr.skyzen.duel.runnable;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Duel;
import fr.skyzen.duel.manager.WinTime;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Class à revoir dans le détail, lors des vérifications, optimisations et debug de codes.
 **/

public class GameRunnable extends BukkitRunnable {

    private Duel duel;
    public int timer = 120;

    public GameRunnable(Duel duel) {
        this.duel = duel;
    }

    @Deprecated
    @Override
    public void run() {

        if (duel.getPlayers().size() < 2) {
            cancel();
            for (Player pl : duel.getPlayers()) {
                pl.setLevel(0);
            }
        }

        for (Player pl : duel.getPlayers()) {
            pl.setLevel(timer);
        }

        if (timer == 120 || timer == 90 || timer == 60 || timer == 30 || timer == 15 || timer == 10) {
            for (Player pl : duel.getPlayers()) {
                ActionBarAPI.sendActionBar(pl, "§cIl vous reste §e" + timer + " secondes §cavant la fin de la partie!", 50);
            }
        }
        if (timer == 5 || timer == 4) {
            for (Player pl : duel.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§6" + timer);
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        }
        if (timer == 3 || timer == 2 || timer == 1) {
            for (Player pl : duel.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§c" + timer);
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        }
        if (timer == 0) {
            for (int i = 0; i < duel.getPlayers().size(); i++) {
                final Player j = duel.getPlayers().get(i);

                //ON INITIALISE LES PROPRIETES DU JOUEUR
                j.teleport(new Location(Bukkit.getWorld("World"), 34.466, 3, 8.070, 89.8f, -3.0f));
                j.getInventory().clear();
                j.setGameMode(GameMode.ADVENTURE);
                j.setMaxHealth(20);
                j.setHealth(j.getMaxHealth());
                j.setLevel(0);
                j.removePotionEffect(PotionEffectType.ABSORPTION);
            }

            cancel();
            WinTime.CheckWinTime();
        }

        timer--;
    }
}
