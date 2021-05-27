package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJTortuosityMap")
public class MorphoLibJTortuosityMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJTortuosityMap() {
        super("Tortuosity");
    }
    // InscrDisc.Radius
    // AverageThickness
    // GeodesicElongation
}
