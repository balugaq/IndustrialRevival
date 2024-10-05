package org.irmc.industrialrevival.implementation.items.machines.electric;

import org.bukkit.inventory.ItemStack;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItemStack;
import org.irmc.industrialrevival.api.items.groups.ItemGroup;
import org.irmc.industrialrevival.api.machines.ElectricMachine;
import org.irmc.industrialrevival.api.machines.recipes.MachineRecipes;
import org.irmc.industrialrevival.api.recipes.RecipeType;
import org.irmc.industrialrevival.implementation.items.IRItems;
import org.jetbrains.annotations.NotNull;

public class ReactionChamber extends ElectricMachine {
    public ReactionChamber() {
        super();
        addRecipe(400, 100,
                IRItems.IRItemStacks.RAW_GRAPHENE,
                IRItems.IRItemStacks.GRAPHENE_THIN_FILM);
    }
}
