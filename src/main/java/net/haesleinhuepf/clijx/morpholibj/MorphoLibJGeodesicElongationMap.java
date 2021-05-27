package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJGeodesicElongationMap")
public class MorphoLibJGeodesicElongationMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJGeodesicElongationMap() {
        super("GeodesicElongation");
    }
}
