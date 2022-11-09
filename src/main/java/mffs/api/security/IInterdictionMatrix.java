package mffs.api.security;

import java.util.Set;

import mffs.api.IActivatable;
import mffs.api.IBiometricIdentifierLink;
import mffs.api.fortron.IFortronFrequency;
import mffs.api.modules.IModuleAcceptor;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IInterdictionMatrix extends IInventory, IFortronFrequency,
                                             IModuleAcceptor, IBiometricIdentifierLink,
                                             IActivatable {
    int getWarningRange();

    int getActionRange();

    boolean mergeIntoInventory(final ItemStack p0);

    Set<ItemStack> getFilteredItems();

    boolean getFilterMode();

    int getFortronCost();
}
