package fr.skyzen.duel.listeners.player;

import fr.skyzen.duel.Duel;
import fr.skyzen.duel.manager.GameStatus;
import fr.skyzen.duel.manager.Win;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player j = e.getPlayer();

        /**
         * Vérification: Si le joueur est dans la liste des participants on le retire.
         **/
        if(Duel.getInstance().getPlayers().contains(j)){
            Duel.getInstance().getPlayers().remove(j);
        }

        if(Duel.getInstance().isState(GameStatus.PLAYING)){
            Duel.getInstance().getPlayers().remove(j);
            Win.CheckWin();
        }

        e.setQuitMessage(null);
    }

}
