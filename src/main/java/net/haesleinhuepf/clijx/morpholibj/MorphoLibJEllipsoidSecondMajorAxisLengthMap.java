package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEllipsoidSecondMajorAxisLengthMap")
public class MorphoLibJEllipsoidSecondMajorAxisLengthMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJEllipsoidSecondMajorAxisLengthMap() {
        super("Elli.R2");
    }
}
