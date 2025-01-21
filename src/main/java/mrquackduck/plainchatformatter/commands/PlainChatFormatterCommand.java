package mrquackduck.plainchatformatter.commands;

import mrquackduck.plainchatformatter.PlainChatFormatterPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlainChatFormatterCommand implements CommandExecutor, TabCompleter {
    private final PlainChatFormatterPlugin plugin;

    public PlainChatFormatterCommand(PlainChatFormatterPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            return true;
        }
        else if (args[0].equalsIgnoreCase("reload") && commandSender.hasPermission("plainchatformatter.admin")) {
            return new PlainChatFormatterReloadCommand(plugin).onCommand(commandSender, command, s, args);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        List<String> options = new ArrayList<>();
        List<String> completions = new ArrayList<>();

        if (commandSender.hasPermission("plainchatformatter.admin")) options.add("reload");

        StringUtil.copyPartialMatches(args[0], options, completions);
        return completions;
    }
}
