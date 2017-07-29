package me.kratoscore.net.bungee.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PluginMessageChannel {

    public static void sendPermissionCheckPluginMessage(String channel, String permission, String command, String[] args, ProxiedPlayer p) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        try {
            out.writeUTF(channel);
            out.writeUTF(permission);
            out.writeUTF(command);
            String s = "";

            for(String arg : args){
                arg = arg + " ";
                s = s + arg;
            }

            out.writeUTF(s);
            out.writeUTF(p.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        p.getServer().getInfo().sendData("Return", stream.toByteArray());
    }

    public static void sendFriendLimitCheckPluginMessage(String channel, String[] args, ProxiedPlayer p){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        try {
            out.writeUTF(channel);
            String s = "";
            for(String arg : args){
                arg = arg + " ";
                s = s + arg;
            }
            out.writeUTF(s);
            out.writeUTF(p.getName());
        } catch (IOException e){
            e.printStackTrace();
        }

        p.getServer().getInfo().sendData("Return", stream.toByteArray());
    }
}
