package me.kratoscore.net.bungee.party;


import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import me.kratoscore.net.api.commands.CommandAPI;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.managers.FileManager;

import net.md_5.bungee.api.ProxyServer;

public class Party {

    private static String path = File.separator + "plugins" + File.separator + "KratosCore" + File.separator
            + "party.yml";
    public static FileManager party = new FileManager(path);

    public static void reloadPartyData() {
        party = null;
        party = new FileManager(path);
    }

    public static void registerPartySystem() {
        party = null;
        party = new FileManager(path);
        if (party.getFile().getBoolean("Party.Enabled", true)) {
            setDefaults();
            CommandAPI.getInstance().registerCommand(new PartyCommand());
            ProxyServer.getInstance().getPluginManager().registerListener(KratosCore.getInstance(), new PartyEvents());
        }
    }

    private static void setDefaults() {
        Collection<String> check = party.getFile().getSection("Party").getKeys();
        if (!check.contains("Enabled")) {
            party.getFile().set("Party.Enabled", true);
        }
        if (!check.contains("Party-Limit")) {
            party.getFile().set("Party.Party-Limit", 0);
        }
        if (!check.contains("Server-Switch")) {
            party.getFile().set("Party.Server-Switch", false);
        }
        if (!check.contains("Messages")) {
            party.getFile().set("Party.Messages.Help",
                    Arrays.asList(new String[] { "&c&l&m----------&r &8[&a&lParty Help&8] &c&l&m----------&r",
                            "&c/party create &7- &c&oCreate a new party",
                            "&c/party list &7- &c&oList the current members of your party}",
                            "&c/party leave &7- &c&oLeave/Delete your current party",
                            "&c/party chat &7- &c&oChat with your party members",
                            "&c/party invite &7- &c&oInvite a player to your party",
                            "&c/party kick &7- &c&oKick someone from your party",
                            "&c/party admin (player) &7- &c&oPromote someone to admin in your party.",
                            "&c&l&m----------&r &8[&a&lParty Help&8] &c&l&m----------&r" }));

            party.getFile().set("Party.Messages.NoPermission", "&cYou don't have the permission to use this Command!");
            party.getFile().set("Party.Messages.WrongArgs", "&cPlease use /party help for more info.");
            party.getFile().set("Party.Messages.ConsoleError", "&cThe console cannot use this Command!");
            party.getFile().set("Party.Messages.AlreadyInParty", "&cYou are already in a party!");
            party.getFile().set("Party.Messages.PartyCreated", "&aYou have created a new party!");
            party.getFile().set("Party.Messages.OfflinePlayer", "&cThat player is not online!");
            party.getFile().set("Party.Messages.AdminRemove", "&c%player% is not a party admin anymore.");
            party.getFile().set("Party.Messages.AdminAdd", "&a%player% is now a party admin!");
            party.getFile().set("Party.Messages.NotInYourParty", "&cThat Player is not in your party!");
            party.getFile().set("Party.Messages.NotPartyOwner",
                    "&cYou are not the party owner, so you cannot use this command!");
            party.getFile().set("Party.Messages.NotInParty", "&cYou are not in a party!");
            party.getFile().set("Party.Messages.LeftParty", "&cYou have left your party!");
            party.getFile().set("Party.Messages.LeftPartyMessage", "&c%player% has left the party!");
            party.getFile().set("Party.Messages.PartyRemoved", "&cYou have deleted your party!");
            party.getFile().set("Party.Messages.PartyRemovedMessage", "&c%player% has deleted the party!");
            party.getFile().set("Party.Messages.PartyChatFormat", "&f%player% &6:: &f%message%");
            party.getFile().set("Party.Messages.PartyFull", "&cYour party is full!");
            party.getFile().set("Party.Messages.PlayerAlreadyInParty", "&cThat player is already in your party!");
            party.getFile().set("Party.Messages.PlayerHasOwnParty", "&cThat player already has his own party!");
            party.getFile().set("Party.Messages.InvitedPlayer", "&aYou have invited &e%player% &ato your party!");
            party.getFile().set("Party.Messages.InviteReceive",
                    Arrays.asList(new String[] { "&e%player% &ahas invited you to join his party!",
                            "&aUse /party accept within 1 minute to join his Party!" }));
            party.getFile().set("Party.Messages.AcceptedToLate", "&cThis invitation is already expired!");
            party.getFile().set("Party.Messages.JoinedParty", "&aYou have joined the party!");
            party.getFile().set("Party.Messages.JoinedPartyMessage", "&e%player% &ahas joined the party!");
            party.getFile().set("Party.Messages.NoInvites", "&cYou do not have any invites.");
            party.getFile().set("Party.Messages.Kicked", "&cYou have been kicked out of the party!");
            party.getFile().set("Party.Messages.KickedPlayer", "&cYou have kicked %player% out of the party!");
            party.getFile().set("Party.Messages.KickedMessage", "&4%player% &cwas kicked out of the party!");
            party.getFile().set("Party.Messages.AdminError", "&cYou cannot make yourself admin!");
            party.getFile().set("Party.Messages.ServerWarp", "&aThe party owner has sent you to his server!");

            party.getFile().set("Party.Messages.PartyList.Header", "&a========== &eParty Members &a==========");
            party.getFile().set("Party.Messages.PartyList.Format",
                    "&a%player% &eis %onORoff% &8[&a%partyrank%&8] [&e%server%&8]");
            party.getFile().set("Party.Messages.PartyList.Footer", "&a========== &eParty Members &a==========");
        }
        party.save();
    }
}