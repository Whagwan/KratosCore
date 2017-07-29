package me.kratoscore.net.bungee.listener;


import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;

public class LoginEvent implements Listener {

    public void onPlayerJoin(ServerConnectEvent event) {
        ProxiedPlayer p = event.getPlayer();
    }}