package me.TeamsInsane.AdvancedTeleport.placeholder;

import org.bukkit.entity.Player;

public class Message {
    public String applyPlaceholder(Player player, Player target, String text){
        if (text.contains("%player%")) text = text.replace("%player%", player.getName());
        if (text.contains("%target%")) text = text.replace("%target%", target.getName());
        return text;
    }
}
