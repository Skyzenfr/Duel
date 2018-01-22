package fr.skyzen.duel.manager;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Duel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Win {

    public static void CheckWin() {
        if (Duel.getInstance().getPlayers().size() <= 1) {

            //ON DEFINI LE STATUS SUR FINI ET ON PREND LE GAGNANT
            Duel.getInstance().setGameStatus(GameStatus.FINISH);
            final Player winner = Duel.getInstance().getPlayers().get(0);

            winner.setAllowFlight(true);

            //ON DEFINI TOUTE LA PARTIE GRAHIQUE DE LA FIN DE PARTIE
            for (Player pl : Bukkit.getOnlinePlayers()) {
                TitleAPI.sendTitle(pl, 20, 80, 20, "§aFin de partie !", "§bLe joueur §e" + winner.getName() + " §bremporte la victoire!");
                pl.setLevel(0);
            }
            Duel.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Duel.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage("   ");
                    Bukkit.broadcastMessage("§7┼────§f────────────────────────§7────┼");
                    Bukkit.broadcastMessage("     ");
                    Bukkit.broadcastMessage("                         §e§lGagnant: §e" + winner.getName());
                    Bukkit.broadcastMessage("      ");
                    Bukkit.broadcastMessage("§7┼────§f────────────────────────§7────┼");
                    Bukkit.broadcastMessage("       ");
                }
            }, 20 * 4);
            Duel.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Duel.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage("§cRedémarrage du serveur dans 5 secondes.");
                }
            }, 20 * 8);

            //ON REBOOT LE SERVEUR
            Duel.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Duel.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.spigot().restart();
                }
            }, 20 * 12);
        }
    }

}
