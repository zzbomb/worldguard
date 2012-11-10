package com.sk89q.worldguard.protection.regions;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class RegionUtil {

    public static void isValidId(String id) throws CommandException {

        isValidId(id, true, true);
    }

    public static void isValidId(String id, boolean validCheck, boolean globalCheck) throws CommandException {

        if (!ProtectedRegion.isValidId(id) && validCheck)
            throw new CommandException("Invalid region ID specified!");
        if (id.equalsIgnoreCase("__global__") && globalCheck)
            throw new CommandException("A region cannot be named __global__");
    }

    public static ProtectedRegion createRegionFromSelection(String id, Selection selection) throws CommandException {

        ProtectedRegion region;

        if (selection instanceof Polygonal2DSelection) {
            Polygonal2DSelection polySel = (Polygonal2DSelection) selection;
            int minY = polySel.getNativeMinimumPoint().getBlockY();
            int maxY = polySel.getNativeMaximumPoint().getBlockY();
            region = new ProtectedPolygonalRegion(id, polySel.getNativePoints(), minY, maxY);
        } else if (selection instanceof CuboidSelection) {
            BlockVector min = selection.getNativeMinimumPoint().toBlockVector();
            BlockVector max = selection.getNativeMaximumPoint().toBlockVector();
            region = new ProtectedCuboidRegion(id, min, max);
        } else {
            throw new CommandException("The type of region selected in WorldEdit is unsupported in WorldGuard!");
        }
        return region;
    }
}
