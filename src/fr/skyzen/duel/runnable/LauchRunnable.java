package fr.skyzen.duel.runnable;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.skyzen.duel.Duel;
import fr.skyzen.duel.manager.Equipe;
import fr.skyzen.duel.manager.GameStatus;
import fr.skyzen.duel.manager.Classes;
import fr.skyzen.duel.manager.Joueur;
import fr.skyzen.duel.utils.ItemModifier;
import fr.skyzen.duel.utils.ScoreboardSign;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Class à revoir dans le détail, lors des vérifications, optimisations et debug de codes.
 **/

public class LauchRunnable extends BukkitRunnable {

    public int timer = 30;
    private Duel duel;
    private Equipe blue;
    private Equipe red;

    public LauchRunnable(Duel duel) {
        this.duel = duel;
    }
    private HashMap<Player, Joueur> pj;

    public Map<Player, ScoreboardSign> boards = new HashMap<>();

    @Deprecated
    @Override
    public void run() {

        if (duel.getPlayers().size() < 2) {
            cancel();
            duel.setGameStatus(GameStatus.WAITING);

            duel.getServer().getScheduler().scheduleSyncDelayedTask(duel, new Runnable() {
                public void run() {
                    for (Player pl : duel.getPlayers()) {
                        pl.setLevel(30);
                    }
                }
            }, 20 * 1);
        }

        for (Player pl : duel.getPlayers()) {
            pl.setLevel(timer);
        }

        //ON INITIALISE LES MESSAGES POUR LES DIFFERENTES SECONDES DU TIMER
        if (timer == 30 || timer == 15) {
            for (Player pl : duel.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§b" + timer, "");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
            }
        }
        if (timer == 10) {
            for (Player pl : duel.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§e" + timer, "§cAttention!");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
            }
        }
        if (timer == 5 || timer == 4) {
            for (Player pl : duel.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§6" + timer, "§ePréparez-vous!");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        } else if (timer == 3 || timer == 2 || timer == 1) {
            for (Player pl : duel.getPlayers()) {
                TitleAPI.sendTitle(pl, 20, 50, 20, "§c" + timer, "§ePréparez-vous!");
                pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 1, 1);
            }
        }

        if (timer == 0) {
            duel.setGameStatus(GameStatus.PLAYING);

            Duel.getInstance().start();

            for (int i = 0; i < duel.getPlayers().size(); i++) {
                final Player j = duel.getPlayers().get(i);

                //ON INITIALISE LES PROPRIETES DU JOUEUR
                j.setGameMode(GameMode.SURVIVAL);
                j.setMaxHealth(40);
                j.setHealth(j.getMaxHealth());
                j.setLevel(120);

                //ON ENVOI UN TITLE AUX JOUEURS
                for (Player pl : duel.getPlayers()) {
                    if(duel.getPlayers().size() == 2){
                        TitleAPI.sendTitle(pl, 20, 20, 20, "§aFIGHT!", "§eAllez tuer votre adversaire");
                    }else if(duel.getPlayers().size() > 2){
                        TitleAPI.sendTitle(pl, 20, 20, 20, "§aFIGHT!", "§eAllez tuer vos adversaires");
                    }
                    pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);

                    //ON DONNE LES KITS AUX JOUEURS
                    PlayerInventory inv = j.getInventory();
                    inv.clear();

                    if (Duel.getInstance().kits.get(j) == Classes.DEFAUT) {

                        //EQUIPEMENT
                        inv.setItem(0, ItemModifier.setText(new ItemStack(Material.IRON_SWORD, 1), "", ""));
                        inv.setItem(1, ItemModifier.setText(new ItemStack(Material.BOW, 1), "", ""));
                        inv.setItem(28, ItemModifier.setText(new ItemStack(Material.ARROW, 6), "", ""));

                        //ARMURE
                        inv.setItem(39, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_HELMET, 1), "", ""));
                        inv.setItem(38, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "", ""));
                        inv.setItem(37, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "", ""));
                        inv.setItem(36, ItemModifier.setText(new ItemStack(Material.CHAINMAIL_BOOTS, 1), "", ""));

                    } else if (Duel.getInstance().kits.get(j) == Classes.TANK) {

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

                    } else if (Duel.getInstance().kits.get(j) == Classes.DEFENSEUR) {

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

                    new MoveStartRunnable().runTaskTimer(duel, 0, 20);
                }
            }

            cancel();
        }

        timer--;

    }

}
