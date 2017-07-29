package me.kratoscore.net.bungee.listener;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.CommandEvent;
import me.kratoscore.net.bungee.user.BungeeUser;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(CommandEvent event) {
        BungeeUser user = event.getPlayer();
        if (user == null) {
            return;
        }
        ProxiedPlayer player = user.getPlayer();

        for (BungeeUser u : KratosCore.getInstance().users) {
            if (u.isLocalSpy()) {
                u.sendMessage(KratosCore.getInstance().getConfig().getString("LocalSpy.Messages.Format")
                        .replace("%player%", player.getName()).replace("%command%", event.getCommand()));
            }
        }
    }

}