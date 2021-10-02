package me.TeamsInsane.AdvancedTeleport;

import me.TeamsInsane.AdvancedTeleport.commands.CommandRegisterable;
import me.TeamsInsane.AdvancedTeleport.commands.impl.*;
import me.TeamsInsane.AdvancedTeleport.events.DamageEvent;
import me.TeamsInsane.AdvancedTeleport.events.MoveEvent;
import me.TeamsInsane.AdvancedTeleport.registry.Registerable;
import me.TeamsInsane.AdvancedTeleport.utils.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Set;

public final class Core extends JavaPlugin {

    private static final Set<Registerable> REGISTERABLES = Set.of(
        new CommandRegisterable()
    );

    private static Core plugin;
    public static Configuration configuration;

    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("The advanced tp plugin has been enabled!");
        this.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MoveEvent(), this);

        REGISTERABLES.forEach(it -> it.register(this));

        configuration = new Configuration(this);
        configuration.saveConfig();
        configuration.reloadConfig();
    }
    public static Core getInstance(){
        return plugin;
    }
}
