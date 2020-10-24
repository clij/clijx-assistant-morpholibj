package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import inra.ijpb.binary.conncomp.FloodFillComponentsLabeling3D;
import inra.ijpb.plugins.Watershed3DPlugin;
import inra.ijpb.watershed.WatershedTransform3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJClassicWatershed")
public class MorphoLibJClassicWatershed extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image gradient_input, Image binary_restriction_input, ByRef Image destination, Number h_min, Number h_max";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJClassicWatershed(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), (ClearCLBuffer) (args[2]), asFloat(args[3]), asFloat(args[4]));
        return result;
    }

    @Override
    public Object[] getDefaultValues() {
        return new Object[]{null, null, null, 0, Float.MAX_VALUE};
    }

    public static boolean morphoLibJClassicWatershed(CLIJ2 clij2, ClearCLBuffer gradient_input, ClearCLBuffer binary_restiction_input, ClearCLBuffer output, Float h_min, Float h_max) {
        // pull image from GPU
        ImagePlus gradient_imp = clij2.pull(gradient_input);
        ImagePlus binary_restriction_imp = clij2.pull(binary_restiction_input);

        if (gradient_imp.getBitDepth() == 8) {
            h_min = Math.max(h_min, 0);
            h_max = Math.min(h_max, 255);
        } else if (gradient_imp.getBitDepth() == 16) {
            h_min = Math.max(h_min, 0);
            h_max = Math.min(h_max, 65535);
        } else {
            h_max = Math.min(h_max, Float.MAX_VALUE);
        }
        int connectivity = 6;
        if (gradient_input.getDimension() == 2 || gradient_imp.getNSlices() == 1 ) {
            connectivity = 4;
        }

        // process it using MorphoLibJ
        ImagePlus output_imp = new Watershed3DPlugin().process(gradient_imp, binary_restriction_imp, connectivity, h_min, h_max);

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
        return "Apply MorpholibJs Classic Watershed to an image.\n\n" +
                "Connectedness: 4 (2D) / 6 (3D)";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Binary,Label,Filter";
    }

    @Override
    public String getInputType() {
        return "Image, Binary Image";
    }

    @Override
    public String getOutputType() {
        return "Label Image";
    }
}
