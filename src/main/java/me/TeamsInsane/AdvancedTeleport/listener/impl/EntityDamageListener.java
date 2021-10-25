package me.TeamsInsane.AdvancedTeleport.listener.impl;

import me.TeamsInsane.AdvancedTeleport.commands.impl.TpaCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (TpaCommand.playerArrayList.contains(player)) e.setCancelled(true);
        }
    }
}
