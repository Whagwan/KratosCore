package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.CommandSender;

public class LocalSpyCommand extends DBCommand {

    public LocalSpyCommand() {
        super("localspy", KratosCore.getInstance().getConfig().getStringList("LocalSpy.Aliases"));
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        if(args.length > 0){
            user.sendMessage(KratosCore.getInstance().getConfig().getString("LocalSpy.Messages.Usage"));
            return;
        }
        if(user.isLocalSpy()){
            user.setLocalSpy(false);
            user.sendMessage(KratosCore.getInstance().getConfig().getString("LocalSpy.Messages.Disabled"));
        } else {
            user.setLocalSpy(true);
            user.sendMessage(KratosCore.getInstance().getConfig().getString("LocalSpy.Messages.Enabled"));
        }
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(Utils.format("&6Only players can use this command!"));
        return;
    }
}
