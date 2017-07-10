/**
 * PacketWrapper - ProtocolLib wrappers for Minecraft packets
 * Copyright (C) dmulloy2 <http://dmulloy2.net>
 * Copyright (C) Kristian S. Strangeland
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.bergwerklabs.framework.commons.spigot.nms.packet.v1MV8;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import de.bergwerklabs.framework.commons.spigot.nms.packet.AbstractPacket;

public class WrapperPlayClientBoatMove extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Client.BOAT_MOVE;

    public WrapperPlayClientBoatMove() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayClientBoatMove(PacketContainer packet) {
        super(packet, TYPE);
    }

    public boolean getLeftOar() {
        return handle.getBooleans().read(0);
    }

    public void setLeftOar(boolean value) {
        handle.getBooleans().write(0, value);
    }

    public boolean getRightOar() {
        return handle.getBooleans().read(1);
    }

    public void setRightOar(boolean value) {
        handle.getBooleans().write(1, value);
    }

}
