package me.TeamsInsane.AdvancedTeleport.commands;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.placeHolder.Message;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpDeny implements CommandExecutor {
    Message message = new Message();
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!(TpaCommand.playerHashMap.containsKey(player))) {
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("no_requests")));
            return false;
        }
        Player target = TpaCommand.playerHashMap.get(player);
        player.sendMessage(Color.format(message.applyPlaceholder(target, player, Core.configuration.getConfig().getString("tpdeny_target"))));
        target.sendMessage(Color.format(message.applyPlaceholder(target, player, Core.configuration.getConfig().getString("tpdeny_player"))));
        TpaCommand.playerArrayList.remove(player);
        TpaCommand.playerHashMap.remove(target, player);
        return true;
    }
}
