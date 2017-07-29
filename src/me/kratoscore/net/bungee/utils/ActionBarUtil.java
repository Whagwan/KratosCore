package me.kratoscore.net.bungee.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

import com.google.gson.JsonObject;

public class ActionBarUtil {

    public static void sendActionBar(ProxiedPlayer player, String message){
        if (player.getPendingConnection().getVersion() >= 47){
            JsonObject object = new JsonObject();
            String b = message;
            object.addProperty("text", b);
            String ob = object.toString();
            player.unsafe().sendPacket(new Chat(ob, (byte)2));
        }
    }

}