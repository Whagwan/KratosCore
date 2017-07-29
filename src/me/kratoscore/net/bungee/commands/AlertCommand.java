package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class AlertCommand extends DBCommand {

    public AlertCommand() {
        super("alert");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        String prefix = Utils.c(KratosCore.getInstance().getConfig().getString("Prefix"));
        if (args.length >= 1) {
            ProxyServer.getInstance().broadcast(Utils.format(prefix + Joiner.on(" ").join(args)));
        } else {
            sender.sendMessage(Utils.format("&cPlease enter a message!"));
        }
    }
}