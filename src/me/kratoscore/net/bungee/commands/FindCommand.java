package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class FindCommand extends DBCommand {

    public FindCommand() {
        super("find");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        this.onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length != 1){
            sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("Main-messages.use-find")));
        }
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if(target == null){
            sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("Main-messages.offline-player")));
        } else {
            String server = target.getServer().getInfo().getName();
            sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("Main-messages.find-message").replace("%server%", server).replace("%player%", sender.getName()).replace("%target%", target.getName())));
        }
    }
}