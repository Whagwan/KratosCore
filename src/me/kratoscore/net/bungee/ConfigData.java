package me.kratoscore.net.bungee;

import net.md_5.bungee.config.Configuration;

public class ConfigData {

    public Boolean UUIDSTORAGE, BUKKITPERMISSIONS, UPDATECHECKER, MYSQL_ENABLED,GLIST_ENABLED,CLEARCHAT_ENABLED,CHATLOCK_COMMAND_ENABLED,
            RULES_ENABLED,VOTE_ENABLED,STORE_ENABLED,HUB_ENABLED;
    public String PREFIX;

    public ConfigData(){
        Configuration config = KratosCore.getInstance().getConfig();
        this.UUIDSTORAGE = config.getBoolean("UUID-Storage");
        this.BUKKITPERMISSIONS = config.getBoolean("Bukkit-Permissions");
        this.UPDATECHECKER = config.getBoolean("UpdateChecker");
        this.PREFIX = config.getString("Prefix");
        this.MYSQL_ENABLED = config.getBoolean("MySQL.Enabled");
        this.GLIST_ENABLED = config.getBoolean("GList.Enabled");
        this.CLEARCHAT_ENABLED = config.getBoolean("ClearChat.Enabled");
        this.CHATLOCK_COMMAND_ENABLED = config.getBoolean("ChatLock.ChatCommand.Enabled");
        this.RULES_ENABLED = config.getBoolean("Rules.Enabled");
        this.VOTE_ENABLED = config.getBoolean("Vote.Enabled");
        this.STORE_ENABLED = config.getBoolean("Store.Enabled");
        this.HUB_ENABLED = config.getBoolean("Hub.Enabled");
    }
}