package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJCircularityMap")
public class MorphoLibJCircularityMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJCircularityMap() {
        super("Circularity");
    }
}
