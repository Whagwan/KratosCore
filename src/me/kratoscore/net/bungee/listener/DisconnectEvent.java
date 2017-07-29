package me.kratoscore.net.bungee.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        ProxiedPlayer p = event.getPlayer();
        if (AntiSpam.norepeat.containsKey(p)) {
            AntiSpam.norepeat.remove(p);
        }
        if (AntiSpam.chatspam.contains(p.getName())) {
            AntiSpam.chatspam.remove(p.getName());
        }
        if (MessageLimiter.messagelimiter.containsKey(p)) {
            MessageLimiter.messagelimiter.remove(p);
        }
    }
}