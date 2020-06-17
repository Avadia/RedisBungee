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
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class PlistCommand extends Command {
    private final RedisBungee plugin;

    public PlistCommand(RedisBungee plugin) {
        super("plist", "redisbungee.command.plist", "rplist");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                String proxy = args.length >= 1 ? args[0] : RedisBungee.getConfiguration().getServerId();
                if (!plugin.getServerIds().contains(proxy)) {
                    sender.sendMessage(new ComponentBuilder(proxy + " is not a valid proxy. See /serverids for valid proxies.").color(ChatColor.RED).create());
                    return;
                }
                Set<UUID> players = RedisBungee.getApi().getPlayersOnProxy(proxy);
                BaseComponent[] playersOnline = new ComponentBuilder("").color(ChatColor.YELLOW)
                        .append(MessageUtil.playerPlural(players.size()) + " currently on proxy " + proxy + ".").create();
                if (args.length >= 2 && args[1].equals("showall")) {
                    Multimap<String, UUID> serverToPlayers = RedisBungee.getApi().getServerToPlayers();
                    Multimap<String, String> human = HashMultimap.create();
                    for (Map.Entry<String, UUID> entry : serverToPlayers.entries()) {
                        if (players.contains(entry.getValue())) {
                            human.put(entry.getKey(), plugin.getUuidTranslator().getNameFromUuid(entry.getValue(), false));
                        }
                    }
                    for (String server : new TreeSet<>(human.keySet())) {
                        TextComponent serverName = new TextComponent();
                        serverName.setColor(ChatColor.RED);
                        serverName.setText("[" + server + "] ");
                        TextComponent serverCount = new TextComponent();
                        serverCount.setColor(ChatColor.YELLOW);
                        serverCount.setText("(" + human.get(server).size() + "): ");
                        TextComponent serverPlayers = new TextComponent();
                        serverPlayers.setColor(ChatColor.WHITE);
                        serverPlayers.setText(Joiner.on(", ").join(human.get(server)));
                        sender.sendMessage(serverName, serverCount, serverPlayers);
                    }
                    sender.sendMessage(playersOnline);
                } else {
                    sender.sendMessage(playersOnline);
                    sender.sendMessage(new ComponentBuilder("To see all players online, use /plist " + proxy + " showall.").color(ChatColor.YELLOW).create());
                }
            }
        });
    }
}