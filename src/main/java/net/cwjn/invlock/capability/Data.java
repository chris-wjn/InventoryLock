package net.cwjn.invlock.capability;

import net.minecraft.nbt.CompoundTag;

public class Data {

    private boolean locked = false;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void copyFrom(Data source) {
        locked = source.isLocked();
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putBoolean("invlock.islocked", locked);
    }

    public void loadNBTData(CompoundTag tag) {
        locked = tag.getBoolean("invlock.islocked");
    }

}
