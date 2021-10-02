package me.TeamsInsane.AdvancedTeleport.commands;

import me.TeamsInsane.AdvancedTeleport.Core;
import me.TeamsInsane.AdvancedTeleport.commands.impl.*;
import me.TeamsInsane.AdvancedTeleport.registry.Registerable;
import org.bukkit.command.CommandExecutor;

import java.util.Set;

public final class CommandRegisterable implements Registerable {

    private static final Set<Command> COMMANDS = Set.of(
        new TpAccept(), new TpaCommand(), new TpBack(), new TpConfirm(), new TpDeny()
    );

    @Override
    public void register(final Core core) {
        COMMANDS.forEach(it -> core.getCommand(it.getCommandName()).setExecutor(it));
    }

}
