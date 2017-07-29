package me.kratoscore.net.bungee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.kratoscore.net.api.MySQL;
import me.kratoscore.net.api.commands.CommandAPI;
import me.kratoscore.net.bungee.actionbarannouncer.ActionBarAnnouncer;
import me.kratoscore.net.bungee.announcer.Announcer;
import me.kratoscore.net.bungee.commands.*;
import me.kratoscore.net.bungee.listener.*;
import me.kratoscore.net.bungee.managers.DatabaseManager;
import me.kratoscore.net.bungee.party.Party;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.staffchat.StaffChat;
import me.kratoscore.net.bungee.tabmanager.TabManager;
import me.kratoscore.net.bungee.titleannouncer.TitleAnnouncer;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.TPSRunnable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class KratosCore extends Plugin {

    @Getter @Setter(AccessLevel.PRIVATE)
    private static KratosCore instance;
    @Getter @Setter(AccessLevel.PRIVATE)
    private DatabaseManager databaseManager;
    public boolean chatMuted = false;
    public boolean update;
    private File configfile;
    @Getter private Configuration config;
    @Getter private ConfigData configData;
    @Getter private HashMap<String, String> pmcache = Maps.newHashMap();
    public List<BungeeUser> users = Lists.newArrayList();
    public List<String> staff = Lists.newArrayList();

    public void onEnable() {
        setInstance(this);


        this.configfile = this.loadResource(this, "config.yml");
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.configfile);
            this.saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.configData = new ConfigData();

        loadCommands();
        registerEvents();
        Announcer.loadAnnouncements();
        TitleAnnouncer.loadAnnouncements();
        ActionBarAnnouncer.loadAnnouncements();
        StaffChat.registerStaffChat();
        Party.registerPartySystem();
        TabManager.loadTab();

        ProxyServer.getInstance().getScheduler().schedule(this, new TPSRunnable(), 50, TimeUnit.MILLISECONDS);



        if (configData.MYSQL_ENABLED) {
            String host = config.getString("MySQL.Host", "localhost");
            int port = config.getInt("MySQL.Port", 3306);
            String database = config.getString("MySQL.Database", "database");
            String username = config.getString("MySQL.Username", "username");
            String password = config.getString("MySQL.Password", "password");
            databaseManager = new DatabaseManager(host, port, database, username, password);
            databaseManager.openConnection();

            ProxyServer.getInstance().getScheduler().schedule(KratosCore.getInstance(), new Runnable() {
                @Override
                public void run() {
                    databaseManager.closeConnection();
                    databaseManager.openConnection();
                }
            }, 5, 5, TimeUnit.MINUTES);

            Punishments.registerPunishmentSystem();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        staff.clear();
                        ResultSet rs = MySQL.getInstance().select().table("Staffs").column("*").select();

                        while (rs.next()) {
                            staff.add(rs.getString("Name").toLowerCase());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            BungeeCord.getInstance().getScheduler().schedule(this, r, 0, 1, TimeUnit.MINUTES);
        }
    }

    public List<BungeeUser> getStaff() {
        List<BungeeUser> list = Lists.newArrayList();

        for (BungeeUser user : Lists.newArrayList(users)) {
            if (user.isStaff()) {
                list.add(user);
            }
        }

        return list;
    }

    public BungeeUser getUser(ProxiedPlayer p) {
        List<BungeeUser> users = Lists.newArrayList(this.users);
        for (BungeeUser user : users) {
            if (user.getPlayer().equals(p)) {
                return user;
            }
        }
        return null;
    }

    public BungeeUser getUser(String name) {
        List<BungeeUser> users = Lists.newArrayList(this.users);
        for (BungeeUser user : users) {
            if (user.getPlayer().getName().toLowerCase().equals(name.toLowerCase())) {
                return user;
            }
        }
        return null;
    }

    public List<BungeeUser> getUsers() {
        return Lists.newArrayList(users);
    }

    public boolean saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configfile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean reloadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.configfile);
            this.configData = new ConfigData();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loadCommands() {
        CommandAPI.getInstance().registerCommand(new AlertCommand());
        CommandAPI.getInstance().registerCommand(new BgcCommand());
        CommandAPI.getInstance().registerCommand(new BigalertCommand());
        CommandAPI.getInstance().registerCommand(new KutilisalsCommand());
        if (configData.CHATLOCK_COMMAND_ENABLED) {
            CommandAPI.getInstance().registerCommand(new ChatCommand());
        }
        if (configData.CLEARCHAT_ENABLED) {
            CommandAPI.getInstance().registerCommand(new ClearChatCommand());
        }
        CommandAPI.getInstance().registerCommand(new FindCommand());
        if (configData.GLIST_ENABLED) {
            CommandAPI.getInstance().registerCommand(new GlistCommand());
        }
        if (configData.MYSQL_ENABLED) {
            CommandAPI.getInstance().registerCommand(new GRankCommand());
        }
        if (configData.HUB_ENABLED) {
            String cmd = getConfig().getString("Hub.Command");
            if (cmd.contains(";")) {
                String[] cmds = cmd.split(";");

                CommandAPI.getInstance()
                        .registerCommand(new HubCommand(cmds[0], Arrays.copyOfRange(cmds, 1, cmds.length)));
            } else {
                CommandAPI.getInstance().registerCommand(new HubCommand(cmd));
            }
        }
        CommandAPI.getInstance().registerCommand(new LocalSpyCommand());
        CommandAPI.getInstance().registerCommand(new MSGCommand());
        CommandAPI.getInstance().registerCommand(new ReplyCommand());
        CommandAPI.getInstance().registerCommand(new ServerCommand());
        CommandAPI.getInstance().registerCommand(new SpyCommand());
        if (getConfig().getBoolean("Vote.Enabled")) {
            CommandAPI.getInstance().registerCommand(new VoteCommand());
        }
        if (getConfig().getBoolean("Store.Enabled")) {
            CommandAPI.getInstance().registerCommand(new StoreCommand());
        }
        if (getConfig().getBoolean("Rules.Enabled")) {
            CommandAPI.getInstance().registerCommand(new RulesCommand());
        }
    }

    private void registerEvents() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MessageLimiter());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new DisconnectEvent());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new LoginEvent());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiSpam());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatLock());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatUtilities());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiSwear());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiCaps());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiAd());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PluginMessageReceive());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PrivateMessageListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new CommandListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatListener());
    }

    public void onDisable() {
        databaseManager.closeConnection();
        ProxyServer.getInstance().getLogger().info("KratosCore is now Disabled!");
    }

    public File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResourceAsStream(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }
}