package me.kratoscore.net.bungee.tasks;

import java.util.ArrayList;

import me.kratoscore.net.bungee.utils.TitleUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerTitleAnnouncements implements Runnable {

    ArrayList<String> list = new ArrayList<String>();
    int count = 0;
    ServerInfo server;
    int fadeIn;
    int stay;
    int fadeOut;

    public ServerTitleAnnouncements(ServerInfo server, int fadeIn, int Stay, int fadeOut) {
        this.server = server;
        this.fadeIn = fadeIn;
        this.stay = Stay;
        this.fadeOut = fadeOut;
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
                if (line.contains("%n")) {
                    String[] titles = line.split("%n");
                    String stitle = titles[0];
                    String ssubtitle = titles[1];

                    BaseComponent[] title = new ComponentBuilder(stitle.replace("%p%", player.getName())).create();
                    BaseComponent[] subtitle = new ComponentBuilder(ssubtitle.replace("%p%", player.getName()))
                            .create();

                    TitleUtil.sendFullTitle(player, fadeIn, stay, fadeOut, subtitle, title);
                } else {
                    BaseComponent[] title = new ComponentBuilder(line.replace("%p%", player.getName())).create();
                    BaseComponent[] subtitle = new ComponentBuilder("").create();

                    TitleUtil.sendFullTitle(player, fadeIn, stay, fadeOut, subtitle, title);
                }
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