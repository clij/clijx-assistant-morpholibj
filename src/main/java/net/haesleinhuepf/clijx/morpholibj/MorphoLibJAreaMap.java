package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJAreaMap")
public class MorphoLibJAreaMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJAreaMap() {
        super("Area");
    }
}
