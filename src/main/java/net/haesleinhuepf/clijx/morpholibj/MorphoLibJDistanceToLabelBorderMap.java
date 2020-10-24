package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.binary.BinaryImages;
import inra.ijpb.label.LabelImages;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJDistanceToLabelBorderMap")
public class MorphoLibJDistanceToLabelBorderMap extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image label_input, ByRef Image destination";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJRemoveLargestRegion(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]));
        return result;
    }

    public static boolean morphoLibJRemoveLargestRegion(CLIJ2 clij2, ClearCLBuffer label_input, ClearCLBuffer output) {
        // pull image from GPU
        ImagePlus input_imp = clij2.pull(label_input);

        // process it using MorphoLibJ
        ImageStack output_stack = LabelImages.distanceMap(input_imp.getImageStack());

        // push result back
        ClearCLBuffer result = clij2.push(new ImagePlus("bla", output_stack));

        // save it in the right place
        clij2.copy(result, output);

        // clean up
        result.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ Chamfer distance map to a label image.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Label,Measurement";
    }

    @Override
    public String getInputType() {
        return "Label Image";
    }

    @Override
    public String getOutputType() {
        return "Image";
    }
}
