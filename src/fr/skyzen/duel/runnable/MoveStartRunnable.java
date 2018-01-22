package fr.skyzen.duel.runnable;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Duel;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Class à revoir dans le détail, lors des vérifications, optimisations et debug de codes.
 **/

public class MoveStartRunnable extends BukkitRunnable {

    public int timer = 5;

    @Deprecated
    @Override
    public void run() {

        if (Duel.getInstance().getPlayers().size() < 2) {
            cancel();
            for (Player pl : Duel.getInstance().getPlayers()){
                pl.setLevel(0);
            }
        }

        for (Player pl : Duel.getInstance().getPlayers()) {
            pl.setLevel(timer);
        }

        if (timer == 5 || timer == 4) {
            for (Player pl : Duel.getInstance().getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§6" + timer);
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        }
        if (timer == 3 || timer == 2 || timer == 1) {
            for (Player pl : Duel.getInstance().getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§c" + timer);
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        }
        if (timer == 0) {
            cancel();
            for (Player pl : Duel.getInstance().getPlayers()) {
                pl.setLevel(120);
            }
            new GameRunnable(Duel.getInstance()).runTaskTimer(Duel.getInstance(), 0, 20);
        }

        timer--;
    }
}
