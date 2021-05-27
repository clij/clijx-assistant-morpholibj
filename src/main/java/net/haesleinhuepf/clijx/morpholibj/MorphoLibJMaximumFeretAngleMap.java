package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJMaximumFeretAngleMap")
public class MorphoLibJMaximumFeretAngleMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJMaximumFeretAngleMap() {
        super("MaxFeretDiamAngle");
    }
}
