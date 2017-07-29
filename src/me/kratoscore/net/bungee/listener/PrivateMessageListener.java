package me.kratoscore.net.bungee.listener;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.PrivateMessageEvent;
import me.kratoscore.net.bungee.user.BungeeUser;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PrivateMessageListener implements Listener {

    @EventHandler
    public void onPrivateMessage(PrivateMessageEvent event) {
        String message = KratosCore.getInstance().getConfig().getString("PrivateMessages.Spy.Messages.Format")
                .replace("%sender%", event.getSender().getPlayer().getName())
                .replace("%receiver%", event.getReceiver().getPlayer().getName())
                .replace("%message%", event.getMessage())
                .replace("%server%", event.getSender().getPlayer().getServer().getInfo().getName());
        for (BungeeUser user : KratosCore.getInstance().users) {
            if (user.isSocialSpy()) {
                user.sendMessage(message);
            }
        }
    }

}