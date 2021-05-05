package net.haesleinhuepf.clijx.morpholibj;


import ij.IJ;
import ij.ImagePlus;
import inra.ijpb.label.LabelImages;
import inra.ijpb.plugins.DilateLabelsPlugin;
import inra.ijpb.plugins.RemoveBorderLabelsPlugin;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.enums.ImageChannelDataType;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_morphoLibJDilateLabels")
public class MorphoLibJDilateLabels extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    @Override
    public String getParameterHelpText() {
        return "Image labels_input, ByRef Image labels_destination, Number distance";
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJDilateLabels(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), asFloat(args[2]));
        return result;
    }

    public static boolean morphoLibJDilateLabels(CLIJ2 clij2, ClearCLBuffer input1, ClearCLBuffer output, Float distance) {
        ClearCLBuffer input = input1;
        if (input1.getNativeType() == NativeTypeEnum.Float) {
            input = clij2.create(input.getDimensions(), NativeTypeEnum.UnsignedShort);
            clij2.copy(input1, input);
        }

        // pull image from GPU
        ImagePlus input_imp = clij2.pull(input);

        if (input != input1) {
            input.close();
        }

        // process it using MorphoLibJ
        input_imp = LabelImages.dilateLabels(input_imp, distance);

        // push result back
        ClearCLBuffer result = clij2.push(input_imp);

        // save it in the right place
        clij2.copy(result, output);

        // clean up
        result.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Apply MorphoLibJ Dilate Labels to a label image.\n\n";
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
        return "Label Image";
    }

    @Override
    public String getOutputType() {
        return "Label Image";
    }
}
