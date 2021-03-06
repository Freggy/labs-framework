package de.bergwerklabs.framework.bedrock.api.lobby;

import de.bergwerklabs.framework.bedrock.api.PlayerRegistry;
import de.bergwerklabs.framework.bedrock.api.event.game.GameStartEvent;
import de.bergwerklabs.framework.bedrock.api.event.lobby.LobbyWaitingPhaseStartEvent;
import de.bergwerklabs.framework.bedrock.api.event.lobby.LobbyWaitingPhaseStopEvent;
import de.bergwerklabs.framework.bedrock.api.session.GameSession;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimerStopCause;
import de.bergwerklabs.framework.commons.spigot.general.update.Task;
import de.bergwerklabs.framework.commons.spigot.general.update.TaskManager;
import de.bergwerklabs.framework.commons.spigot.title.ActionbarTitle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Yannic Rieger on 18.09.2017.
 *
 * <p>Simple lobby implementation for mini games.
 *
 * @author Yannic Rieger
 */
public class SimpleLobby extends AbstractLobby {

  private boolean timerShortened = false;
  private Task task;

  /**
   * @param waitingDuration duration the players have to wait.
   * @param maxPlayers maximum amount of player that can play the game.
   * @param minPlayers minimum amount of players needed to start the game.
   * @param session {@link GameSession} associated with this lobby.
   */
  public SimpleLobby(
      int waitingDuration,
      int maxPlayers,
      int minPlayers,
      GameSession session,
      PlayerRegistry registry) {
    super(waitingDuration, maxPlayers, minPlayers, session, registry);
  }

  @Override
  public void init() {}

  @Override
  public void startWaitingPhase() {
    Bukkit.getServer().getPluginManager().callEvent(new LobbyWaitingPhaseStartEvent(this.session));
    task =
        TaskManager.registerAsyncRepeatingTask(
            () -> {
              int currentPlayers = Bukkit.getOnlinePlayers().size();
              if (currentPlayers < this.minPlayers) {
                this.timer.stop();
                int needed = (this.minPlayers - currentPlayers);
                if (needed == 1) {
                  ActionbarTitle.broadcastTitle("§6» §cWarte auf §4einen §canderen Spieler... §6«");
                } else
                  ActionbarTitle.broadcastTitle(
                      "§6» §cWarte auf §4" + needed + "§c andere Spieler... §6«");
              } else if (currentPlayers >= minPlayers) {
                if (!this.timer.isRunning()) {
                  this.timer.start();
                }
              } else if (currentPlayers >= maxPlayers) {
                if (!timerShortened) {
                  this.timer.setTimeLeft(15);
                  timerShortened = true;
                  this.session
                      .getGame()
                      .getMessenger()
                      .messageAll(
                          "§bMaximale Anzahl erreicht. Timer wird auf 15 Sekunden verkürzt.");
                }
              }
            },
            20,
            10);

    this.timer.addStopListener(
        event -> {
          if (event.getCause() == LabsTimerStopCause.TIMES_UP) {
            Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(0));
            Bukkit.getPluginManager().callEvent(new LobbyWaitingPhaseStopEvent(this.session));
            // clear chat
            for (int i = 0; i < 30; i++)
              Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(" "));
            TaskManager.stopTask(task);
            HandlerList.unregisterAll(this);
            ActionbarTitle.broadcastTitle("");
            this.session.getGame().start(this.registry);
            Bukkit.getPluginManager().callEvent(new GameStartEvent<>(this.session.getGame()));
          }
        });
  }

  @EventHandler
  private void onPlayerDamage(EntityDamageEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  private void onFoodLevelChange(FoodLevelChangeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().setLevel(0);
  }
}
