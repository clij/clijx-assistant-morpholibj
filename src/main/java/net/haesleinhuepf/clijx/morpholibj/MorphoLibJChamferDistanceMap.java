package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.binary.ChamferWeights3D;
import inra.ijpb.binary.conncomp.FloodFillComponentsLabeling3D;
import inra.ijpb.binary.distmap.DistanceTransform3D;
import inra.ijpb.binary.distmap.DistanceTransform3D4WeightsFloat;
import inra.ijpb.binary.distmap.DistanceTransform3DFloat;
import inra.ijpb.plugins.ChamferDistanceMap3DPlugin;
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

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJChamferDistanceMap")
public class MorphoLibJChamferDistanceMap extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasAuthor, HasLicense
{
    static String weightLabel = ChamferWeights3D.WEIGHTS_3_4_5_7.toString();
    static boolean normalize = true;


    @Override
    public String getParameterHelpText() {
        return "Image input, ByRef Image destination";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJChamferDistanceMap(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]));
        return result;
    }

    public static boolean morphoLibJChamferDistanceMap(CLIJ2 clij2, ClearCLBuffer input1, ClearCLBuffer output) {
        // pull image from GPU
        ImageStack input_stack = clij2.pull(input1).getStack();

        // process it using MorphoLibJ
        ChamferWeights3D chamferWeights = ChamferWeights3D.fromLabel(weightLabel);
        float[] weights = chamferWeights.getFloatWeights();
        DistanceTransform3D algo = new DistanceTransform3DFloat(weights, normalize);

        ImageStack output_stack = algo.distanceMap(input_stack);

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
        return "Apply MorpholibJ ChamferDistanceMap to an image.\n\n" +
                "Options: \n" +
                "Weights: " + weightLabel + "\n" +
                "Normalize: " + normalize;
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Binary";
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
