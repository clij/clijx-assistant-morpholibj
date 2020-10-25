package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;
import inra.ijpb.binary.conncomp.FloodFillComponentsLabeling3D;
import inra.ijpb.morphology.Reconstruction;
import inra.ijpb.morphology.Reconstruction3D;
import inra.ijpb.plugins.FillHolesPlugin;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJFillHoles")
public class MorphoLibJFillHoles extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image input, ByRef Image destination";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJFillHoles(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]));
        return result;
    }

    public static boolean morphoLibJFillHoles(CLIJ2 clij2, ClearCLBuffer input1, ClearCLBuffer output) {
        ClearCLBuffer result;
        if (input1.getDimension() == 2 || input1.getDepth() == 1) {
            // pull image from GPU
            ImageProcessor input_processor = clij2.pull(input1).getProcessor();

            // process it using MorphoLibJ
            ImageProcessor output_processor = Reconstruction.fillHoles(input_processor);

            // push result back
            result = clij2.push(new ImagePlus("title", output_processor));
        } else { // 3D
            // pull image from GPU
            ImageStack input_stack = clij2.pull(input1).getStack();

            // process it using MorphoLibJ
            ImageStack output_stack = Reconstruction3D.fillHoles(input_stack);

            // push result back
            result = clij2.push(new ImagePlus("title", output_stack));
        }

        // save it in the right place
        clij2.copy(result, output);

        // clean up
        result.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ Fill Holes (Binary/Gray) to an image.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Binary Filter";
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
