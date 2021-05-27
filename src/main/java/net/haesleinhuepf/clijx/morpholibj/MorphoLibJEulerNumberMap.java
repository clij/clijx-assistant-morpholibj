package net.haesleinhuepf.clijx.morpholibj;


import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJEulerNumberMap")
public class MorphoLibJEulerNumberMap extends AbstractMorphoLibJAnalyzeRegions
{
    public MorphoLibJEulerNumberMap() {
        super("EulerNumber");
    }
}
