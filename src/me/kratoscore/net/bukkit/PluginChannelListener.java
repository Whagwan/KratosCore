package me.kratoscore.net.bukkit;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginChannelListener implements PluginMessageListener {

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            String subchannel = in.readUTF();
            if (subchannel.equals("hasPermission")) {
                String perm = in.readUTF();
                String command = in.readUTF();
                String args = in.readUTF();
                String name = in.readUTF();
                Player p = Bukkit.getPlayer(name);
                if (p == null) {
                    return;
                }
                if (p.hasPermission(perm) || p.hasPermission("kratoscore.*")) {
                    sendMessageBack(player, name, command, args, true);
                } else {
                    sendMessageBack(player, name, command, args, false);
                }
                return;
            }
            if (subchannel.equals("getLimit")) {
                String args = in.readUTF();
                String name = in.readUTF();
                Player p = Bukkit.getPlayer(name);
                if (p == null) {
                    return;
                }
                int limit = 0;
                for (PermissionAttachmentInfo s : p.getEffectivePermissions()) {
                    String perm = s.getPermission();
                    if (perm.contains("kratoscore.friends.amount")) {
                        limit = (perm == "kratoscore.friends.amount.*" ? 1000000 : Integer.valueOf(perm.replace("kratoscore.friends.amount.", "")));
                    }
                }

                sendFriendMessageBack(player, name, args, limit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageBack(Player p, String name, String command, String args, boolean hasPerm) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("hasPermission");
            out.writeUTF(name);
            out.writeUTF(command);
            out.writeUTF(args);
            out.writeBoolean(hasPerm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendPluginMessage(KratosCore.getInstance(), "BungeeCord", b.toByteArray());
    }

    public void sendFriendMessageBack(Player p, String name, String args, int limit) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("getLimit");
            out.writeUTF(name);
            out.writeUTF(args);
            out.writeInt(limit);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendPluginMessage(KratosCore.getInstance(), "BungeeCord", b.toByteArray());
    }
}
