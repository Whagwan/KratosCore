package me.kratoscore.net.bungee.listener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import me.kratoscore.net.api.commands.CommandAPI;
import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageReceive implements Listener {

    @EventHandler
    public void onMessageReceive(PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase("BungeeCord")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                String channel = in.readUTF();
                if (channel.equals("hasPermission")) {
                    String name = in.readUTF();
                    String command = in.readUTF();
                    String argline = in.readUTF();
                    String[] args = new String[] {};
                    if (argline.isEmpty()) {
                        args = new String[] {};
                    } else {
                        args = argline.split(" ");
                    }
                    boolean hasperm = in.readBoolean();

                    BungeeUser user = KratosCore.getInstance().getUser(name);
                    if (user == null) {
                        return;
                    }
                    if (!hasperm) {
                        user.sendMessage(KratosCore.getInstance().getConfig().getString("Prefix")
                                + KratosCore.getInstance().getConfig().getString("Main-messages.no-permission"));
                        return;
                    }
                    for (DBCommand cmd : CommandAPI.getInstance().commands) {
                        if (cmd.getName().toLowerCase().contains(command.toLowerCase())) {
                            cmd.onExecute(user, args);
                            return;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}