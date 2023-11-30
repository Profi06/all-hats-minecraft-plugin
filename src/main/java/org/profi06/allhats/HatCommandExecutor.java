package org.profi06.allhats;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class HatCommandExecutor implements CommandExecutor {
    public static final TextColor PLUGIN_COLOR = NamedTextColor.YELLOW;
    public static final TextColor WARNING_COLOR = NamedTextColor.RED;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {
        // Only useful for players
        if (commandSender instanceof Player) {
            Player csPlayer = (Player) commandSender;
            PlayerInventory csPlayerInv = csPlayer.getInventory();
            ItemStack item = csPlayerInv.getItemInMainHand();
            if (item.isEmpty()) {
                commandSender.sendMessage(Component.text("[")
                        .append(Component.text("All Hats", PLUGIN_COLOR))
                        .append(Component.text("] "))
                        .append(Component.text("Could not set hat. Hold something in your hand and try again.", WARNING_COLOR)));
            } else {
                if (hasHelmet(csPlayerInv)) {
                    dropHelmet(csPlayer);
                }
                csPlayerInv.setHelmet(item.asOne());
                csPlayer.playSound(csPlayer.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.PLAYERS, 1, 1);
                item.subtract();
                commandSender.sendMessage(Component.text("[")
                        .append(Component.text("All Hats", PLUGIN_COLOR))
                        .append(Component.text("] Successfully set hat!")));
            }
            return true;
        } else {
            commandSender.sendMessage(Component.text("[")
                    .append(Component.text("All Hats", PLUGIN_COLOR))
                    .append(Component.text("] "))
                    .append(Component.text("Only Players can set hat.", WARNING_COLOR))
            );
            return false;
        }
    }

    public boolean hasHelmet(PlayerInventory inv) {
        return inv.getHelmet() != null && !inv.getHelmet().isEmpty();
    }

    public void dropHelmet(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet == null) return;
        player.getWorld().dropItem(player.getEyeLocation(), helmet);
        player.getInventory().setHelmet(null);
    }
}
