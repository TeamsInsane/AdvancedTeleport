package me.TeamsInsane.AdvancedTeleport.commands.impl;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SafeTpToggle implements me.TeamsInsane.AdvancedTeleport.commands.Command {
    public static List<String> playerList = new ArrayList<>();

    @Override
    public String getCommandName() {return "safetptoggle";}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (playerList.contains(player.getName())){
            playerList.remove(player.getName());
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("safe_tp_toggle_on")));
        }
        else{
            playerList.add(player.getName());
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("safe_tp_toggle_off")));
        }
        Core.configuration.getConfig().set("safe_tp_toggle_list", playerList);
        Core.configuration.saveConfig();
        saveDataToFile(Core.getInstance());
        return true;
    }

    public void saveDataToFile(final Core plugin){
        try{
            final File file = new File(plugin.getDataFolder(), "config.yml");
            if (!file.exists()) file.createNewFile();
            final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set("tp_toggle_list", playerList);
            configuration.save(file);
        }catch (final IOException ex){
            ex.printStackTrace();
        }

    }
}
