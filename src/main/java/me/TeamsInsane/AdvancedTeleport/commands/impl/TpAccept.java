package me.TeamsInsane.AdvancedTeleport.commands.impl;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.placeHolder.Message;
import me.TeamsInsane.AdvancedTeleport.utils.Color;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class TpAccept implements me.TeamsInsane.AdvancedTeleport.commands.Command {
    public static Location location;
    Message message = new Message();
    public static ArrayList<String> stringArrayList = new ArrayList<>();
    public static ArrayList<Player> playerArrayList = new ArrayList<>();

    @Override
    public String getCommandName() { return "tpaccept"; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        if (!(TpaCommand.playerHashMap.containsKey(player))){
            player.sendMessage(Color.format(Core.configuration.getConfig().getString("no_requests")));
            return false;
        }
        player.sendMessage(Color.format(Core.configuration.getConfig().getString("tpaccept_target")));
        Player target = TpaCommand.playerHashMap.get(player);
        location = target.getLocation();
        int timer = 60;
        if (target.hasPermission(Core.configuration.getConfig().getString("skip_wait_perm"))){
            timer = 0;
            target.sendMessage(Color.format(message.applyPlaceholder(target, player, Objects.requireNonNull(Core.configuration.getConfig().getString("tpaccept_player_with_perm")))));
        } else target.sendMessage(Color.format(message.applyPlaceholder(target, player, Objects.requireNonNull(Core.configuration.getConfig().getString("tpaccept_player_without_perm")))));
        playerArrayList.add(target);
        scheduler.scheduleSyncDelayedTask(Core.getInstance(), () -> {
            if (!playerArrayList.contains(target)){
                return;
            }
            playerArrayList.remove(target);
            target.sendMessage(Color.format(message.applyPlaceholder(target, player, Objects.requireNonNull(Core.configuration.getConfig().getString("tpaccept_player")))));
            target.teleport(player.getLocation());
            TpaCommand.playerArrayList.add(TpaCommand.playerHashMap.get(player));
            if (!TpToggle.playerList.contains(target.getName())) {
                TextComponent yes = new TextComponent();
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpconfirm"));
                yes.setText(Color.format(Core.configuration.getConfig().getString("tpconfirm_message")));
                yes.setColor(ChatColor.valueOf(Core.configuration.getConfig().getString("tpconfirm_message_color")));
                TextComponent no = new TextComponent();no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpback"));
                no.setText(Color.format(Core.configuration.getConfig().getString("tpback_message")));
                no.setColor(ChatColor.valueOf(Core.configuration.getConfig().getString("tpback_message_color")));
                target.sendMessage(yes);
                target.sendMessage(no);
                TpaCommand.playerHashMap.remove(player, target);
                stringArrayList.add(target.getName());
                scheduler.scheduleSyncDelayedTask(Core.getInstance(), () -> {
                    if (TpaCommand.playerArrayList.contains(player)) player.performCommand("tpback");
                }, 200);
            }else {
                TpaCommand.playerHashMap.remove(player, target);
                stringArrayList.add(target.getName());
                target.performCommand("tpconfirm");
            }
        }, timer);
        return true;
    }
}