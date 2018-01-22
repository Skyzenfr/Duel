package fr.skyzen.duel.manager;

import fr.skyzen.duel.Duel;
import fr.skyzen.duel.utils.ItemModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class TeamSelector implements Listener
{
    private final Duel plugin;
    private final Scoreboard board;
    private final Team red, blue, neutre;

    public TeamSelector(Duel plugin)
    {
        this.plugin = plugin;
        this.board = plugin.getServer().getScoreboardManager().getNewScoreboard();
        this.red = board.registerNewTeam("Rouge");
        this.blue = board.registerNewTeam("Bleu");
        this.neutre = board.registerNewTeam("Neutre");
    }

    @Deprecated
    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        if (!e.getInventory().getTitle().equalsIgnoreCase("Sélecteur d'équipes"))
            return;
        if (e.getCurrentItem() == null)
            return;

        if (e.getCurrentItem().getDurability() == 11)
            switch (plugin.getBlue().addJoueur(plugin.getJoueur((Player) e.getWhoClicked())))
            {
                case -1:
                    e.getWhoClicked().sendMessage("§8[§6Equipes§8] §cL'équipe est pleine!");
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    break;
                case 0:
                    e.getWhoClicked().sendMessage("§8[§6Equipes§8] §cVous êtes déjà dans l'équipe §9bleu.");
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    break;
                case 1:
                    e.getWhoClicked().sendMessage("§8[§6Equipes§8] §7Vous avez rejoint l'équipe §9bleu");
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                    blue.addPlayer((Player) e.getWhoClicked());
                    for (Player p : Bukkit.getOnlinePlayers())
                        p.setScoreboard(board);
                    ((Player) e.getWhoClicked()).setPlayerListName("§9" + e.getWhoClicked().getName());
                    e.getWhoClicked().closeInventory();
                    break;
            }
        else if (e.getCurrentItem().getDurability() == 14)
            switch (plugin.getRed().addJoueur(plugin.getJoueur((Player) e.getWhoClicked())))
            {
                case -1:
                    e.getWhoClicked().sendMessage("§8[§6Equipes§8] §cL'équipe est pleine!");
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    break;
                case 0:
                    e.getWhoClicked().sendMessage("§8[§6Equipes§8] §cVous êtes déjà dans l'équipe §cRouge.");
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    break;
                case 1:
                    e.getWhoClicked().sendMessage("§8[§6Equipes§8] §7Vous avez rejoint l'équipe §cRouge");
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                    red.addPlayer((Player) e.getWhoClicked());
                    for (Player p : Bukkit.getOnlinePlayers())
                        p.setScoreboard(board);
                    ((Player) e.getWhoClicked()).setPlayerListName("§c" + e.getWhoClicked().getName());
                    e.getWhoClicked().closeInventory();
                    break;
            }
    }

    @EventHandler
    public void openInv(PlayerInteractEvent e)
    {
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) || e.getItem() == null)
            return;
        if (e.getItem().getType() == Material.NETHER_STAR)
        {
            final Inventory inv = Bukkit.createInventory(e.getPlayer(), 9, "Sélecteur d'équipes");

            //première ligne
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Joueur(s) dans cette équipe:");
            for (Joueur j : plugin.getBlue().getJoueurs())
                lore.add("§9• " + j.getPlayer().getName());
            lore.add("");
            if (plugin.getBlue().containsPlayer(e.getPlayer()))
                lore.add("§c✖ Vous êtes dans cette équipe ✖");
            else
                lore.add("§a✔ Cliquez ici pour rejoindre cette équipe ✔");
            String[] lore2 = new String[lore.size()];
            lore2 = lore.toArray(lore2);
            inv.setItem(0, ItemModifier.setText(new ItemStack(Material.WOOL, 1, (short) 11), "§9Bleu", lore2));
            lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Joueur(s) dans cette équipe:");
            for (Joueur j : plugin.getRed().getJoueurs())
                lore.add("§c• " + j.getPlayer().getName());
            lore2 = new String[lore.size()];
            lore.add("");
            if (plugin.getRed().containsPlayer(e.getPlayer()))
                lore.add("§c✖ Vous êtes dans cette équipe ✖");
            else
                lore.add("§a✔ Cliquez ici pour rejoindre cette équipe ✔");
            lore2 = lore.toArray(lore2);
            inv.setItem(1, ItemModifier.setText(new ItemStack(Material.WOOL, 1, (short) 14), "§cRouge", lore2));

            //fermer l'inventaire
            inv.setItem(8, ItemModifier.setText(new ItemStack(Material.ARROW, 1), "§fFermer l'inventaire", ""));

            e.getPlayer().openInventory(inv);
        }
    }

}
