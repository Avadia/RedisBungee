package com.imaginarycode.minecraft.redisbungee.commands;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.util.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

public class GlistCommand extends Command {
    private final RedisBungee plugin;

    public GlistCommand(RedisBungee plugin) {
        super("glist", "bungeecord.command.list", "redisbungee", "rglist");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            int count = RedisBungee.getApi().getPlayerCount();
            BaseComponent[] playersOnline = new ComponentBuilder("").color(ChatColor.YELLOW)
                    .append(MessageUtil.playerPlural(count) + " currently online.").create();
            if (args.length > 0 && args[0].equals("showall")) {
                Multimap<String, UUID> serverToPlayers = RedisBungee.getApi().getServerToPlayers();
                Multimap<String, String> human = HashMultimap.create();
                for (Map.Entry<String, UUID> entry : serverToPlayers.entries()) {
                    human.put(entry.getKey(), plugin.getUuidTranslator().getNameFromUuid(entry.getValue(), false));
                }
                for (String server : new TreeSet<>(serverToPlayers.keySet())) {
                    TextComponent serverName = new TextComponent();
                    serverName.setColor(ChatColor.GREEN);
                    serverName.setText("[" + server + "] ");
                    TextComponent serverCount = new TextComponent();
                    serverCount.setColor(ChatColor.YELLOW);
                    serverCount.setText("(" + serverToPlayers.get(server).size() + "): ");
                    TextComponent serverPlayers = new TextComponent();
                    serverPlayers.setColor(ChatColor.WHITE);
                    serverPlayers.setText(Joiner.on(", ").join(human.get(server)));
                    sender.sendMessage(serverName, serverCount, serverPlayers);
                }
                sender.sendMessage(playersOnline);
            } else {
                sender.sendMessage(playersOnline);
                sender.sendMessage(new ComponentBuilder("To see all players online, use /glist showall.").color(ChatColor.YELLOW).create());
            }
        });
    }
}