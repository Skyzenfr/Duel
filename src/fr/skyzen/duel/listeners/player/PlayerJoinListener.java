package fr.skyzen.duel.listeners.player;

import fr.skyzen.duel.Duel;
import fr.skyzen.duel.manager.GameStatus;
import fr.skyzen.duel.manager.Classes;
import fr.skyzen.duel.manager.Joueur;
import fr.skyzen.duel.runnable.LauchRunnable;
import fr.skyzen.duel.utils.ItemModifier;
import fr.skyzen.duel.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinListener implements Listener {

    @Deprecated
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player j = e.getPlayer();
        final Joueur ju = Duel.getInstance().addPlayer(j);

        /**
         * Initialisation de tout les paramètres du joueur lors de la connexion.
         **/
        j.setGameMode(GameMode.ADVENTURE);
        j.setMaxHealth(20);
        j.setHealth(j.getMaxHealth());
        j.setLevel(30);
        j.removePotionEffect(PotionEffectType.ABSORPTION);
        j.teleport(new Location(Bukkit.getWorld("World"), 34.466, 3, 8.070, 89.8f, -3.0f));

        //On lui donne la classe par défaut pour éviter un bug.
        Classes.DEFAUT.add(j);

        //Si le joueur n'est pas dans la liste des participants à la partie on l'ajoute.
        if (!Duel.getInstance().getPlayers().contains(j)) {
            Duel.getInstance().getPlayers().add(j);
        }

        //On lui donne les items de la salle d'attente.
        j.getInventory().clear();
        j.getInventory().setItem(0, ItemModifier.setText(new ItemStack(Material.NAME_TAG, 1), "§bSélecteur de Classes §7(Clic-droit)"));
        j.getInventory().setItem(4, ItemModifier.setText(new ItemStack(Material.NETHER_STAR, 1), "§eSélecteur d'équipes §7(Clic-droit)"));
        j.getInventory().setItem(8, ItemModifier.setText(new ItemStack(Material.BED, 1), "§cQuitter la partie §7(Clic-droit)"));

        //On envoi un son puis des particules quand il vient d'apparaitre dans la salle d'attente.
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.playSound(j.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 1);
            new Particle(EnumParticle.CLOUD, j.getLocation().add(0, 2.25, 0), true, 0.75f, 0.75f, 0.75f, 0, 35).sendPlayer(pl);
            new Particle(EnumParticle.VILLAGER_HAPPY, j.getLocation().add(0, 2.25, 0), true, 0.75f, 0.75f, 0.75f, 0, 35).sendPlayer(pl);
        }

        /**
         * Vérification du status de jeu et du commencement de la partie.
         *
         * - Si le status de jeu est en attente/démarrage et qu'il y'a 4 personnes = expulsion.
         * - Si le status de jeu est en jeu/terminé = expulsion.
         **/
        //On expulse le joueur si (voir dessus).
        if (Duel.getInstance().isState(GameStatus.WAITING) && Duel.getInstance().isState(GameStatus.WAITING)) {
            if (Duel.getInstance().getPlayers().size() == 4) {
                j.kickPlayer("§6Smashs §f➽ §eLa partie est pleine.");
            }
        } else if (Duel.getInstance().isState(GameStatus.PLAYING) && Duel.getInstance().isState(GameStatus.FINISH)) {
            j.kickPlayer("§6Smashs §f➽ §eLa partie a déjà commencé.");
        }

        //On commence la partie si il y'a plus ou deux joueurs de connectés.
        if (Duel.getInstance().isState(GameStatus.WAITING) && Duel.getInstance().getPlayers().size() >= 2) {
            Duel.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Duel.getInstance(), new Runnable() {
                public void run() {
                    LauchRunnable start = new LauchRunnable(Duel.getInstance());
                    start.runTaskTimer(Duel.getInstance(), 0, 20);
                    Duel.getInstance().setGameStatus(GameStatus.STARTING);
                }
            }, 20 * 2);
        }
    }
}
