package me.TeamsInsane.AdvancedTeleport.commands.impl;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.commands.impl.TpaCommand;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class TpConfirm implements me.TeamsInsane.AdvancedTeleport.commands.Command {

    @Override
    public String getCommandName() {
        return "tpconfirm";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!TpaCommand.playerArrayList.contains(player)){
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("no_permission")));
            return false;
        }
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        TpaCommand.playerArrayList.remove(player);
        scheduler.scheduleSyncDelayedTask(Core.getInstance(), () -> TpAccept.stringArrayList.remove(player.getName()), 200);
        return true;
    }
}
