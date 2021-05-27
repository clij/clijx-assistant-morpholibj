package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJConvexityMap")
public class MorphoLibJConvexityMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJConvexityMap() {
        super("Convexity");
    }
}
