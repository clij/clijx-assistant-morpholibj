package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEulerNumber3DMap")
public class MorphoLibJEulerNumber3DMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJEulerNumber3DMap() {
        super("EulerNumber");
    }
}
