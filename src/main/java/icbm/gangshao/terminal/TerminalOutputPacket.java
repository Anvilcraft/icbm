package icbm.gangshao.terminal;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import universalelectricity.core.vector.Vector3;

public class TerminalOutputPacket implements IMessage {
    Vector3 pos;
    List<String> output;

    public TerminalOutputPacket(Vector3 pos, List<String> output) {
        this.pos = pos;
        this.output = output;
    }

    public TerminalOutputPacket() {
        this.output = new ArrayList<>();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new Vector3(buf.readInt(), buf.readInt(), buf.readInt());
        int c = buf.readInt();
        for (int i = 0; i < c; i++) {
            int slen = buf.readInt();
            byte[] out = new byte[slen];
            buf.readBytes(out);
            output.add(new String(out));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pos.intX());
        buf.writeInt(this.pos.intY());
        buf.writeInt(this.pos.intZ());

        buf.writeInt(this.output.size());
        for (String s : this.output) {
            buf.writeInt(s.getBytes().length);
            buf.writeBytes(s.getBytes());
        }
    }
}
