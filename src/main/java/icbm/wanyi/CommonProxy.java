// 
// Decompiled by Procyon v0.6.0
// 

package icbm.wanyi;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import icbm.wanyi.b.TProximityDetector;
import cpw.mods.fml.common.registry.GameRegistry;
import icbm.wanyi.b.TCamouflage;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
    public void preInit() {
    }
    
    public void init() {
        GameRegistry.registerTileEntity((Class)TCamouflage.class, "ICBMYinXin");
        GameRegistry.registerTileEntity((Class)TProximityDetector.class, "ICBMYinGanQi");
    }
    
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return null;
    }
    
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return null;
    }
}
