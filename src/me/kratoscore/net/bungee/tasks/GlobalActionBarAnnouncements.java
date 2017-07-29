package me.kratoscore.net.bungee.tasks;

import java.util.ArrayList;
import java.util.Collection;

import me.kratoscore.net.bungee.utils.ActionBarUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GlobalActionBarAnnouncements implements Runnable {

    ArrayList<String> list = new ArrayList<String>();
    int count = 0;

    public void addAnnouncement(String message) {
        list.add(colorize(message));
    }

    public void run() {
        Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
        if (players.isEmpty()) {
            return;
        }

        for (ProxiedPlayer player : players) {
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
