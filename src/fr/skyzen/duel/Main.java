package fr.skyzen.duel;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.game.BedClick;
import fr.skyzen.duel.game.KitMenu;
import fr.skyzen.duel.game.Kits;
import fr.skyzen.duel.game.State;
import fr.skyzen.duel.listeners.*;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main instance;
    private State state;

    public HashMap<Player, Kits> kits = new HashMap<>();
    private List<Player> players = new ArrayList<>();
    private List<Location> spawns = new ArrayList<>();


    @Override
    public void onEnable() {
        instance = this;
        setState(State.WAITING);

        //ON CHARGE LES SPAWNS QUAND LA PARTIE COMMENCE
        spawns.add(new Location(Bukkit.getWorld("world"), 13.052, 1, 8.009, 88.5f, -1.0f));
        spawns.add(new Location(Bukkit.getWorld("world"), -29.180, 1, 8.031, -89.3f, -3.0f));
        spawns.add(new Location(Bukkit.getWorld("world"), -7.889, 1, 30.124, 180.0f, -2.5f));

        //ON CHARGE TOUT LES LISTENERS
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        getServer().getPluginManager().registerEvents(new DamageListeners(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldListeners(this), this);
        getServer().getPluginManager().registerEvents(new MessagesListeners(this), this);
        getServer().getPluginManager().registerEvents(new KitMenu(), this);
        getServer().getPluginManager().registerEvents(new BedClick(), this);

        //QUAND LE SERVEUR DEMARRE ON ENVOIE CE MESSAGE SI LE PLUGIN FONCTIONNE CORRECTEMENT
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info("");
        getLogger().info(getDescription().getName());
        getLogger().info("Version " + getDescription().getVersion());
        getLogger().info("");
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

    }

    public static Main getInstance() {
        return instance;
    }

    public void win() {
        if (getPlayers().size() == 1) {

            //ON DEFINI LE STATUS SUR FINI ET ON PREND LE GAGNANT
            setState(State.FINISH);
            final Player winner = getPlayers().get(0);

            //ON LANCE UN FEU D'ARTIFICE A LA POSITION DU GAGNANT
            Firework fw = (Firework) winner.getWorld().spawnEntity(winner.getLocation(), EntityType.FIREWORK);
            fw.getFireworkMeta().addEffect(FireworkEffect.builder()
                    .flicker(true)
                    .trail(true)
                    .with(FireworkEffect.Type.BURST)
                    .withColor(Color.GREEN)
                    .withFade(Color.AQUA)
                    .build());
            fw.getFireworkMeta().setPower(2);
            fw.setFireworkMeta(fw.getFireworkMeta());

            winner.setAllowFlight(true);

            //ON DEFINI TOUTE LA PARTIE GRAHIQUE DE LA FIN DE PARTIE
            for (Player pl : Bukkit.getOnlinePlayers()) {
                TitleAPI.sendTitle(pl, 20, 80, 20, "§aFin de partie !", "§bLe joueur §e" + winner.getName() + " §bremporte la victoire!");
                pl.setLevel(0);
            }
            getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
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
            getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage("§cRedémarrage du serveur dans 5 secondes.");
                }
            }, 20 * 8);

            //ON REBOOT LE SERVEUR
            getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    Bukkit.spigot().restart();
                }
            }, 20 * 12);
        }
    }

    public void wintime() {
        setState(State.FINISH);

        //ON ENVOI UN TITLE AUX JOUEURS
        for (Player pl : getPlayers()) {
            TitleAPI.sendTitle(pl, 20, 20, 20, "§cDOMMAGE!", "§7Vous n'êtes pas aller au bout de votre combat.");
            pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
        }
            //ON REBOOT LE SERVEUR
            getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    Bukkit.spigot().restart();
                }
            }, 20 * 4);
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isState(State state) {
        return this.state == state;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void eliminate(Player player) {
        if (getPlayers().contains(player)) getPlayers().remove(player);
        player.setGameMode(GameMode.SPECTATOR);
        win();
    }

}
