package me.kratoscore.net.bungee.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TitleUtil {

    public static void broadcastTitle(Integer fadeIn, Integer stay, Integer fadeOut, BaseComponent[] text) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            sendTitle(p, fadeIn, stay, fadeOut, text);
        }
    }

    public static void broadcastSubTitle(Integer fadeIn, Integer stay, Integer fadeOut, BaseComponent[] text) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            playTitle(p, fadeIn, stay, fadeOut, null, text);
        }
    }

    public static void sendAllFullTitle(Integer fadeIn, Integer stay, Integer fadeOut, BaseComponent[] title, BaseComponent[] subtitle) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            playTitle(p, fadeIn, stay, fadeOut, title, subtitle);
        }
    }

    public static void sendTitle(ProxiedPlayer player, Integer fadeIn, Integer stay, Integer fadeOut, BaseComponent[] text) {
        playTitle(player, fadeIn, stay, fadeOut, text, null);
    }

    public static void sendSubTitle(ProxiedPlayer player, Integer fadeIn, Integer stay, Integer fadeOut, BaseComponent[] text) {
        playTitle(player, fadeIn, stay, fadeOut, null, text);
    }

    public static void sendFullTitle(ProxiedPlayer player, Integer fadeIn, Integer stay, Integer fadeOut, BaseComponent[] title, BaseComponent[] subtitle) {
        playTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    private static void playTitle(ProxiedPlayer player, Integer fadeIn, Integer stay, Integer fadeOut, BaseComponent[] title, BaseComponent[] subtitle) {
        Title tit = ProxyServer.getInstance().createTitle();
        if (subtitle != null) {
            tit.title(title);
        }
        if (subtitle != null) {
            tit.subTitle(subtitle);
        }
        tit.fadeIn(fadeIn);
        tit.stay(stay);
        tit.fadeOut(fadeOut);
        tit.send(player);
    }
}