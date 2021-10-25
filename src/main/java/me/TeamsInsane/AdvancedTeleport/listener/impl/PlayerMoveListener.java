package me.TeamsInsane.AdvancedTeleport.listener.impl;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.commands.impl.TpAccept;
import me.TeamsInsane.AdvancedTeleport.commands.impl.TpaCommand;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (TpaCommand.playerArrayList.contains(player)) {
            Location from = e.getFrom();
            if (from.getZ() != e.getTo().getZ() && from.getX() != e.getTo().getX()) {
                player.teleport(e.getFrom());
            }
        }
        if (TpAccept.playerArrayList.contains(player)){
            Location from = e.getFrom();
            if (from.getZ() != TpAccept.location.getZ() && from.getX() != TpAccept.location.getX()){
                TpAccept.playerArrayList.remove(player);
                player.sendMessage(Color.format(Core.configuration.getConfig().getString("move_msg")));
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                TpAccept.stringArrayList.add(player.getName());
                scheduler.scheduleSyncDelayedTask(Core.getInstance(), () -> TpAccept.stringArrayList.remove(player.getName()), 60);
            }
        }
    }
}
