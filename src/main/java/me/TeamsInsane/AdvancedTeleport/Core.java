package me.TeamsInsane.AdvancedTeleport;

import me.TeamsInsane.AdvancedTeleport.commands.*;
import me.TeamsInsane.AdvancedTeleport.events.DamageEvent;
import me.TeamsInsane.AdvancedTeleport.events.MoveEvent;
import me.TeamsInsane.AdvancedTeleport.utils.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Core extends JavaPlugin {

    private static Core plugin;
    public static Configuration configuration;

    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("The advanced tp plugin has been enabled!");
        this.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MoveEvent(), this);

        Objects.requireNonNull(this.getCommand("tpa")).setExecutor(new TpaCommand());
        Objects.requireNonNull(this.getCommand("tpaccept")).setExecutor(new TpAccept());
        Objects.requireNonNull(this.getCommand("tpdeny")).setExecutor(new TpDeny());
        Objects.requireNonNull(this.getCommand("tpconfirm")).setExecutor(new TpConfirm());
        Objects.requireNonNull(this.getCommand("tpback")).setExecutor(new TpBack());

        configuration = new Configuration(this);
        configuration.saveConfig();
        configuration.reloadConfig();
    }
    public static Core getInstance(){
        return plugin;
    }
}
