package me.TeamsInsane.AdvancedTeleport;

import me.TeamsInsane.AdvancedTeleport.commands.CommandRegisterable;
import me.TeamsInsane.AdvancedTeleport.commands.impl.SafeTpToggle;
import me.TeamsInsane.AdvancedTeleport.commands.impl.TpToggle;
import me.TeamsInsane.AdvancedTeleport.listener.ListenerRegisterable;
import me.TeamsInsane.AdvancedTeleport.registry.Registerable;
import me.TeamsInsane.AdvancedTeleport.utils.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class Core extends JavaPlugin {

    private static final Set<Registerable> REGISTERABLES = Set.of(
        new CommandRegisterable(), new ListenerRegisterable()
    );

    private static Core plugin;
    public static Configuration configuration;

    @Override
    public void onEnable() {
        configuration = new Configuration(this);
        configuration.saveConfig();
        configuration.reloadConfig();

        getLogger().info("Advanced Teleport plugin has been enabled!");
        plugin = this;

        SafeTpToggle.playerList.addAll(configuration.getConfig().getStringList("safe_tp_toggle_list"));
        TpToggle.tpToggleList.addAll(configuration.getConfig().getStringList("tp_toggle_list"));
        REGISTERABLES.forEach(it -> it.register(this));
    }

    public static Core getInstance(){
        return plugin;
    }
}

