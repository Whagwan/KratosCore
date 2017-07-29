package me.kratoscore.net.bukkit;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class KratosCore extends JavaPlugin implements Plugin {

    public static KratosCore instance;
    public PluginChannelListener pcl;

    public void onEnable() {
        instance = this;
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "Return", pcl = new PluginChannelListener());

    }

    public static KratosCore getInstance() {
        return instance;
    }

}
