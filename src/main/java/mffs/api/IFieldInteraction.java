package mffs.api;

import java.util.Set;

import mffs.api.modules.IModule;
import mffs.api.modules.IModuleAcceptor;
import mffs.api.modules.IProjectorMode;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRotatable;

public interface IFieldInteraction extends IModuleAcceptor, IRotatable, IActivatable {
    IProjectorMode getMode();

    ItemStack getModeStack();

    int[] getSlotsBasedOnDirection(final ForgeDirection p0);

    int[] getModuleSlots();

    int getSidedModuleCount(final IModule p0, final ForgeDirection... p1);

    Vector3 getTranslation();

    Vector3 getPositiveScale();

    Vector3 getNegativeScale();

    int getRotationYaw();

    int getRotationPitch();

    Set<Vector3> getCalculatedField();

    Set<Vector3> getInteriorPoints();

    void setCalculating(final boolean p0);

    void setCalculated(final boolean p0);
}
