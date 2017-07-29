package me.kratoscore.net.bungee.tasks;

import java.util.ArrayList;

import me.kratoscore.net.bungee.utils.ActionBarUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerActionBarAnnouncements implements Runnable {

    ArrayList<String> list = new ArrayList<String>();
    int count = 0;
    ServerInfo server;

    public ServerActionBarAnnouncements(ServerInfo server) {
        this.server = server;
    }

    public void addAnnouncement(String message) {
        list.add(colorize(message));
    }

    public void run() {
        if (list.isEmpty()) {
            return;
        }
        if (server.getPlayers().isEmpty()) {
            return;
        }
        for (ProxiedPlayer player : server.getPlayers()) {
            for (String line : list.get(count).split("\n")) {
                ActionBarUtil.sendActionBar(player, line.replace("%p%", player.getName()));
            }
        }
        count++;
        if ((count + 1) > list.size()) {
            count = 0;
        }
    }

    public String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}