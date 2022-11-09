package icbm.gangshao.terminal;

import calclavia.lib.TileEntityUniversalRunnable;
import icbm.gangshao.access.AccessLevel;
import icbm.gangshao.access.UserAccess;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public abstract class TileEntityTerminal
        extends TileEntityUniversalRunnable implements ITerminal {
    private final List<String> terminalOutput;
    private final List<UserAccess> users;
    public static final int SCROLL_SIZE = 15;
    private int scroll;
    public final Set<EntityPlayer> playersUsing;

    public TileEntityTerminal() {
        this.terminalOutput = new ArrayList<>();
        this.users = new ArrayList<>();
        this.scroll = 0;
        this.playersUsing = new HashSet<>();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && super.ticks % 3L == 0L) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public abstract String getChannel();

    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord,
                this.getBlockMetadata(), nbt);
    }

    public void sendTerminalOutputToClients() {
        // TODO: WTF
        // final List data = new ArrayList();
        // data.add(TerminalPacketType.TERMINAL_OUTPUT.ordinal());
        // data.add(this.getTerminalOuput().size());
        // data.addAll(this.getTerminalOuput());
        // final Packet packet = PacketManager.getPacket(this.getChannel(), this,
        // data.toArray()); for (final EntityPlayer player : this.playersUsing) {
        // PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
        // }
    }

    // TODO: WTF
    // public void sendCommandToServer(final EntityPlayer entityPlayer,
    // final String cmdInput) {
    // if (this.worldObj.isRemote) {
    // final Packet packet = PacketManager.getPacket(
    // this.getChannel(), this,
    // TerminalPacketType.GUI_COMMAND.ordinal(),
    // entityPlayer.getDisplayName(), cmdInput);
    // PacketDispatcher.sendPacketToServer(packet);
    // }
    // }

    // TODO: WHAT THE ACTUAL FUCK!
    // @Override
    // public void handlePacketData(final INetworkManager network,
    // final int packetID,
    // final Packet250CustomPayload packet,
    // final EntityPlayer player,
    // final ByteArrayDataInput dataStream) {
    // try {
    // final TerminalPacketType packetType =
    // TerminalPacketType.values()[dataStream.readInt()]; switch
    // (packetType) {
    // case DESCRIPTION_DATA: {
    // if (this.worldObj.isRemote) {
    // final short size = dataStream.readShort();
    // if (size > 0) {
    // final byte[] byteCode = new byte[size];
    // dataStream.readFully(byteCode);
    // this.func_70307_a(CompressedStreamTools.func_74792_a(byteCode));
    // }
    // break;
    // }
    // break;
    // }
    // case GUI_COMMAND: {
    // if (!this.field_70331_k.isRemote) {
    // CommandRegistry.onCommand(
    // this.field_70331_k.getPlayerEntityByName(dataStream.readUTF()),
    // this, dataStream.readUTF());
    // this.sendTerminalOutputToClients();
    // break;
    // }
    // break;
    // }
    // case GUI_EVENT: {
    // if (this.field_70331_k.isRemote) {
    // break;
    // }
    // if (dataStream.readBoolean()) {
    // this.playersUsing.add(player);
    // this.sendTerminalOutputToClients();
    // break;
    // }
    // this.playersUsing.remove(player);
    // break;
    // }
    // case TERMINAL_OUTPUT: {
    // if (this.field_70331_k.isRemote) {
    // final int size2 = dataStream.readInt();
    // final List oldTerminalOutput = new
    // ArrayList(this.terminalOutput);
    // this.terminalOutput.clear();
    // for (int i = 0; i < size2; ++i) {
    // this.terminalOutput.add(dataStream.readUTF());
    // }
    // if (!this.terminalOutput.equals(oldTerminalOutput) &&
    // this.terminalOutput.size() !=
    // oldTerminalOutput.size()) {
    // this.setScroll(this.getTerminalOuput().size() -
    // 15);
    // }
    // break;
    // }
    // break;
    // }
    // }
    // } catch (final Exception e) {
    // ZhuYaoBase.LOGGER.severe("Terminal error: " + this.toString());
    // e.printStackTrace();
    // }
    // }

    @Override
    public AccessLevel getUserAccess(final String username) {
        for (int i = 0; i < this.users.size(); ++i) {
            if (this.users.get(i).username.equalsIgnoreCase(username)) {
                return this.users.get(i).level;
            }
        }
        return AccessLevel.NONE;
    }

    public boolean canUserAccess(final String username) {
        return this.getUserAccess(username).ordinal() >= AccessLevel.USER.ordinal();
    }

    @Override
    public List<UserAccess> getUsers() {
        return this.users;
    }

    @Override
    public List<UserAccess> getUsersWithAcess(final AccessLevel level) {
        final List<UserAccess> players = new ArrayList<>();
        for (int i = 0; i < this.users.size(); ++i) {
            final UserAccess ref = this.users.get(i);
            if (ref.level == level) {
                players.add(ref);
            }
        }
        return players;
    }

    @Override
    public boolean addUserAccess(final String player, final AccessLevel lvl,
            final boolean save) {
        this.removeUserAccess(player);
        final boolean bool = this.users.add(new UserAccess(player, lvl, save));
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        return bool;
    }

    @Override
    public boolean removeUserAccess(final String player) {
        final List<UserAccess> removeList = new ArrayList<>();
        for (int i = 0; i < this.users.size(); ++i) {
            final UserAccess ref = this.users.get(i);
            if (ref.username.equalsIgnoreCase(player)) {
                removeList.add(ref);
            }
        }
        if (removeList != null && removeList.size() > 0) {
            final boolean bool = this.users.removeAll(removeList);
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return bool;
        }
        return false;
    }

    @Override
    public List<String> getTerminalOuput() {
        return this.terminalOutput;
    }

    @Override
    public boolean addToConsole(String msg) {
        if (!this.worldObj.isRemote) {
            msg.trim();
            if (msg.length() > 23) {
                msg = msg.substring(0, 22);
            }
            this.getTerminalOuput().add(msg);
            this.sendTerminalOutputToClients();
            return true;
        }
        return false;
    }

    @Override
    public void scroll(final int amount) {
        this.setScroll(this.scroll + amount);
    }

    @Override
    public void setScroll(final int length) {
        this.scroll = Math.max(Math.min(length, this.getTerminalOuput().size()), 0);
    }

    @Override
    public int getScroll() {
        return this.scroll;
    }

    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.users.clear();
        final NBTTagList userList = nbt.getTagList("Users", 10);
        for (int i = 0; i < userList.tagCount(); ++i) {
            final NBTTagCompound var4 = (NBTTagCompound) userList.getCompoundTagAt(i);
            this.users.add(UserAccess.loadFromNBT(var4));
        }
    }

    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        final NBTTagList usersTag = new NBTTagList();
        for (int player = 0; player < this.users.size(); ++player) {
            final UserAccess access = this.users.get(player);
            if (access != null && access.shouldSave) {
                final NBTTagCompound accessData = new NBTTagCompound();
                access.writeToNBT(accessData);
                usersTag.appendTag((NBTBase) accessData);
            }
        }
        nbt.setTag("Users", (NBTBase) usersTag);
    }

    public enum TerminalPacketType {
        GUI_EVENT("GUI_EVENT", 0),
        GUI_COMMAND("GUI_COMMAND", 1),
        TERMINAL_OUTPUT("TERMINAL_OUTPUT", 2),
        DESCRIPTION_DATA("DESCRIPTION_DATA", 3);

        private TerminalPacketType(final String name, final int ordinal) {
        }
    }
}
