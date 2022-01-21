package me.TeamsInsane.AdvancedTeleport.commands.impl;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TpToggle implements me.TeamsInsane.AdvancedTeleport.commands.Command {

    public static ArrayList<String> tpToggleList = new ArrayList<>();

    @Override
    public String getCommandName(){ return "tptoggle";}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!player.hasPermission(Core.configuration.getConfig().getString("tp_toggle_perm"))){
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("no_permission")));
            return false;
        }
        if (tpToggleList.contains(player.getName())){
            tpToggleList.remove(player.getName());
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("tp_toggle_on")));
        } else {
            tpToggleList.add(player.getName());
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("tp_toggle_off")));
        }
        Core.configuration.getConfig().set("tp_toggle_list", tpToggleList);
        Core.configuration.saveConfig();
        saveDataToFile(Core.getInstance());
        return false;
    }

    public void saveDataToFile(final Core plugin){
        try{
            final File file = new File(plugin.getDataFolder(), "config.yml");
            if (!file.exists()) file.createNewFile();
            final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set("safe_tp_toggle_list", tpToggleList);
            configuration.save(file);
        }catch (final IOException ex){
            ex.printStackTrace();
        }
    }
}
