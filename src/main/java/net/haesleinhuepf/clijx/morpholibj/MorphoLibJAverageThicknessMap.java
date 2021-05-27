package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJAverageThicknessMap")
public class MorphoLibJAverageThicknessMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJAverageThicknessMap() {
        super("AverageThickness");
    }
}
