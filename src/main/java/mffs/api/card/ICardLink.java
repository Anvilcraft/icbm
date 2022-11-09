package mffs.api.card;

import net.minecraft.item.ItemStack;
import universalelectricity.core.vector.Vector3;

public interface ICardLink {
    void setLink(final ItemStack p0, final Vector3 p1);

    Vector3 getLink(final ItemStack p0);
}
