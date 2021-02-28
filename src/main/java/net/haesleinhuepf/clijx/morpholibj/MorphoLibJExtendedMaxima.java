package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.morphology.MinimaAndMaxima3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJExtendedMaxima")
public class MorphoLibJExtendedMaxima extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image input, ByRef Image binary_destination, Number tolerance_threshold";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJExtendedMaxima(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), asFloat(args[2]));
        return result;
    }

    public static boolean morphoLibJExtendedMaxima(CLIJ2 clij2, ClearCLBuffer input, ClearCLBuffer labels_destination, Float tolerance_threshold) {
        // pull image from GPU
        ImageStack input_stack = clij2.pull(input).getStack();

        int connectivity = 6;
        ImageStack result_stack = MinimaAndMaxima3D.extendedMaxima(input_stack, tolerance_threshold, connectivity);

        // create image with watershed result
        ImagePlus resultImage = new ImagePlus( "wtvr", result_stack );

        // push result back
        ClearCLBuffer result = clij2.push(resultImage);

        // save it in the right place
        clij2.copy(result, labels_destination);

        // clean up
        result.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ's Extended Maxima to an image to produce an image where maxima regions are set to 255 and background to 0. \n\n" +
                "The tolerance parameter specifies how deep intensity valley between maxima can be to fuse them while maxima detection.\n" +
                "This operation uses connectivity = 6 (a.k.a. diamond).";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Detection,Binary";
    }

    @Override
    public String getInputType() {
        return "Image";
    }

    @Override
    public String getOutputType() {
        return "Binary Image";
    }

    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input) {
        return clij.create(input);
    }
}
