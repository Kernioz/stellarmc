package fr.kernioz.commands.moderations;

import fr.kernioz.Evania;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class SlowCommand implements CommandExecutor{

    public static int slow = 0;
    private static BukkitTask task;
    private Evania evania;

    public SlowCommand(Evania evania) {
        this.evania = evania;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)){
            System.out.print("StellarCore : Cette commande est exécutable que par les joueurs...");
            return true;
        }

        Player p = (Player)sender;

        if(!p.hasPermission("kernioz.slow")) {
            p.sendMessage("§4■ §cVous n'avez pas les permissions nécessaire pour exécuter cette commande.");
            return true;
        }else {
            if (args.length == 0) {
                p.sendMessage("§8■ §7Utiliser: §6/slow <seconde>");
                p.sendMessage("§cConfiguration du Slow, 1 message toutes les " + slow + " seconde(s)");
                return true;
            }

            if (args.length > 0) {

                try {
                    int time = Integer.parseInt(args[0]);

                    if (time < 0 || time > 300) {
                        p.sendMessage("La valeur doit être comprise entre 0 et 300 secondes.");
                        return true;
                    }

                    if (time == 0) {
                        Bukkit.broadcastMessage("§dSlow §8» §cDésactivé§7, les joueurs peuvent à présent parler dans le chat sans délai.");
                    }

                    slow = time;
                    Bukkit.broadcastMessage("§dSlow §8» §7Un message toutes les §e" + slow + " §7seconde(s)");
                    runSilenceOffTask();

                } catch (Exception e) {
                    displayHelp(p);
                    return true;
                }
            }

        }
        return true;
    }

    private void displayHelp(Player p){
        p.sendMessage("§e/slow §fpour voir l'état du mode slow");
        p.sendMessage("§e/slow <secondes> §f1 messages toutes les X secondes.");
    }

    private void runSilenceOffTask(){
        if(task != null) task.cancel();
        task = Bukkit.getScheduler().runTaskLater(Evania.get(), new Runnable() {

            @Override
            public void run() {
                Bukkit.broadcastMessage("§dSlow §8» §cDésactivé§7, les joueurs peuvent à présent parler dans le chat sans délai.");
            }
        }, 20 * 60 * 10);
    }
}

