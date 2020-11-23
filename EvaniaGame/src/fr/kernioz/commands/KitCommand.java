package fr.kernioz.commands;

import fr.kernioz.inventories.KitMenu;
import fr.kernioz.util.gui.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)){
            System.out.print("StellarCore : Cette commande est exécutable que par les joueurs...");
            return true;
        }

        GuiManager.openGui(new KitMenu((Player)commandSender));

        return false;
    }
}
