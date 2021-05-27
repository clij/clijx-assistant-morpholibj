package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJInscribedBallRadiusMap")
public class MorphoLibJInscribedBallRadiusMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJInscribedBallRadiusMap() {
        super("InscrBall.Radius");
    }
}
