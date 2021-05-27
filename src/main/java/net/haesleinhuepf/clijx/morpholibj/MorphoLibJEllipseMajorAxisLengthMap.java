package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEllipseMajorAxisLengthMap")
public class MorphoLibJEllipseMajorAxisLengthMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJEllipseMajorAxisLengthMap() {
        super("Ellipse.Radius2");
    }
}
