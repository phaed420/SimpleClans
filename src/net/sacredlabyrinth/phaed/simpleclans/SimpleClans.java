package net.sacredlabyrinth.phaed.simpleclans;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sacredlabyrinth.phaed.simpleclans.commands.ClanCommand;
import net.sacredlabyrinth.phaed.simpleclans.listeners.SCEntityListener;
import net.sacredlabyrinth.phaed.simpleclans.listeners.SCPlayerListener;
import net.sacredlabyrinth.phaed.simpleclans.listeners.SCServerListener;
import net.sacredlabyrinth.phaed.simpleclans.managers.DeathManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.PermissionsManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.RequestManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.SettingsManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.SpoutPluginManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.StorageManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.CommandManager;
import net.sacredlabyrinth.register.payment.Method;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Phaed
 */
public class SimpleClans extends JavaPlugin
{
    private static SimpleClans instance;
    private static Logger logger = Logger.getLogger("Minecraft");
    private Method Method;
    private ClanManager clanManager;
    private RequestManager requestManager;
    private DeathManager deathManager;
    private StorageManager storageManager;
    private SpoutPluginManager spoutPluginManager;
    private SettingsManager settingsManager;
    private PermissionsManager permissionsManager;
    private CommandManager commandManager;
    private SCPlayerListener playerListener;
    private SCEntityListener entityListener;
    private SCServerListener serverListener;

    /**
     * @return the logger
     */
    public static Logger getLogger()
    {
        return logger;
    }

    /**
     * @return the instance
     */
    public static SimpleClans getInstance()
    {
        return instance;
    }

    /**
     * Parametrized logger
     * @param level the level
     * @param msg the message
     * @param arg the arguments
     */
    public static void log(Level level, String msg, Object... arg)
    {
        getLogger().log(level, new StringBuilder().append("[SimpleClans] ").append(MessageFormat.format(msg, arg)).toString());
    }

    public void onEnable()
    {
        logger.info("[" + getDescription().getName() + "] version " + getDescription().getVersion() + " loaded");

        instance = this;
        spoutPluginManager = new SpoutPluginManager();
        settingsManager = new SettingsManager();
        permissionsManager = new PermissionsManager();
        requestManager = new RequestManager();
        clanManager = new ClanManager();
        deathManager = new DeathManager();
        storageManager = new StorageManager();
        commandManager = new CommandManager();

        playerListener = new SCPlayerListener();
        entityListener = new SCEntityListener();
        serverListener = new SCServerListener();

        registerEvents();
        registerCommands();

        spoutPluginManager.processAllPlayers();
    }

    private void registerEvents()
    {
        getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Low, this);
        getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Low, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Lowest, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Lowest, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_ENABLE, serverListener, Priority.Monitor, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_DISABLE, serverListener, Priority.Monitor, this);
    }

    private void registerCommands()
    {
        getCommand("clan").setExecutor(new ClanCommand());
    }

    public void onDisable()
    {
        getServer().getScheduler().cancelTasks(this);
        getStorageManager().closeConnection();
    }

    /**
     * @param Method the Method to set
     */
    public void setMethod(Method Method)
    {
        this.Method = Method;
    }

    /**
     * @return the Method
     */
    public Method getMethod()
    {
        return Method;
    }

    /**
     * @return the clanManager
     */
    public ClanManager getClanManager()
    {
        return clanManager;
    }

    /**
     * @return the requestManager
     */
    public RequestManager getRequestManager()
    {
        return requestManager;
    }

    /**
     * @return the deathManager
     */
    public DeathManager getDeathManager()
    {
        return deathManager;
    }

    /**
     * @return the storageManager
     */
    public StorageManager getStorageManager()
    {
        return storageManager;
    }

    /**
     * @return the spoutManager
     */
    public SpoutPluginManager getSpoutPluginManager()
    {
        return spoutPluginManager;
    }

    /**
     * @return the settingsManager
     */
    public SettingsManager getSettingsManager()
    {
        return settingsManager;
    }

    /**
     * @return the permissionsManager
     */
    public PermissionsManager getPermissionsManager()
    {
        return permissionsManager;
    }

    /**
     * @return the commandManager
     */
    public CommandManager getCommandManager()
    {
        return commandManager;
    }
}