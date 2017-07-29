package me.kratoscore.net.bungee.tabmanager;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer p = event.getPlayer();

        String count = String.valueOf(ProxyServer.getInstance().getPlayers().size());

        String sheader = (TabUpdateTask.headers.get(TabUpdateTask.headercount)).replace("%newline%", "\n")
                .replace("%p%", p.getName()).replace("%globalonline%", count).replace("%server%", "")
                .replaceAll("%nl%", "\n");
        String sfooter = (TabUpdateTask.footers.get(TabUpdateTask.footercount)).replace("%newline%", "\n")
                .replace("%p%", p.getName()).replace("%globalonline%", count).replace("%server%", "")
                .replaceAll("%nl%", "\n");

        BaseComponent[] header = TextComponent.fromLegacyText(sheader);
        BaseComponent[] footer = TextComponent.fromLegacyText(sfooter);

        p.setTabHeader(header, footer);
    }
}