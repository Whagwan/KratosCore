package me.kratoscore.net.bungee.staffchat;

import java.util.ArrayList;
import java.util.List;

import me.kratoscore.net.api.commands.CommandAPI;
import me.kratoscore.net.bungee.KratosCore;

import net.md_5.bungee.api.ProxyServer;

public class StaffChat {

    public static List<String> inchat = new ArrayList<String>();

    public static void registerStaffChat() {
        if (KratosCore.getInstance().getConfig().getBoolean("StaffChat.Enabled")) {
            CommandAPI.getInstance().registerCommand(new StaffChatCommand());
            ProxyServer.getInstance().getPluginManager().registerListener(KratosCore.getInstance(), new StaffChatEvent());
        }
    }
}
