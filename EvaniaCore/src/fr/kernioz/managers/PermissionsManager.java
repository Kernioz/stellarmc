package fr.kernioz.managers;

import fr.kernioz.Evania;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class PermissionsManager {

    Player player;
    HashMap<UUID, PermissionAttachment> permissionHash = new HashMap<UUID, PermissionAttachment>();

    public PermissionsManager(Player player){
        this.player = player;
        PermissionAttachment permissionAttachment = player.addAttachment(Evania.get());
        permissionHash.put(player.getUniqueId(), permissionAttachment);
    }

    public void addPermission(String permission){
        PermissionAttachment playerPerm = permissionHash.get(player.getUniqueId());
        playerPerm.setPermission(permission, true);
    }

    public void removePermission(String permission){
        PermissionAttachment playerPerm = permissionHash.get(player.getUniqueId());
        playerPerm.unsetPermission(permission);
    }
}
