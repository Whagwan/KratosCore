package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.actionbarannouncer.ActionBarAnnouncements;
import me.kratoscore.net.bungee.announcer.Announcements;
import me.kratoscore.net.bungee.party.Party;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.tabmanager.TabManager;
import me.kratoscore.net.bungee.titleannouncer.TitleAnnouncements;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.CommandSender;

public class KutilisalsCommand extends DBCommand {

    public KutilisalsCommand() {
        super("kutilisals", "kutili");
        permissions.add("kratoscore.admin");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                KratosCore.getInstance().reloadConfig();
                Announcements.reloadAnnouncements();
                TitleAnnouncements.reloadAnnouncements();
                ActionBarAnnouncements.reloadAnnouncements();
                Party.reloadPartyData();
                Punishments.reloadPunishmentData();
                TabManager.reloadTab();
                sender.sendMessage(Utils.format("&e&KratosCore &8» &6Config reloaded!"));
                return;
            }
        }
        sender.sendMessage(Utils.format("&e&lKratosCore &8» &6Please use &b/kutilisals reload &6to reload the plugin!"));
    }
}