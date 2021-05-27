package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJSphericityMap")
public class MorphoLibJSphericityMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJSphericityMap() {
        super("Sphericity");
    }
}
