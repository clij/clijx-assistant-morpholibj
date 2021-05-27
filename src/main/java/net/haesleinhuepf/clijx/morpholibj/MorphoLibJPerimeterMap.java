package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJPerimeterMap")
public class MorphoLibJPerimeterMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJPerimeterMap() {
        super("Perimeter");
    }
}
