package fr.skyzen.duel.tasks;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Main;
import fr.skyzen.duel.game.Kits;
import fr.skyzen.duel.game.State;
import fr.skyzen.duel.utils.ItemModifier;
import fr.skyzen.duel.utils.ScoreboardSign;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class AutoStart extends BukkitRunnable {

    public int timer = 30;
    private Main main;

    public AutoStart(Main main) {
        this.main = main;
    }

    public Map<Player, ScoreboardSign> boards = new HashMap<>();

    @Deprecated
    @Override
    public void run() {

        if (main.getPlayers().size() < 2) {
            cancel();
            main.setState(State.WAITING);

            main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                public void run() {
                    for (Player pl : main.getPlayers()) {
                        pl.setLevel(30);
                    }
                }
            }, 20 * 1);
        }

        for (Player pl : main.getPlayers()) {
            pl.setLevel(timer);
        }

        //ON INITIALISE LES MESSAGES POUR LES DIFFERENTES SECONDES DU TIMER
        if (timer == 30 || timer == 15) {
            for (Player pl : main.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§b" + timer, "");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
            }
        }
        if (timer == 10) {
            for (Player pl : main.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§e" + timer, "§cAttention!");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
            }
        }
        if (timer == 5 || timer == 4) {
            for (Player pl : main.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§6" + timer, "§ePréparez-vous!");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        } else if (timer == 3 || timer == 2 || timer == 1) {
            for (Player pl : main.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§c" + timer, "§ePréparez-vous!");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        }

        if (timer == 0) {
            main.setState(State.PLAYING);

            for (int i = 0; i < main.getPlayers().size(); i++) {
                final Player j = main.getPlayers().get(i);

                //ON INITIALISE LES PROPRIETES DU JOUEUR
                Location spawn = main.getSpawns().get(i);
                j.teleport(spawn);
                j.setGameMode(GameMode.SURVIVAL);
                j.setMaxHealth(40);
                j.setHealth(j.getMaxHealth());
                j.setLevel(120);

                //ON ENVOI UN TITLE AUX JOUEURS
                for (Player pl : main.getPlayers()) {
                    if(main.getPlayers().size() == 2){
                        TitleAPI.sendTitle(pl, 20, 20, 20, "§aFIGHT!", "§eAllez tuer votre adversaire");
                    }else if(main.getPlayers().size() > 2){
                        TitleAPI.sendTitle(pl, 20, 20, 20, "§aFIGHT!", "§eAllez tuer vos adversaires");
                    }
                    pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);

                    //ON DONNE LES KITS AUX JOUEURS
                    PlayerInventory inv = j.getInventory();
                    inv.clear();

                    if (Main.getInstance().kits.get(j) == Kits.DEFAUT) {

                        //EQUIPEMENT
                        inv.setItem(0, ItemModifier.setText(new ItemStack(Material.IRON_SWORD, 1), "", ""));
                        inv.setItem(1, ItemModifier.setText(new ItemStack(Material.BOW, 1), "", ""));
                        inv.setItem(28, ItemModifier.setText(new ItemStack(Material.ARROW, 6), "", ""));

                        //ARMURE
                        inv.setItem(39, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_HELMET, 1), "", ""));
                        inv.setItem(38, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "", ""));
                        inv.setItem(37, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "", ""));
                        inv.setItem(36, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_BOOTS, 1), "", ""));

                    } else if (Main.getInstance().kits.get(j) == Kits.TANK) {

                        //EQUIPEMENT
                        inv.setItem(0, ItemModifier.setText(new ItemStack(Material.STONE_SWORD, 1), "", ""));
                        inv.setItem(1, ItemModifier.setText(new ItemStack(Material.BOW, 1), "", ""));
                        inv.setItem(28, ItemModifier.setText(new ItemStack(Material.ARROW, 3), "", ""));

                        //SPECIALITE
                        inv.setItem(40, ItemModifier.setText(new ItemStack(Material.SHIELD, 1), "", ""));

                        //ARMURE
                        inv.setItem(39, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_HELMET, 1), "", ""));
                        inv.setItem(38, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "", ""));
                        inv.setItem(37, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "", ""));
                        inv.setItem(36, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_BOOTS, 1), "", ""));

                    } else if (Main.getInstance().kits.get(j) == Kits.DEFENSEUR) {

                        inv.setItem(0, ItemModifier.setText(new ItemStack(Material.IRON_SWORD, 1), "", ""));
                        inv.setItem(1, ItemModifier.setText(new ItemStack(Material.BOW, 1), "", ""));
                        inv.setItem(28, ItemModifier.setText(new ItemStack(Material.ARROW, 3), "", ""));

                        //SPECIALITE
                        inv.setItem(8, ItemModifier.setText(new ItemStack(Material.GOLDEN_APPLE, 1), "", ""));

                        //ARMURE
                        inv.setItem(39, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_HELMET, 1), "", ""));
                        inv.setItem(38, ItemModifier.setText(new ItemStack(Material.IRON_CHESTPLATE, 1), "", ""));
                        inv.setItem(37, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "", ""));
                        inv.setItem(36, ItemModifier.setText(new ItemStack(Material.IRON_BOOTS, 1), "", ""));

                    }

                    j.updateInventory();

                    new GameCycle(main).runTaskTimer(main, 0, 20);
                }
            }

            cancel();
        }

        timer--;

    }

}
