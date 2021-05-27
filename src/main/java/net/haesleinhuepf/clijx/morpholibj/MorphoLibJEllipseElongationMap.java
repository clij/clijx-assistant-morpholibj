package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEllipseElongationMap")
public class MorphoLibJEllipseElongationMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJEllipseElongationMap() {
        super("Ellipse.Elong");
    }
}
