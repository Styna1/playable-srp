import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.UniversalBucket

hideByMod = { String modId, List<String> exclusions = [] ->
    int count = 0

    // Item hiding
    for (def rl : ForgeRegistries.ITEMS.getKeys()) {
        if (rl.getNamespace() != modId) continue
        def path = rl.getPath()
        if (path in exclusions) continue
        def registryItem = ForgeRegistries.ITEMS.getValue(rl)
        if (registryItem == null) continue
        try {
            mods.jei.ingredient.hide(new ItemStack(registryItem, 1, OreDictionary.WILDCARD_VALUE))
            count++
        } catch (Throwable ignored) {}
    }

    // Fluid sucks
    def universalBucket = null
    try { universalBucket = FluidRegistry.getUniversalBucket() } catch (Throwable ignored) {}
    if (universalBucket == null) {
        try {
            def item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("forge", "bucketfilled"))
            if (item instanceof UniversalBucket) universalBucket = item
        } catch (Throwable ignored) {}
    }
    for (def entry : FluidRegistry.getRegisteredFluids().entrySet()) {
        def fluidName = entry.getKey()
        def fluidObj = entry.getValue()
        def fluidBlock = fluidObj.getBlock()
        if (fluidBlock == null) continue
        def blockRL = ForgeRegistries.BLOCKS.getKey(fluidBlock)
        if (blockRL == null || blockRL.getNamespace() != modId) continue

        try {
            mods.jei.ingredient.hide(fluid(fluidName))
            count++
        } catch (Throwable ignored) {}

        if (universalBucket != null) {
            try {
                def bucketStack = UniversalBucket.getFilledBucket(universalBucket, fluidObj)
                if (bucketStack != null) {
                    mods.jei.ingredient.hide(bucketStack)
                    count++
                }
            } catch (Throwable ignored) {}
        }
    }

    count
}

hideByModJei = hideByMod
