package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEllipseOrientationMap")
public class MorphoLibJEllipseOrientationMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJEllipseOrientationMap() {
        super("Ellipse.Orientation");
    }
}
