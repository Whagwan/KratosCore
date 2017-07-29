package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class ChatCommand extends DBCommand {

    public ChatCommand(){
        super("chat");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length == 1 && args[0].equals("lock")){
            if (KratosCore.getInstance().chatMuted){
                KratosCore.getInstance().chatMuted = false;
                sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("ChatLock.Unlock")));
                if(KratosCore.getInstance().getConfig().getBoolean("ChatLock.Broadcast.Enabled")){
                    for(String s : KratosCore.getInstance().getConfig().getStringList("ChatLock.Broadcast.UnLock")){
                        ProxyServer.getInstance().broadcast(Utils.format(s));
                    }
                }
            } else {
                KratosCore.getInstance().chatMuted = true;
                sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("ChatLock.Lock")));
                if(KratosCore.getInstance().getConfig().getBoolean("ChatLock.Broadcast.Enabled")){
                    for(String s : KratosCore.getInstance().getConfig().getStringList("ChatLock.Broadcast.Lock")){
                        ProxyServer.getInstance().broadcast(Utils.format(s));
                    }
                }
            }
            return;
        }
        sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("ChatLock.ChatCommand.WrongArgs")));
        return;
    }
}