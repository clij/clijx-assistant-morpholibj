package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.binary.conncomp.FloodFillComponentsLabeling3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasAuthor;
import net.haesleinhuepf.clij2.utilities.HasLicense;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJConnectedComponentsLabeling")
public class MorphoLibJConnectedComponentsLabeling extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasAuthor, HasLicense
{
    @Override
    public String getParameterHelpText() {
        return "Image input, ByRef Image destination";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJConnectedComponentsLabeling(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]));
        return result;
    }

    public static boolean morphoLibJConnectedComponentsLabeling(CLIJ2 clij2, ClearCLBuffer input1, ClearCLBuffer output) {
        // pull image from GPU
        ImageStack input_stack = clij2.pull(input1).getStack();

        // process it using MorphoLibJ
        ImageStack output_stack = new FloodFillComponentsLabeling3D().computeLabels(input_stack);

        // push result back
        ClearCLBuffer result = clij2.push(new ImagePlus("title", output_stack));

        // save it in the right place
        clij2.copy(result, output);

        // clean up
        result.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ FloodFillComponentsLabeling3D to an image.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Labeling";
    }

    @Override
    public String getAuthorName() {
        return "Put your name here.";
    }

    @Override
    public String getLicense() {
        return "Public domain";
    }
}
