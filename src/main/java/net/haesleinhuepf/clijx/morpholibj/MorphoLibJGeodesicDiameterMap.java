package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJGeodesicDiameterMap")
public class MorphoLibJGeodesicDiameterMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJGeodesicDiameterMap() {
        super("GeodesicDiameter");
    }
}
