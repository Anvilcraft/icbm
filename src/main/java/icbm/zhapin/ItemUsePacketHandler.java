package icbm.zhapin;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import icbm.zhapin.dianqi.ItLaserDesignator;
import icbm.zhapin.dianqi.ItRadarGun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;

public class ItemUsePacketHandler implements IMessageHandler<ItemUsePacket, IMessage> {
    @Override
    public IMessage onMessage(ItemUsePacket message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (message.type == ItemUsePacket.Type.RADAR_GUN) {
            if (player.inventory.getCurrentItem().getItem() instanceof ItRadarGun) {
                final ItemStack itemStack = player.inventory.getCurrentItem();

                if (itemStack.stackTagCompound == null) {
                    itemStack.setTagCompound(new NBTTagCompound());
                }

                itemStack.stackTagCompound.setInteger("x", message.pos.intX());
                itemStack.stackTagCompound.setInteger("y", message.pos.intY());
                itemStack.stackTagCompound.setInteger("z", message.pos.intZ());
                ICBMExplosion.itLeiDaQiang.onProvide(
                    ElectricityPack.getFromWatts(
                        1000.0, ICBMExplosion.itLeiDaQiang.getVoltage(itemStack)
                    ),
                    itemStack
                );
            }
        } else if (message.type == ItemUsePacket.Type.LASER_DESIGNATOR) {
            if (player.inventory.getCurrentItem().getItem()
                    instanceof ItLaserDesignator) {
                final ItemStack itemStack = player.inventory.getCurrentItem();
                final Vector3 position = message.pos;
                ((ItLaserDesignator) ICBMExplosion.itLeiSheZhiBiao)
                    .setLauncherCountDown(itemStack, 119);
                player.worldObj.playSoundEffect(
                    position.intX(),
                    player.worldObj.getHeightValue(position.intX(), position.intZ()),
                    position.intZ(),
                    "icbm:airstrike",
                    5.0f,
                    (1.0f
                     + (player.worldObj.rand.nextFloat()
                        - player.worldObj.rand.nextFloat())
                         * 0.2f)
                        * 0.7f
                );
                player.worldObj.spawnEntityInWorld(
                    new ELightBeam(player.worldObj, position, 100, 0.0f, 1.0f, 0.0f)
                );
                ICBMExplosion.itLeiDaQiang.onProvide(
                    ElectricityPack.getFromWatts(
                        6000.0, ICBMExplosion.itLeiDaQiang.getVoltage(itemStack)
                    ),
                    itemStack
                );
            }
        } else if (message.type == ItemUsePacket.Type.REMOTE) {
            final ItemStack itemStack = player.inventory.getCurrentItem();
            ICBMExplosion.itYaoKong.onProvide(
                ElectricityPack.getFromWatts(
                    1500.0, ICBMExplosion.itYaoKong.getVoltage(itemStack)
                ),
                itemStack
            );
        }

        return null;
    }
}
