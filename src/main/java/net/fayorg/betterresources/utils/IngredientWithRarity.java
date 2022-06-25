package net.fayorg.betterresources.utils;

import net.minecraft.world.item.crafting.Ingredient;

public class IngredientWithRarity {

    private final String item;
    private final float rarity;

    public IngredientWithRarity(String ingredient, float rarity) {
        this.item = ingredient;
        this.rarity = rarity;
    }

    public IngredientWithRarity(Ingredient ingredient, float rarity) {
        this(ingredient.toString(), rarity);
    }

    public float getRarity() {
        return rarity;
    }

    public String getItem() {
        return item;
    }
}
