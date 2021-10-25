package me.TeamsInsane.AdvancedTeleport.listener;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.listener.impl.EntityDamageListener;
import me.TeamsInsane.AdvancedTeleport.listener.impl.PlayerMoveListener;
import me.TeamsInsane.AdvancedTeleport.registry.Registerable;
import org.bukkit.event.Listener;

import java.util.Set;

public final class ListenerRegisterable implements Registerable {

    private static final Set<Listener> LISTENERS = Set.of(
        new EntityDamageListener(), new PlayerMoveListener()
    );

    @Override
    public void register(final Core core) {
        LISTENERS.forEach(it -> core.getServer().getPluginManager().registerEvents(it, core));
    }
}
