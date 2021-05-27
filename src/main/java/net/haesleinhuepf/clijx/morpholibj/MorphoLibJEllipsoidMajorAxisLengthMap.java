package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEllipsoidMajorAxisLengthMap")
public class MorphoLibJEllipsoidMajorAxisLengthMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJEllipsoidMajorAxisLengthMap() {
        super("Elli.R3");
    }
}
