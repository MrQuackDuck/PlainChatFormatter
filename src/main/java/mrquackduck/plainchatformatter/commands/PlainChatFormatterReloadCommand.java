package mrquackduck.plainchatformatter.commands;

import mrquackduck.plainchatformatter.PlainChatFormatterPlugin;
import mrquackduck.plainchatformatter.utils.MessageColorizer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PlainChatFormatterReloadCommand implements CommandExecutor {
    private final PlainChatFormatterPlugin plugin;

    public PlainChatFormatterReloadCommand(PlainChatFormatterPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        plugin.reload();
        commandSender.sendMessage(MessageColorizer.colorize("&aReloaded successfully!"));

        return false;
    }
}
