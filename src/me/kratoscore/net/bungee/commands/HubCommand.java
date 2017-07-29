package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class HubCommand extends DBCommand {

    private static KratosCore instance = (KratosCore)KratosCore.getInstance();

    public HubCommand(String cmd) {
        super(cmd);
    }

    public HubCommand(String cmd, String[] aliases){
        super(cmd, aliases);
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        ServerInfo hub = ProxyServer.getInstance().getServerInfo(instance.getConfig().getString("Hub.Server"));
        ServerInfo current = user.getServer().getInfo();

        if(hub.getName().equals(current.getName())){
            user.sendMessage(instance.getConfig().getString("Hub.inHub"));
            return;
        }
        user.connect(hub);
        user.sendMessage(instance.getConfig().getString("Hub.Message"));
        return;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(Utils.format("The console cannot go to the hub!"));
        return;
    }
}