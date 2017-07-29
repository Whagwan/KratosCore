package me.kratoscore.net.bungee.listener;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatLock implements Listener {

    @EventHandler
    public void Chatlock(ChatEvent event) {
        ProxiedPlayer p = (ProxiedPlayer) event.getSender();
        if (isChatMuted()) {
            if (p.hasPermission("kratoscore.talkinlock") || p.hasPermission("kratoscore.*")) {
                return;
            }
            if (!event.getMessage().startsWith("/")) {
                p.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("ChatLock.Locked")));
                event.setCancelled(true);
            }
        }
    }

    private boolean isChatMuted() {
        return KratosCore.getInstance().chatMuted;
    }
}
