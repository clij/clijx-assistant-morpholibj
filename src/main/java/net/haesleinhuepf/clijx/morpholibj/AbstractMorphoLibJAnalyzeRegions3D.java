package net.haesleinhuepf.clijx.morpholibj;


import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.plugins.AnalyzeRegions3D;
import inra.ijpb.plugins.Watershed3DPlugin;
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

public abstract class AbstractMorphoLibJAnalyzeRegions3D extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput
{
    protected String property = null;

    protected AbstractMorphoLibJAnalyzeRegions3D(String property) {
        this.property = property;
    }

    @Override
    public boolean executeCL() {
        boolean result = morphoLibJAnalyzeRegions3D(getCLIJ2(), (ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]), property);
        return result;
    }

    @Override
    public String getParameterHelpText() {
        return "Image labels_input, ByRef Image parametric_map_destination";
    }

    protected static boolean morphoLibJAnalyzeRegions3D(CLIJ2 clij2, ClearCLBuffer labels, ClearCLBuffer parametric_map, String property) {
        // pull image from GPU
        ImagePlus labels_imp = clij2.pull(labels);

        ResultsTable table = new AnalyzeRegions3D().process(labels_imp);

        ClearCLBuffer vector = clij2.create(table.getCounter(), 1, 1);

        clij2.pushResultsTableColumn(vector, table, property);

        ClearCLBuffer vector_with_background = clij2.create(table.getCounter() + 1, 1, 1);
        clij2.setColumn(vector_with_background, 0, 0);
        clij2.paste(vector, vector_with_background, 1, 0, 0);

        clij2.replaceIntensities(labels, vector_with_background, parametric_map);

        // clean up
        vector.close();
        vector_with_background.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Applies MorphoLibJs Analyse Regions 3D to a label image an produces a parametric image visualizing " + property + ".\n\n" +
                "See also: https://imagej.net/MorphoLibJ.html#Region_analysis\n\n" +
                "Background is set to 0.";
    }


    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input) {
        return getCLIJ2().create(input.getDimensions(), NativeTypeEnum.Float);
    }

    @Override
    public String getAvailableForDimensions() {
        return "3D";
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
