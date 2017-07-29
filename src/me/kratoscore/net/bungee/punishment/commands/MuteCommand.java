package me.kratoscore.net.bungee.punishment.commands;

import java.util.Arrays;
import java.util.UUID;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.MuteEvent;
import me.kratoscore.net.bungee.punishment.MuteAPI;
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

public class MuteCommand extends DBCommand {

    public MuteCommand() {
        super("mute");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Boolean useUUIDs = KratosCore.getInstance().getConfigData().UUIDSTORAGE;
        if (args.length < 2) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Mute.Messages.WrongArgs")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        UUID uuid = UUIDFetcher.getUUIDOf(args[0]);
        if (uuid == null && useUUIDs) {
            return;
        }
        if ((useUUIDs ? MuteAPI.isMuted(uuid.toString()) : MuteAPI.isMuted(args[0]))) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Mute.Messages.AlreadyMuted")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        String reason = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));
        MuteAPI.addMute(sender.getName(), (useUUIDs ? uuid.toString() : args[0]), -1L, reason);
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
        if (p != null) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Mute.Messages.MuteMessage")) {
                p.sendMessage(Utils.format(s.replace("%player%", sender.getName()).replace("%reason%", reason)));
            }
        }
        PlayerInfo info = new PlayerInfo((useUUIDs ? uuid.toString() : args[0]));
        MuteEvent event = new MuteEvent(sender.getName(), args[0], -1L, reason);
        BungeeCord.getInstance().getPluginManager().callEvent(event);
        info.addMute();
        for (String s : Punishments.punishments.getFile().getStringList("Punishments.Mute.Messages.Muted")) {
            sender.sendMessage(Utils.format(s.replace("%player%", args[0]).replace("%reason%", reason)));
        }
    }
}