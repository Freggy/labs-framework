package de.bergwerklabs.framework.commons.spigot.title;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.gson.JsonObject;
import de.bergwerklabs.framework.commons.spigot.nms.packet.serverside.title.TitlePacket;
import de.bergwerklabs.framework.commons.spigot.nms.packet.serverside.title.TitlePacketBuilder;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 18.07.2017.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class Title {

  private String title, subtitle;
  private int fadeIn, fadeOut, stay;

  /**
   * @param title
   * @param subtitle
   * @param fadeIn
   * @param fadeOut
   * @param stay
   */
  public Title(String title, String subtitle, int fadeIn, int fadeOut, int stay) {
    this.title = title;
    this.subtitle = subtitle;
    this.fadeIn = fadeIn;
    this.fadeOut = fadeOut;
    this.stay = stay;
  }

  /**
   * @param json
   * @return
   */
  public static Title fromJson(JsonObject json) {
    String title = null, subtitle = null;

    int fadeIn = json.get("fade-in").getAsInt();
    int fadeOut = json.get("fade-out").getAsInt();
    int stay = json.get("stay").getAsInt();

    if (json.has("title")) title = json.get("title").getAsString();

    if (json.has("subtitle")) subtitle = json.get("subtitle").getAsString();

    return new Title(title, subtitle, fadeIn, fadeOut, stay);
  }

  /** */
  public String getTitle() {
    return title;
  }

  /** @param title */
  public void setTitle(String title) {
    this.title = title;
  }

  /** */
  public String getSubtitle() {
    return subtitle;
  }

  /** @param subtitle */
  private void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  /** */
  public int getFadeIn() {
    return fadeIn;
  }

  /** @param fadeIn */
  public void setFadeIn(int fadeIn) {
    this.fadeIn = fadeIn;
  }

  /** */
  public int getFadeOut() {
    return fadeOut;
  }

  /** @param fadeOut */
  public void setFadeOut(int fadeOut) {
    this.fadeOut = fadeOut;
  }

  /** */
  public int getStay() {
    return stay;
  }

  /** @param stay */
  public void setStay(int stay) {
    this.stay = stay;
  }

  /**
   * Displays the title to the player.
   *
   * @param player {@link Player} to display the title to.
   */
  public void display(Player player) {
    this.createTitlePacket(EnumWrappers.TitleAction.TIMES, "", this.fadeIn, this.fadeOut, this.stay)
        .sendPacket(player);
    this.createTitlePacket(
            EnumWrappers.TitleAction.TITLE,
            this.title == null || this.title.equalsIgnoreCase("") ? "" : this.title,
            this.fadeIn,
            this.fadeOut,
            this.stay)
        .sendPacket(player);
    this.createTitlePacket(
            EnumWrappers.TitleAction.SUBTITLE,
            this.subtitle == null || this.subtitle.equalsIgnoreCase("") ? "" : this.subtitle,
            this.fadeIn,
            this.fadeOut,
            this.stay)
        .sendPacket(player);
  }

  /**
   * Builds a {@link TitlePacket}.
   *
   * @param action
   * @param text
   * @param fadeIn
   * @param fadeOut
   * @param stay
   * @return a {@link TitlePacket} ready to be sent.
   */
  private TitlePacket createTitlePacket(
      EnumWrappers.TitleAction action, String text, int fadeIn, int fadeOut, int stay) {
    return new TitlePacketBuilder()
        .setAction(action)
        .setTitle(WrappedChatComponent.fromText(text))
        .setFadeIn(fadeIn)
        .setFadeOut(fadeOut)
        .setStay(stay)
        .build();
  }
}
