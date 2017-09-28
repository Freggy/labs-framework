package de.bergwerklabs.framework.party.client;

import de.bergwerklabs.framework.party.client.command.PartyDisbandCommand;
import de.bergwerklabs.framework.party.client.command.PartyLeaveCommand;
import de.bergwerklabs.framework.party.client.command.PartyParentCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Yannic Rieger on 04.09.2017.
 * <p>
 *
 * @author Yannic Rieger
 */
public class LabsPartyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("party").setExecutor(new PartyParentCommand("party", new PartyDisbandCommand(), new PartyLeaveCommand()));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}