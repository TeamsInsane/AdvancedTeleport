package me.TeamsInsane.AdvancedTeleport.events;

import me.TeamsInsane.AdvancedTeleport.commands.impl.TpaCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (TpaCommand.playerArrayList.contains(player)) {
            Location from = e.getFrom();
            if (from.getZ() != e.getTo().getZ() && from.getX() != e.getTo().getX()) {
                player.teleport(e.getFrom());
            }
        }
    }
}
