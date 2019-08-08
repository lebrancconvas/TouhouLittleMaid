package com.github.tartaricacid.touhoulittlemaid.item;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityChair;
import com.github.tartaricacid.touhoulittlemaid.init.MaidItems;
import com.github.tartaricacid.touhoulittlemaid.proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.github.tartaricacid.touhoulittlemaid.item.ItemChair.NBT.MODEL_ID;
import static com.github.tartaricacid.touhoulittlemaid.item.ItemChair.NBT.MOUNTED_HEIGHT;

/**
 * @author TartaricAcid
 * @date 2019/8/8 11:36
 **/
public class ItemChair extends Item {
    private static final String DEFAULT_MODEL_ID = "touhou_little_maid:cushion";
    private static final float DEFAULT_MOUNTED_HEIGHT = -0.002f;

    public ItemChair() {
        setTranslationKey(TouhouLittleMaid.MOD_ID + ".chair");
        setMaxStackSize(1);
        setCreativeTab(MaidItems.TABS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing == EnumFacing.UP) {
            ItemStack itemstack = player.getHeldItem(hand);
            float yaw = (float) MathHelper.floor((MathHelper.wrapDegrees(player.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
            EntityChair chair = new EntityChair(worldIn, pos.getX() + 0.5, pos.up().getY(), pos.getZ() + 0.5, yaw);
            chair.setModelId(getChairModelId(itemstack));
            chair.setMountedHeight(getMountedHeight(itemstack));
            // 应用命名
            if (itemstack.hasDisplayName()) {
                chair.setCustomNameTag(itemstack.getDisplayName());
            }
            // 物品消耗，实体生成
            player.getHeldItem(hand).shrink(1);
            if (!worldIn.isRemote) {
                worldIn.spawnEntity(chair);
            }
            chair.rotationYawHead = yaw;
            chair.playSound(SoundEvents.BLOCK_CLOTH_PLACE, 1.0f, 1.0f);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }


    public static String getChairModelId(ItemStack stack) {
        if (stack.getItem() == MaidItems.CHAIR && stack.hasTagCompound() && stack.getTagCompound().hasKey(MODEL_ID.getName())) {
            return stack.getTagCompound().getString(MODEL_ID.getName());
        }
        return DEFAULT_MODEL_ID;
    }


    private static ItemStack setChairModelId(ItemStack stack, String modelId) {
        if (stack.getItem() == MaidItems.CHAIR) {
            if (stack.hasTagCompound()) {
                stack.getTagCompound().setString(MODEL_ID.getName(), modelId);
            } else {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setString(MODEL_ID.getName(), modelId);
                stack.setTagCompound(tag);
            }
        }
        return stack;
    }

    public static float getMountedHeight(ItemStack stack) {
        if (stack.getItem() == MaidItems.CHAIR && stack.hasTagCompound() && stack.getTagCompound().hasKey(MOUNTED_HEIGHT.getName())) {
            return stack.getTagCompound().getFloat(MOUNTED_HEIGHT.getName());
        }
        return DEFAULT_MOUNTED_HEIGHT;
    }

    private static ItemStack setMountedHeight(ItemStack stack, float height) {
        if (stack.getItem() == MaidItems.CHAIR) {
            if (stack.hasTagCompound()) {
                stack.getTagCompound().setFloat(MOUNTED_HEIGHT.getName(), height);
            } else {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setFloat(MOUNTED_HEIGHT.getName(), height);
                stack.setTagCompound(tag);
            }
        }
        return stack;
    }

    public static ItemStack setModelIdAndHeight(ItemStack stack, String modelId, float height) {
        setChairModelId(stack, modelId);
        setMountedHeight(stack, height);
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (String key : ClientProxy.ID_CHAIR_INFO_MAP.keySet()) {
                float height = ClientProxy.ID_CHAIR_INFO_MAP.get(key).getMountedYOffset();
                items.add(setModelIdAndHeight(new ItemStack(this), key, height));
            }
        }
    }

    enum NBT {
        // 模型 ID
        MODEL_ID("ModelId"),
        // 实体坐上去的高度
        MOUNTED_HEIGHT("MountedHeight");

        private String name;

        NBT(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}