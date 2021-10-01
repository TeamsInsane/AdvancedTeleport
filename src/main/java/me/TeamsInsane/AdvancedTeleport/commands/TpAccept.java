package me.TeamsInsane.AdvancedTeleport.commands;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.placeHolder.Message;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class TpAccept implements CommandExecutor {
    public static Location location;
    Message message = new Message();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        if (!(TpaCommand.playerHashMap.containsKey(player))){
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("no_requests")));
            return false;
        }
        Player target = TpaCommand.playerHashMap.get(player);
        location = target.getLocation();
        target.sendMessage(Color.format(message.applyPlaceholder(target, player, Core.configuration.getConfig().getString("tpaccept_player"))));
        player.sendMessage(Color.format(message.applyPlaceholder(target, player, Core.configuration.getConfig().getString("tpaccept_target"))));
        scheduler.scheduleSyncDelayedTask(Core.getInstance(), () -> {
            target.teleport(player.getLocation());
            TpaCommand.playerArrayList.add(TpaCommand.playerHashMap.get(player));
            TextComponent yes = new TextComponent("Za sprejem teleportacije klikna na to sporocilo ali pa napisi /tpconfirm");
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpconfirm")); yes.setColor(ChatColor.GREEN);
            TextComponent no = new TextComponent("Za zavrnitev teleportacije pa klikni na to sporocilo ali pa napisi /tpback.");
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpback")); no.setColor(ChatColor.RED);
            target.sendMessage(yes); target.sendMessage(no);
            TpaCommand.playerHashMap.remove(player, target);
            scheduler.scheduleSyncDelayedTask(Core.getInstance(), () -> {
                if (TpaCommand.playerArrayList.contains(player)) player.performCommand("tpback");
            }, 200);
        }, 60);
        return true;
    }
}