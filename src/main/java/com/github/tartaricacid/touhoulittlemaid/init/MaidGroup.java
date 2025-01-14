package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

public class MaidGroup extends ItemGroup {
    private final ITextComponent displayName;
    private final RegistryObject<Item> iconItem;
    private ItemStack icon = null;

    public MaidGroup(String label, RegistryObject<Item> iconItem) {
        super(label);
        this.displayName = new TranslationTextComponent(String.format("item_group.%s.%s", TouhouLittleMaid.MOD_ID, label));
        this.iconItem = iconItem;
    }

    @Override
    public ItemStack makeIcon() {
        if (icon == null) {
            icon = iconItem.get().getDefaultInstance();
        }
        return icon;
    }

    @Override
    public ITextComponent getDisplayName() {
        return displayName;
    }
}
