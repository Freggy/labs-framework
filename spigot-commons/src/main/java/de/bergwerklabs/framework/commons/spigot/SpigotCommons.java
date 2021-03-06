package de.bergwerklabs.framework.commons.spigot;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.bergwerklabs.framework.commons.spigot.file.FileUtil;
import de.bergwerklabs.framework.commons.spigot.general.LabsController;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created by Yannic Rieger on 02.05.2017.
 *
 * <p>Main class
 *
 * @author Yannic Rieger
 */
public class SpigotCommons extends JavaPlugin
    implements Listener, LabsController, PluginMessageListener {

  private static SpigotCommons instance;
  /** Prefix for console output. */
  public final String CONSOLE_PREFIX = "[SpigotCommons] ";
  private ProtocolManager protocolManager;
  private HashSet<UUID> joiningPlayers = new HashSet<>();

  /** Gets the instance of the Framework Plugin. */
  public static SpigotCommons getInstance() {
    return instance;
  }

  /** Gets a {@link ProtocolManager}. */
  public ProtocolManager getProtocolManager() {
    return this.protocolManager;
  }

  /** Contains all joining players. A player will be hold in this list for approx. 2 seconds. */
  public HashSet<UUID> getJoiningPlayers() {
    return this.joiningPlayers;
  }

  @Override
  public void onEnable() {
    // Just for test purposes
    FileUtil.createFolderIfNotExistent(this.getDataFolder());
    instance = this;

    this.protocolManager = ProtocolLibrary.getProtocolManager();

    this.getServer().getPluginManager().registerEvents(this, this);
  }

  @Override
  public void onDisable() {
    this.getServer().getScheduler().cancelTasks(this);
  }

  @Override
  public void onPluginMessageReceived(String s, Player player, byte[] bytes) {}
}
