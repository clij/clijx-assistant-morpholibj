package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJSurfaceAreaMap")
public class MorphoLibJSurfaceAreaMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJSurfaceAreaMap() {
        super("SurfaceArea");
    }
}
