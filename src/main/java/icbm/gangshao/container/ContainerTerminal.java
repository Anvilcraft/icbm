package icbm.gangshao.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import icbm.gangshao.terminal.TileEntityTerminal;
import net.minecraft.inventory.Container;

public abstract class ContainerTerminal extends Container
{
    private TileEntityTerminal tileEntity;
    
    public ContainerTerminal(final InventoryPlayer inventoryPlayer, final TileEntityTerminal tileEntity) {
        this.tileEntity = tileEntity;
        this.tileEntity.playersUsing.add(inventoryPlayer.player);
    }
    
    public void onContainerClosed(final EntityPlayer par1EntityPlayer) {
        this.tileEntity.playersUsing.remove(par1EntityPlayer);
        super.onContainerClosed(par1EntityPlayer);
    }
    
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return !(this.tileEntity instanceof IInventory) || ((IInventory)this.tileEntity).isUseableByPlayer(par1EntityPlayer);
    }
}
