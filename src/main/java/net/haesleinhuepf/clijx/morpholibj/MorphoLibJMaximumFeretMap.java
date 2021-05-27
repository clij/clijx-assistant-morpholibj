package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJMaximumFeretMap")
public class MorphoLibJMaximumFeretMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJMaximumFeretMap() {
        super("MaxFeretDiam");
    }
}
