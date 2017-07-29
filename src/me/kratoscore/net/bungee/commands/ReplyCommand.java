package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.PrivateMessageEvent;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ReplyCommand extends DBCommand {

    public ReplyCommand() {
        super("greply", KratosCore.getInstance().getConfig().getStringList("PrivateMessages.Reply.Aliases"));
        permissions.add("kratoscore.reply");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        ProxiedPlayer p = user.getPlayer();
        if (!KratosCore.getInstance().getPmcache().containsKey(p.getName().toLowerCase())) {
            p.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("PrivateMessages.Reply.Messages.NoTarget")));
            return;
        }
        if (args.length < 1) {
            p.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("PrivateMessages.Reply.Messages.WrongUsage")));
            return;
        }
        String target = KratosCore.getInstance().getPmcache().get(p.getName().toLowerCase());
        ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(target);
        if (pl == null) {
            p.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("PrivateMessages.Reply.Messages.OfflineTarget")));
            KratosCore.getInstance().getPmcache().remove(p.getName().toLowerCase());
            KratosCore.getInstance().getPmcache().remove(target.toLowerCase());
            return;
        }
        String message = Joiner.on(" ").join(args);

        PrivateMessageEvent event = new PrivateMessageEvent(KratosCore.getInstance().getUser(p),
                KratosCore.getInstance().getUser(pl), message);
        ProxyServer.getInstance().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
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

        p.sendMessage(Utils.format(KratosCore.getInstance().getConfig()
                .getString("PrivateMessages.Reply.Messages.Format.Sending").replace("%player%", pl.getName())
                .replace("%server%", pl.getServer().getInfo().getName()).replace("%message%", message)));
        pl.sendMessage(Utils.format(KratosCore.getInstance().getConfig()
                .getString("PrivateMessages.Reply.Messages.Format.Receiving").replace("%player%", p.getName())
                .replace("%server%", p.getServer().getInfo().getName()).replace("%message%", message)));
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(Utils.format("&6Only players can use this command!"));
    }
}
