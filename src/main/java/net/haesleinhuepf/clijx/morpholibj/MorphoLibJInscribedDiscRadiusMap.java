package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJInscribedDiscRadiusMap")
public class MorphoLibJInscribedDiscRadiusMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJInscribedDiscRadiusMap() {
        super("InscrDisc.Radius");
    }
}
