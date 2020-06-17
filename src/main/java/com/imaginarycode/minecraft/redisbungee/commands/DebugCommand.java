package com.imaginarycode.minecraft.redisbungee.commands;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class DebugCommand extends Command {
    private final RedisBungee plugin;

    public DebugCommand(RedisBungee plugin) {
        super("rdebug", "redisbungee.command.debug");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        TextComponent poolActiveStat = new TextComponent("Currently active pool objects: " + plugin.getPool().getNumActive());
        TextComponent poolIdleStat = new TextComponent("Currently idle pool objects: " + plugin.getPool().getNumIdle());
        TextComponent poolWaitingStat = new TextComponent("Waiting on free objects: " + plugin.getPool().getNumWaiters());
        sender.sendMessage(poolActiveStat);
        sender.sendMessage(poolIdleStat);
        sender.sendMessage(poolWaitingStat);
    }
}