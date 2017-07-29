package me.kratoscore.net.bungee.tasks;

import java.util.ArrayList;
import java.util.Collection;

import me.kratoscore.net.bungee.titleannouncer.TitleAnnouncements;
import me.kratoscore.net.bungee.utils.TitleUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GlobalTitleAnnouncements implements Runnable {

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

        int fadeIn = TitleAnnouncements.titleannouncements.getFile().getInt("Announcements.Global.FadeIn", 30);
        int stay = TitleAnnouncements.titleannouncements.getFile().getInt("Announcements.Global.Stay", 60);
        int fadeOut = TitleAnnouncements.titleannouncements.getFile().getInt("Announcements.Global.FadeOut", 30);

        for (ProxiedPlayer player : players) {
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
