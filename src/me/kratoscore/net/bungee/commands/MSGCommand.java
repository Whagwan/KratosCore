package me.kratoscore.net.bungee.commands;

import java.util.ArrayList;
import java.util.List;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.PrivateMessageEvent;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MSGCommand extends DBCommand {

    public MSGCommand() {
        super("gmsg", KratosCore.getInstance().getConfig().getStringList("PrivateMessages.MSG.Aliases"));
        permissions.add("kratoscore.msg");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        ProxiedPlayer p = user.getPlayer();
        if(args.length < 2){
            p.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("PrivateMessages.MSG.Messages.WrongUsage")));
            return;
        }
        String target = args[0];
        ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(target);
        if(pl == null){
            p.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("PrivateMessages.MSG.Messages.OfflineTarget")));
            return;
        }
        List<String> newargs = new ArrayList<String>();
        for(String arg : args){
            if(!arg.equals(target)){
                newargs.add(arg);
            }
        }
        String message = Joiner.on(" ").join(newargs);

        PrivateMessageEvent event = new PrivateMessageEvent(KratosCore.getInstance().getUser(p), KratosCore.getInstance().getUser(pl), message);
        ProxyServer.getInstance().getPluginManager().callEvent(event);

        if(event.isCancelled()){
            return;
        }

        if (KratosCore.getInstance().getPmcache().containsKey(p.getName().toLowerCase())) {
            KratosCore.getInstance().getPmcache().remove(p.getName().toLowerCase());
        }
        if (KratosCore.getInstance().getPmcache().containsKey(pl.getName().toLowerCase())) {
            KratosCore.getInstance().getPmcache().remove(pl.getName().toLowerCase());
        }
        KratosCore.getInstance().getPmcache().put(p.getName().toLowerCase(), pl.getName().toLowerCase());
        KratosCore.getInstance().getPmcache().put(pl.getName().toLowerCase(), p.getName().toLowerCase());

        p.sendMessage(TextComponent.fromLegacyText(KratosCore.getInstance().getConfig().getString("PrivateMessages.MSG.Messages.Format.Sending").replace("%player%", pl.getName()).replace("%server%", pl.getServer().getInfo().getName()).replace("&", "§").replace("%message%", message)));
        pl.sendMessage(TextComponent.fromLegacyText(KratosCore.getInstance().getConfig().getString("PrivateMessages.MSG.Messages.Format.Receiving").replace("%player%", p.getName()).replace("%server%", p.getServer().getInfo().getName()).replace("&", "§").replace("%message%", message)));
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(Utils.format("&6Only players can use this command!"));
    }
}