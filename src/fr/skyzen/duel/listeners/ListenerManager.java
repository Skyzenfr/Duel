package fr.skyzen.duel.listeners;

import fr.skyzen.duel.Duel;
import fr.skyzen.duel.listeners.entity.EntityDamageListener;
import fr.skyzen.duel.listeners.player.*;
import fr.skyzen.duel.listeners.world.WeatherChangeListener;
import fr.skyzen.duel.manager.ClassesMenu;
import fr.skyzen.duel.manager.TeamSelector;
import fr.skyzen.duel.runnable.ScoreboardRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    public Duel plugin;
    public PluginManager pluginManager;

    /**
     * Constructeur du ListenerManager.
     *
     * @param plugin
     */
    public ListenerManager(Duel plugin) {
        this.plugin = plugin;
        this.pluginManager = Bukkit.getPluginManager();
    }


    /**
     * Liste des événements à recevoir.
     **/
    public void registerListeners() {

        /**
         * Listener concernant les entités.
         **/
        pluginManager.registerEvents(new EntityDamageListener(), plugin);

        /**
         * Listener concernant les joueurs.
         **/
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(), plugin);
        pluginManager.registerEvents(new PlayerFoodLevelListener(), plugin);
        pluginManager.registerEvents(new PlayerAnotherListener(), plugin);
        pluginManager.registerEvents(new PlayerDropItemListener(), plugin);
        pluginManager.registerEvents(new PlayerMoveListener(), plugin);
        pluginManager.registerEvents(new PlayerPickupItemListener(), plugin);
        pluginManager.registerEvents(new PlayerChatListener(), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(), plugin);
        pluginManager.registerEvents(new ScoreboardRunnable(), plugin);
        pluginManager.registerEvents(new ClassesMenu(), plugin);
        pluginManager.registerEvents(new TeamSelector(plugin), plugin);

        /**
         * Listener concernant le monde.
         **/
        pluginManager.registerEvents(new WeatherChangeListener(), plugin);
    }
}