package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.binary.BinaryImages;
import inra.ijpb.binary.conncomp.FloodFillComponentsLabeling3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJKeepLargestRegion")
public class MorphoLibJKeepLargestRegion extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image binary_input, ByRef Image binary_destination";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJKeepLargestRegion(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]));
        return result;
    }

    public static boolean morphoLibJKeepLargestRegion(CLIJ2 clij2, ClearCLBuffer input1, ClearCLBuffer output) {
        // pull image from GPU
        ImagePlus input_imp = clij2.pull(input1);

        // process it using MorphoLibJ
        ImagePlus output_imp = BinaryImages.keepLargestRegion(input_imp);

        // push result back
        ClearCLBuffer result = clij2.push(output_imp);

        // save it in the right place
        clij2.copy(result, output);

        // clean up
        result.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ Keep Largest Region to a binary image.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Binary,Filter";
    }

    @Override
    public String getInputType() {
        return "Binary Image";
    }

    @Override
    public String getOutputType() {
        return "Binary Image";
    }
}
