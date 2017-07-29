package me.kratoscore.net.bungee.punishment.commands;

import java.util.Arrays;
import java.util.UUID;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.KickEvent;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.PlayerInfo;
import me.kratoscore.net.bungee.utils.UUIDFetcher;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class KickCommand extends DBCommand {

    public KickCommand() {
        super("kick");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Boolean useUUIDs = KratosCore.getInstance().getConfigData().UUIDSTORAGE;
        if (args.length < 2) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Kick.Messages.WrongArgs")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
        if (p == null) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Kick.Messages.NotOnline")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        String reason = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));
        UUID uuid = UUIDFetcher.getUUIDOf(args[0]);
        if (uuid == null && useUUIDs) {
            return;
        }
        PlayerInfo info = new PlayerInfo((useUUIDs ? uuid.toString() : args[0]));
        info.addKick();
        KickEvent event = new KickEvent(sender.getName(), args[0], reason);
        BungeeCord.getInstance().getPluginManager().callEvent(event);
        p.disconnect(
                Utils.format(
                        Joiner.on("\n")
                                .join(Punishments.punishments.getFile()
                                        .getStringList("Punishments.Kick.Messages.KickMessage"))
                                .replace("%kicker%", sender.getName()).replace("%reason%", reason)));
        for (String s : Punishments.punishments.getFile().getStringList("Punishments.Kick.Messages.Kicked")) {
            sender.sendMessage(Utils.format(s.replace("%player%", args[0])));
        }
    }
}