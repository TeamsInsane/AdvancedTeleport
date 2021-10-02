package me.TeamsInsane.AdvancedTeleport.commands.impl;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.placeHolder.Message;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;

public class TpaCommand implements me.TeamsInsane.AdvancedTeleport.commands.Command {
    public static final ArrayList<Player> playerArrayList = new ArrayList<>();
    public static final HashMap <Player, Player> playerHashMap = new HashMap<>();

    @Override
    public String getCommandName() {
        return "tpa";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Message message = new Message();
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0){
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("tpa_without_args")));
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("player_not_found")));
            return false;
        }
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        player.sendMessage(Color.format(message.applyPlaceholder(player, target, Core.configuration.getConfig().getString("tpa_request_player"))));
        target.sendMessage(Color.format(message.applyPlaceholder(player, target, Core.configuration.getConfig().getString("tpa_request_target"))));
        playerHashMap.put(target, player);
        scheduler.scheduleSyncDelayedTask(Core.getInstance(), () -> {
            if (playerArrayList.contains(player)) playerArrayList.remove(player);
            if (playerHashMap.containsKey(target)) playerHashMap.remove(target, player);
        }, 2400);
        return true;
    }
}