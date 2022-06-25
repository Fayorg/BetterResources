package net.fayorg.betterresources.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fayorg.betterresources.BetterResourcesMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ExtractorRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final HashMap<Ingredient, Float> outputs;
    private final NonNullList<Ingredient> inputs;

    public ExtractorRecipe(ResourceLocation id, HashMap<Ingredient, Float> output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.outputs = output;
        this.inputs = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ExtractorRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ExtractorRecipe.Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    public HashMap<Ingredient, Float> getOutputs() {
        return this.outputs;
    }

    public static class Type implements RecipeType<ExtractorRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "extractor";
    }

    public static class Serializer implements RecipeSerializer<ExtractorRecipe> {
        public static final ExtractorRecipe.Serializer INSTANCE = new ExtractorRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(BetterResourcesMod.MODID,"extractor");

        @Override
        public ExtractorRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray inputsIngredients = GsonHelper.getAsJsonArray(json, "ingredients");
            JsonArray outputsIngredients = GsonHelper.getAsJsonArray(json, "outputsIngredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(inputsIngredients.size(), Ingredient.EMPTY);
            HashMap<Ingredient, Float> outputs = new HashMap<Ingredient, Float>();

            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Inputs: ");
            System.out.println(inputsIngredients.toString());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Outputs: ");
            System.out.println(outputsIngredients.toString());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            //System.out.println("Trying to get the first Item and Rarity :");
            //System.out.println("Rarity :" + outputsIngredients.get(0).getAsJsonObject().get("rarity").getAsFloat());

            System.out.println("InputsIngredients Size:" + inputsIngredients.size());
            System.out.println("Input Size:" + inputs.size());
            System.out.println("OutputsIngredients Size:" + outputsIngredients.size());

            for (int i = 0; i < inputs.size(); i++) {
                System.out.println("Added new Input: " + inputsIngredients.get(i));
                inputs.set(i, Ingredient.fromJson(inputsIngredients.get(i)));
            }

            for (int i = 0; i < outputsIngredients.size(); i++) {
                System.out.println("Added new Input: " + outputsIngredients.get(i));
                outputs.put(Ingredient.fromJson(outputsIngredients.get(i)), outputsIngredients.get(i).getAsJsonObject().get("rarity").getAsFloat());
            }

            return new ExtractorRecipe(id, outputs, inputs);
        }

        @Override
        public ExtractorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            HashMap<Ingredient, Float> outputs = new HashMap<>();
            int hashSize = buf.readInt();
            for (int i = 0; i < hashSize; i++) {
                outputs.put(Ingredient.fromNetwork(buf), buf.readFloat());
            }

            return new ExtractorRecipe(id, outputs, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ExtractorRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeInt(recipe.getOutputs().size());
            recipe.getOutputs().forEach(((ingredient, aFloat) -> {
                ingredient.toNetwork(buf);
                buf.writeFloat(aFloat);
            }));
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return ExtractorRecipe.Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
