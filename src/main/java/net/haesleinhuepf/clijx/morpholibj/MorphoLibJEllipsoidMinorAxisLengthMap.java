package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEllipsoidMinorAxisLengthMap")
public class MorphoLibJEllipsoidMinorAxisLengthMap extends AbstractMorphoLibJAnalyzeRegions3D
{
    public MorphoLibJEllipsoidMinorAxisLengthMap() {
        super("Elli.R1");
    }
}
