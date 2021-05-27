package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEllipseMinorAxisLengthMap")
public class MorphoLibJEllipseMinorAxisLengthMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJEllipseMinorAxisLengthMap() {
        super("Ellipse.Radius1");
    }
}
