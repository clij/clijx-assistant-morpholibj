package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.binary.BinaryImages;
import inra.ijpb.morphology.MinimaAndMaxima3D;
import inra.ijpb.watershed.Watershed;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJMorphologicalSegmentationLabelBorderImage")
public class MorphoLibJMorphologicalSegmentationLabelBorderImage extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image input, ByRef Image labels_destination, Number tolerance_threshold";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJMorphologicalSegmentationLabelBorderImage(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), asFloat(args[2]));
        return result;
    }

    public static boolean morphoLibJMorphologicalSegmentationLabelBorderImage(CLIJ2 clij2, ClearCLBuffer input, ClearCLBuffer labels_destination, Float tolerance_threshold) {
        // pull image from GPU
        ImageStack input_stack = clij2.pull(input).getStack();
        ImageStack result_stack = morphoLibJMorphologicalSegmentationLabelBorderImage(input_stack, tolerance_threshold);

        // create image with watershed result
        ImagePlus resultImage = new ImagePlus( "watershed", result_stack );

        // push result back
        ClearCLBuffer result = clij2.push(resultImage);

        // save it in the right place
        clij2.copy(result, labels_destination);

        // clean up
        result.close();

        return true;
    }

    static ImageStack morphoLibJMorphologicalSegmentationLabelBorderImage(ImageStack input_stack, Float tolerance_threshold) {
        int connectivity = 6;
        boolean dams = false;

        // find regional minima on gradient image with dynamic value of 'tolerance' and 'conn'-connectivity
        ImageStack regionalMinima = MinimaAndMaxima3D.extendedMinima(input_stack, tolerance_threshold, connectivity);

        // impose minima on gradient image
        ImageStack imposedMinima = MinimaAndMaxima3D.imposeMinima(input_stack, regionalMinima, connectivity);

        // label minima using connected components (32-bit output)
        ImageStack labeledMinima = BinaryImages.componentsLabeling(regionalMinima, connectivity, 32);

        // apply marker-based watershed using the labeled minima on the minima-imposed
        // gradient image (the last value indicates the use of dams in the output)
        ImageStack resultStack = Watershed.computeWatershed(imposedMinima, labeledMinima, connectivity, dams);

        return resultStack;
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ Morphological Segmentation to an object image to produce a label image. \n\n" +
                "The tolerance parameter specifies how deep intensity valley between local maxima can be to be ignored while flooding the regions.\n" +
                "With connectivity = 6 and using dams=false while computing the watershed." +
                "See also https://imagej.net/Morphological_Segmentation";
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
        return "Image";
    }

    @Override
    public String getOutputType() {
        return "Label Image";
    }

    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input) {
        return clij.create(input.getDimensions(), NativeTypeEnum.Float);
    }
}
