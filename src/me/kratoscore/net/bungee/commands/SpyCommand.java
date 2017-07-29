package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.CommandSender;

public class SpyCommand extends DBCommand {

    public SpyCommand() {
        super("gspy", KratosCore.getInstance().getConfig().getStringList("PrivateMessages.Spy.Aliases"));
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        if (args.length > 0) {
            user.sendMessage(
                    KratosCore.getInstance().getConfig().getString("PrivateMessages.Spy.Messages.WrongUsage"));
            return;
        }
        if (user.isSocialSpy()) {
            user.setSocialSpy(false);
            user.sendMessage(
                    KratosCore.getInstance().getConfig().getString("PrivateMessages.Spy.Messages.Disabled"));
        } else {
            user.setSocialSpy(true);
            user.sendMessage(
                    KratosCore.getInstance().getConfig().getString("PrivateMessages.Spy.Messages.Enabled"));
        }
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(Utils.format("&6Only players can use this command!"));
    }
}