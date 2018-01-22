package fr.skyzen.duel;

import fr.skyzen.duel.listeners.ListenerManager;
import fr.skyzen.duel.manager.Equipe;
import fr.skyzen.duel.manager.GameStatus;
import fr.skyzen.duel.manager.Classes;
import fr.skyzen.duel.manager.Joueur;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Duel extends JavaPlugin {

    public static Duel instance;
    private GameStatus gameStatus;
    public HashMap<Player, Joueur> pj;
    public Equipe blue;
    public Equipe red;

    public HashMap<Player, Classes> kits = new HashMap<>();
    private List<Player> players = new ArrayList<>();
    private List<Location> spawns = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        this.pj = new HashMap<>();

        /**
         * On envoi ce message dans la console si le plugin s'est bien démarré.
         **/
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info("");
        getLogger().info(getDescription().getName());
        getLogger().info("Version " + getDescription().getVersion());
        getLogger().info("");
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        /**
         * On met le status de jeu sur Attente.
         **/
        setGameStatus(GameStatus.WAITING);
        getLogger().info("§eLe status de jeu est en Attente.");

        /**
         * Initialisation des listeners.
         **/
        new ListenerManager(instance).registerListeners();

        /**
         * Chargement des points d'apparition pour les équipes.
         **/
        this.blue = new Equipe("bleu", "§9", new Location(Bukkit.getWorld("world"), 13.052, 1, 8.009, 88.5f, -1.0f));
        this.red = new Equipe("rouge", "§c", new Location(Bukkit.getWorld("world"), -29.180, 1, 8.031, -89.3f, -3.0f));
        blue.setAdv(red);
        red.setAdv(blue);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Duel getInstance() {
        return instance;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean isState(GameStatus gameStatus) {
        return this.gameStatus == gameStatus;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public final Joueur addPlayer(Player p) {
        if (pj.containsKey(p))
            return pj.get(p);
        return pj.put(p, new Joueur(p));
    }

    public final void removePlayer(Player p) {
        if (pj.containsKey(p))
            pj.remove(p);
    }

    public Joueur getJoueur(Player p) {
        return (pj.get(p));
    }

    public final Equipe getBlue() {
        return blue;
    }

    public final Equipe getRed() {
        return red;
    }

    public final void start(){

        for (Map.Entry<Player, Joueur> entry : pj.entrySet()) {
            final Joueur joueur = entry.getValue();
            final Player player = entry.getKey();

            joueur.getPlayer().setGameMode(GameMode.SURVIVAL);
            joueur.getPlayer().getInventory().clear();

            if (blue.containsPlayer(player) || red.containsPlayer(player))
                continue;

            if (blue.getJoueurs().size() < red.getJoueurs().size())
                blue.addJoueur(joueur);
            else if (red.getJoueurs().size() < blue.getJoueurs().size())
                red.addJoueur(joueur);
            else
                red.addJoueur(joueur);
        }

        for (Joueur j : blue.getJoueurs()){
            j.getPlayer().teleport(blue.getSpawn());
            j.getPlayer().setPlayerListName("§9" + j.getPlayer().getName());
        }
        for (Joueur j : red.getJoueurs()){
            j.getPlayer().teleport(red.getSpawn());
            j.getPlayer().setPlayerListName("§c" + j.getPlayer().getName());
        }
    }

}
