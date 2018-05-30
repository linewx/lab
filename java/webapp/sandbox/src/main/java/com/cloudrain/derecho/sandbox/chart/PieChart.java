package com.cloudrain.derecho.sandbox.chart;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.AttributedString;

import javax.swing.JPanel;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a pie chart using 
 * data from a {@link DefaultPieDataset}.
 */
public class PieChart extends ApplicationFrame {

    /**
     * Default constructor.
     *
     * @param title  the frame title.
     */
    public PieChart(String title) throws Exception{
        super(title);
        setContentPane(createDemoPanel());
    }

    static class CustomLabelGenerator implements PieSectionLabelGenerator {

        /**
         * Generates a label for a pie section.
         *
         * @param dataset  the dataset (<code>null</code> not permitted).
         * @param key  the section key (<code>null</code> not permitted).
         *
         * @return the label (possibly <code>null</code>).
         */
        public String generateSectionLabel(final PieDataset dataset, final Comparable key) {
            Number number = dataset.getValue(key);
            return key.toString() + "  ("+ number.toString() + ")";
        }

        @Override
        public AttributedString generateAttributedSectionLabel(PieDataset pieDataset, Comparable comparable) {
            Object object = null;
            String string = comparable.toString();
            String string_0_ = (string + " : " + String.valueOf(pieDataset
                    .getValue(comparable)));
            AttributedString attributedstring = new AttributedString("");
            attributedstring.addAttribute(TextAttribute.WEIGHT,
                    TextAttribute.WEIGHT_BOLD, 0, string.length() - 1);
            return attributedstring;
        }
    }

    /**
     * Creates a sample dataset.
     *
     * @return A sample dataset.
     */
    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("High", new Double(1));
        dataset.setValue("Medium", new Double(21));
        dataset.setValue("Low", new Double(51));
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset  the dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart Demo 1",  // chart title
                dataset,             // data
                true,               // include legend
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.BOLD, 16));
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
        plot.setShadowXOffset(0);
        plot.setShadowYOffset(0);
        plot.setLegendItemShape(new Rectangle(6,6));
        plot.setSectionPaint("Low", new Color(221, 158, 13));
        plot.setSectionPaint("Medium", new Color(117, 63, 131));
        plot.setSectionPaint("High", new Color(34, 129, 175));
        plot.setLabelGenerator(new CustomLabelGenerator());
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelLinkPaint(Color.green);
        plot.setLabelPaint(Color.white);
        //plot.setLabelGap(0.2d);
        plot.setSimpleLabels(true);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() throws Exception{
        JFreeChart chart = createChart(createDataset());
        saveChartToPDF(chart, "C:\\Users\\lugan\\test\\test.pdf",300, 300);
        //create(new FileOutputStream("C:\\Users\\lugan\\test\\test.pdf"), chart);
        return new ChartPanel(chart);
    }

    public static void create(OutputStream outputStream, JFreeChart chart) throws DocumentException, IOException {
        Document document = null;
        PdfWriter writer = null;

        try {
            //instantiate document and writer
            document = new Document();
            writer = PdfWriter.getInstance(document, outputStream);

            //open document
            document.open();

            //add image
            int width = 500;
            int height = 500;
            BufferedImage bufferedImage = chart.createBufferedImage(width, height);
            Image image = Image.getInstance(writer, bufferedImage, 1.0f);
            document.add(image);

            //release resources
            document.close();
            document = null;

            writer.close();
            writer = null;
        } catch(DocumentException de) {
            throw de;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            //release resources
            if(null != document) {
                try { document.close(); }
                catch(Exception ex) { }
            }

            if(null != writer) {
                try { writer.close(); }
                catch(Exception ex) { }
            }
        }
    }
    public static void saveChartToPDF(JFreeChart chart, String fileName, int width, int height) throws Exception {
        if (chart != null) {
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(new FileOutputStream(fileName));

                //convert chart to PDF with iText:
                com.lowagie.text.Rectangle pagesize = new com.lowagie.text.Rectangle(width, height);
                com.lowagie.text.Document document = new com.lowagie.text.Document(pagesize, 50, 50, 50, 50);
                try {
                    PdfWriter writer = PdfWriter.getInstance(document, out);
                    document.addAuthor("JFreeChart");
                    document.open();

                    PdfContentByte cb = writer.getDirectContent();
                    PdfTemplate tp = cb.createTemplate(width, height);
                    Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());

                    Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
                    chart.draw(g2, r2D, null);
                    g2.dispose();
                    cb.addTemplate(tp, 0, 0);
                } finally {
                    document.close();
                }
            } finally {
                if (out != null) {
                    out.close();

                }
            }
        }//else: input values not availabel
    }//saveChartToPDF()
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) throws Exception{

        PieChart demo = new PieChart("Pie Chart Demo 1");

        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
