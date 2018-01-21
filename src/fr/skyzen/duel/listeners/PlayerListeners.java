package fr.skyzen.duel.listeners;

import fr.skyzen.duel.Main;
import fr.skyzen.duel.game.Kits;
import fr.skyzen.duel.game.State;
import fr.skyzen.duel.tasks.AutoStart;
import fr.skyzen.duel.utils.ItemModifier;
import fr.skyzen.duel.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

public class PlayerListeners implements Listener {

    private Main main;

    public PlayerListeners(Main main) {
        this.main = main;
    }

    @Deprecated
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player j = e.getPlayer();

        //ON INITIALISE TOUT LES PARAMETRES DU JOUEUR
        j.teleport(new Location(Bukkit.getWorld("World"), 34.466, 3, 8.070, 89.8f, -3.0f));
        j.setFoodLevel(20);
        j.setMaxHealth(20);
        j.setHealth(j.getMaxHealth());
        j.setGameMode(GameMode.ADVENTURE);
        j.setLevel(30);
        j.removePotionEffect(PotionEffectType.ABSORPTION);
        Kits.DEFAUT.add(j);

        if (!main.getPlayers().contains(j)) {
            main.getPlayers().add(j);
        }

        //ON DEFINI TOUTE LA PARTIE GRAHIQUE QUAND ON REJOINT
        Particle particle = new Particle(EnumParticle.CLOUD, j.getLocation().add(0, 2.25, 0), true, 0.75f, 0.75f, 0.75f, 0, 35);
        Particle particle1 = new Particle(EnumParticle.VILLAGER_HAPPY, j.getLocation().add(0, 2.25, 0), true, 0.75f, 0.75f, 0.75f, 0, 35);

        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.playSound(j.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 1);
            particle.sendPlayer(pl);
            particle1.sendPlayer(pl);
        }

        //ON LUI DONNE LES ITEMS DU LOBBY
        PlayerInventory inv = j.getInventory();

        inv.clear();
        inv.setItem(0, ItemModifier.setText(new ItemStack(Material.NAME_TAG, 1), "§bSélecteur de Classes §7(Clic-droit)"));
        inv.setItem(4, ItemModifier.setBookPages(ChatColor.GOLD + "But du jeu §7(Clic-droit)", "PixelsPalace", ChatColor.BLACK + " " + ChatColor.BOLD + "Bienvenue dans le jeu Duel\n\n" + ChatColor.LIGHT_PURPLE + "Le but du jeu est de se battre en 1 versus 1..."));
        inv.setItem(8, ItemModifier.setText(new ItemStack(Material.BED, 1), "§cQuitter la partie §7(Clic-droit)"));

        //ON KICK LE JOUEUR SI LA PARTIE A DEJA COMMENCE
        if (main.isState(State.PLAYING) || main.isState(State.FINISH) && main.getPlayers().size() >= 3){
            e.getPlayer().kickPlayer("");
        }else if(main.isState(State.WAITING) && main.getPlayers().size() > 3){
            e.getPlayer().kickPlayer("");
        }else if(main.isState(State.STARTING) && main.getPlayers().size() > 3){
            e.getPlayer().kickPlayer("");
        }

        if (main.isState(State.WAITING) && main.getPlayers().size() >= 2) {
            main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                public void run() {
                    AutoStart start = new AutoStart(main);
                    start.runTaskTimer(main, 0, 20);
                    main.setState(State.STARTING);
                }
            }, 20 * 2);
        }

        e.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player j = e.getPlayer();

        if (main.getPlayers().contains(j)) {
            main.getPlayers().remove(j);
        }

        if (main.isState(State.PLAYING)) {
            main.getPlayers().remove(j);
            main.win();
        }

        e.setQuitMessage(null);
    }

    @EventHandler
    public void onChangeFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
