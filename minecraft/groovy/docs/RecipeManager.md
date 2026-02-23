```groovy
import classes.RecipeManager
```

## removeByMod

```groovy
RecipeManager.removeByMod(crafting, modId)
RecipeManager.removeByMod(crafting, modId, exclusions)
```
### Exclusion Formats

- `'wirecoil'` - excludes all metadata variants of that item
- `'material:14'` - excludes only that specific metadata

### Examples

```groovy
// Remove all recipes from a mod
RecipeManager.removeByMod(crafting, 'immersiveengineering')

// Remove all except wirecoil recipes
RecipeManager.removeByMod(crafting, 'immersiveengineering', ['wirecoil'])

// Remove all except a specific metadata variant
RecipeManager.removeByMod(crafting, 'somemod', ['material:14'])

// Multiple exclusions
def exclusions = [
    'wirecoil',
    'material:14',
    'tool_upgrades',
]
RecipeManager.removeByMod(crafting, 'immersiveengineering', exclusions)
```

## replaceIngredient

Accepts either strings (`'modid:item:meta'`) or ItemStacks (`item()`).

```groovy
RecipeManager.replaceIngredient(crafting, oldItem, newItem)
```

### Examples

```groovy
// With strings (modid:item or modid:item:meta)
RecipeManager.replaceIngredient(crafting, 'immersiveengineering:material:23', 'nucleartech:wire_fine:30')

// With item() calls
RecipeManager.replaceIngredient(crafting,
    item('immersiveengineering:wirecoil:0'),
    item('nucleartech:wire_aluminium')
)

// No metadata (defaults to 0)
RecipeManager.replaceIngredient(crafting, 'minecraft:iron_ingot', 'nucleartech:ingot_steel')
```