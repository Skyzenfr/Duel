package fr.skyzen.duel.manager;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Duel;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class WinTime {

    public static void CheckWinTime() {
        Duel.getInstance().setGameStatus(GameStatus.FINISH);

        //ON ENVOI UN TITLE AUX JOUEURS
        for (Player pl : Duel.getInstance().getPlayers()) {
            TitleAPI.sendTitle(pl, 20, 20, 20, "§cDOMMAGE!", "§7Vous n'êtes pas aller au bout de votre combat.");
            pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
        }
        //ON REBOOT LE SERVEUR
        Duel.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Duel.getInstance(), new Runnable() {
            public void run() {
                Bukkit.spigot().restart();
            }
        }, 20 * 4);
    }

}
