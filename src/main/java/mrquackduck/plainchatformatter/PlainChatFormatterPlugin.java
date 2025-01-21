package mrquackduck.plainchatformatter;

import mrquackduck.plainchatformatter.commands.PlainChatFormatterCommand;
import mrquackduck.plainchatformatter.listeners.SendMessageListener;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PlainChatFormatterPlugin extends JavaPlugin {
    private Chat chat;

    @Override
    public void onEnable() {
        // Setting up the config
        saveDefaultConfig();

        // Setting up the Vault chat
        setupChat();

        // Registering listeners
        getServer().getPluginManager().registerEvents(new SendMessageListener(this), this);

        // Registering commands
        Objects.requireNonNull(getServer().getPluginCommand("plainchatformatter")).setExecutor(new PlainChatFormatterCommand(this));
    }

    public void reload() {
        saveDefaultConfig();
        reloadConfig();
        setupChat();
    }

    public Chat getChat() {
        return chat;
    }

    private void setupChat() {
        try {
            chat = Objects.requireNonNull(getServer().getServicesManager().getRegistration(Chat.class)).getProvider();
        }
        catch (NoClassDefFoundError | NullPointerException e) {
            chat = null;
        }
    }
}
