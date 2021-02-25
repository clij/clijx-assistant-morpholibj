package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import inra.ijpb.binary.BinaryImages;
import inra.ijpb.plugins.RemoveBorderLabelsPlugin;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJRemoveLargestRegion")
public class MorphoLibJRemoveBorderLabels extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image labels_input, ByRef Image labels_destination, Boolean left, Boolean right, Boolean top, Boolean bottom, Boolean front, Boolean back";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJRemoveLargestRegion(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), asBoolean(args[2]),asBoolean(args[3]),asBoolean(args[4]),asBoolean(args[5]),asBoolean(args[6]),asBoolean(args[7]));
        return result;
    }

    public static boolean morphoLibJRemoveLargestRegion(CLIJ2 clij2, ClearCLBuffer input1, ClearCLBuffer output, Boolean left, Boolean right, Boolean top, Boolean bottom, Boolean front, Boolean back) {
        // pull image from GPU
        ImagePlus input_imp = clij2.pull(input1);

        // process it using MorphoLibJ
        input_imp = RemoveBorderLabelsPlugin.remove(input_imp, left, right, top, bottom, front, back);

        // push result back
        ClearCLBuffer result = clij2.push(input_imp);

        // save it in the right place
        clij2.copy(result, output);

        // clean up
        result.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ Remove Border Labels to a label image.\n\n";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Label";
    }

    @Override
    public String getInputType() {
        return "Label Image";
    }

    @Override
    public String getOutputType() {
        return "Label Image";
    }
}
