package icbm.gangshao.terminal;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import universalelectricity.core.vector.Vector3;

public class TerminalCommandPacket implements IMessage {
    Vector3 pos;
    String cmd;

    public TerminalCommandPacket() {}

    public TerminalCommandPacket(Vector3 pos, String cmd) {
        this.pos = pos;
        this.cmd = cmd;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new Vector3(buf.readInt(), buf.readInt(), buf.readInt());
        int len = buf.readInt();
        byte[] data = new byte[len];
        buf.readBytes(data);
        this.cmd = new String(data);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pos.intX());
        buf.writeInt(this.pos.intY());
        buf.writeInt(this.pos.intZ());

        buf.writeInt(this.cmd.getBytes().length);
        buf.writeBytes(this.cmd.getBytes());
    }
}
