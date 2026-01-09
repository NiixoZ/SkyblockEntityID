# Hypixel SkyBlock EntityID
A simple Minecraft mod that determine and stock in NBT the EntityID of mobs in Hypixel SkyBlock.

## Features
- Automatically detects and stores it in NBT the EntityID of mobs in Hypixel SkyBlock.
- That's it.

## Example Usage
When creating a resource pack you can use it like that (with ETF or EMF mods):
```jem
models.1=2
nbt.1.SKYBLOCK_ENTITY_ID=iregex:^ZEALOT$

models.2=3
nbt.2.SKYBLOCK_ENTITY_ID=iregex:^ZEALOT_BRUISER$
```