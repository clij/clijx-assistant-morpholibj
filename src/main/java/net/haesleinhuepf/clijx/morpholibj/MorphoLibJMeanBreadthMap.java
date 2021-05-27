package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJMeanBreadthMap")
public class MorphoLibJMeanBreadthMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJMeanBreadthMap() {
        super("MeanBreadth");
    }
}
