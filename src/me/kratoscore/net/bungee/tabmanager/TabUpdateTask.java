package me.kratoscore.net.bungee.tabmanager;

import java.util.ArrayList;
import java.util.Collection;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TabUpdateTask implements Runnable {

    public static ArrayList<String> headers = new ArrayList<String>();
    public static ArrayList<String> footers = new ArrayList<String>();
    String header;
    String footer;
    public static int headercount = 0;
    public static int footercount = 0;

    public void addHeader(String header) {
        headers.add(colorize(header));
    }

    public void addFooter(String footer) {
        footers.add(colorize(footer));
    }

    public void run() {
        if ((headers.isEmpty()) && (footers.isEmpty())) {
            return;
        }
        if (headers.isEmpty()) {
            headers.add("");
        }
        if (footers.isEmpty()) {
            footers.add("");
        }
        Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
        if (players.isEmpty()) {
            return;
        }
        for (ProxiedPlayer p : players) {
            if (p.getServer() == null) {
                String sheader = ((String) headers.get(headercount)).replace("%p%", p.getName())
                        .replace("%newline%", "\n").replaceAll("%nl%", "\n");
                String sfooter = ((String) footers.get(footercount)).replace("%p%", p.getName())
                        .replace("%newline%", "\n").replaceAll("%nl%", "\n");

                BaseComponent[] header = TextComponent.fromLegacyText(sheader);
                BaseComponent[] footer = TextComponent.fromLegacyText(sfooter);

                p.setTabHeader(header, footer);
            } else {
                String count = String.valueOf(ProxyServer.getInstance().getPlayers().size());

                String sheader = (headers.get(headercount)).replace("%newline%", "\n").replace("%p%", p.getName())
                        .replace("%globalonline%", count).replace("%server%", p.getServer().getInfo().getName());
                String sfooter = (footers.get(footercount)).replace("%newline%", "\n").replace("%p%", p.getName())
                        .replace("%globalonline%", count).replace("%server%", p.getServer().getInfo().getName());

                BaseComponent[] header = TextComponent.fromLegacyText(sheader);
                BaseComponent[] footer = TextComponent.fromLegacyText(sfooter);

                p.setTabHeader(header, footer);
            }
        }
        headercount += 1;
        footercount += 1;
        if (headercount + 1 > headers.size()) {
            headercount = 0;
        }
        if (footercount + 1 > footers.size()) {
            footercount = 0;
        }
    }

    public String colorize(String input) {
        return input.replace("&", "§");
    }
}
