package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import inra.ijpb.plugins.MarkerControlledWatershed3DPlugin;
import inra.ijpb.plugins.Watershed3DPlugin;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJMarkerControlledWatershed")
public class MorphoLibJMarkerControlledWatershed extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image gradient_input, Image labelled_spots_image, Image binary_restriction_input, ByRef Image destination";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJMarkerControlledWatershed(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), (ClearCLBuffer) (args[2]), (ClearCLBuffer) (args[3]));
        return result;
    }

    @Override
    public Object[] getDefaultValues() {
        return new Object[]{null, null, null, 0, Float.MAX_VALUE};
    }

    public static boolean morphoLibJMarkerControlledWatershed(CLIJ2 clij2, ClearCLBuffer gradient_input,  ClearCLBuffer labeled_spots_input, ClearCLBuffer binary_restiction_input, ClearCLBuffer output) {
        // pull image from GPU
        ImagePlus gradient_imp = clij2.pull(gradient_input);
        ImagePlus labeled_spots_imp = clij2.pull(labeled_spots_input);
        ImagePlus binary_restriction_imp = clij2.pull(binary_restiction_input);

        int connectivity = 6;
        if (gradient_input.getDimension() == 2 || gradient_imp.getNSlices() == 1 ) {
            connectivity = 4;
        }

        // process it using MorphoLibJ
        ImagePlus output_imp = new MarkerControlledWatershed3DPlugin().process(gradient_imp, labeled_spots_imp, binary_restriction_imp, connectivity);

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
        return "Apply MorpholibJs Marker-controlled Watershed to an image.\n\n" +
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
