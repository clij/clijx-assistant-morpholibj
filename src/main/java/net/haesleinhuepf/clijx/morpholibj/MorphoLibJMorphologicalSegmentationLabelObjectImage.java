package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel3D;
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

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJMorphologicalSegmentationLabelObjectImage")
public class MorphoLibJMorphologicalSegmentationLabelObjectImage extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image input, ByRef Image labels_destination, Number gradient_radius, Number tolerance_threshold";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJMorphologicalSegmentationLabelObjectImage(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), asInteger(args[2]), asFloat(args[3]));
        return result;
    }

    public static boolean morphoLibJMorphologicalSegmentationLabelObjectImage(CLIJ2 clij2, ClearCLBuffer input, ClearCLBuffer labels_destination, Integer gradient_radius, Float tolerance_threshold) {
        // pull image from GPU
        ImageStack input_stack = clij2.pull(input).getStack();
        ImageStack result_stack = morphoLibJMorphologicalSegmentationLabelObjectImage(input_stack, gradient_radius, tolerance_threshold);

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

    static ImageStack morphoLibJMorphologicalSegmentationLabelObjectImage(ImageStack input_stack, Integer gradient_radius, Float tolerance_threshold) {
        // create structuring element (cube of radius 'radius')
        Strel3D strel = Strel3D.Shape.CUBE.fromRadius( gradient_radius );
        // apply morphological gradient to input image
        ImageStack image = Morphology.gradient( input_stack, strel );

        return MorphoLibJMorphologicalSegmentationLabelBorderImage.morphoLibJMorphologicalSegmentationLabelBorderImage(image, tolerance_threshold);
    }

    @Override
    public String getDescription() {
        return "Apply MorpholibJ Morphological Segmentation to an object image to produce a label image. \n\n" +
                "The gradient radius parameter describes the width of edges which should be considered while separating objects.\n" +
                "The tolerance parameter specifies how deep intensity valley between local maxima can be to be ignored while flooding the regions.\n" +
                "With connectivity = 6 and using dams=false while computing the watershed.\n" +
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
