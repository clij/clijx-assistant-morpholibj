package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJConvexAreaMap")
public class MorphoLibJConvexAreaMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJConvexAreaMap() {
        super("ConvexArea");
    }
}
