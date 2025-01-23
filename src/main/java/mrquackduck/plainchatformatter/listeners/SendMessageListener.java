package mrquackduck.plainchatformatter.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import mrquackduck.plainchatformatter.PlainChatFormatterPlugin;
import mrquackduck.plainchatformatter.utils.MessageColorizer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class SendMessageListener implements Listener, ChatRenderer {
    private final PlainChatFormatterPlugin plugin;

    public SendMessageListener(PlainChatFormatterPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMessageSent(AsyncChatEvent event) {
        event.renderer(this);
    }

    @Override
    public @NotNull Component render(@NotNull Player sender, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        String format = plugin.getConfig().getString("format");
        if (format == null) throw new NullPointerException("`format` cannot be null!");

        TextComponent formatComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(format);

        TextReplacementConfig prefixReplacementConfig = TextReplacementConfig.builder()
                .matchLiteral("[prefix]")
                .replacement(getPrefix(sender))
                .build();

        TextReplacementConfig nameReplacementConfig = TextReplacementConfig.builder()
                .matchLiteral("[name]")
                .replacement(sourceDisplayName)
                .build();

        TextReplacementConfig suffixReplacementConfig = TextReplacementConfig.builder()
                .matchLiteral("[suffix]")
                .replacement(getSuffix(sender))
                .build();

        TextReplacementConfig messageReplacementConfig = TextReplacementConfig.builder()
                .matchLiteral("[message]")
                .replacement(message)
                .build();

        return formatComponent
                .replaceText(prefixReplacementConfig)
                .replaceText(nameReplacementConfig)
                .replaceText(suffixReplacementConfig)
                .replaceText(messageReplacementConfig);
    }

    private String getPrefix(Player player) {
        if (plugin.getChat() == null) return "";
        return MessageColorizer.colorize(plugin.getChat().getPlayerPrefix(player));
    }

    private String getSuffix(Player player) {
        if (plugin.getChat() == null) return "";
        return MessageColorizer.colorize(plugin.getChat().getPlayerSuffix(player));
    }
}
