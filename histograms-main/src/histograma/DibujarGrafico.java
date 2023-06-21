package histograma;

import java.awt.Color;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class DibujarGrafico {

    /**
     * Crea un gráfico de barras y lo asigna al JPanel recibido
     *
     * @param histograma       histograma de frecuencias (int[256]).
     * @param jPanelHistograma JPanel donde el histograma será dibujado
     * @param colorBarras      color de cuál será dibujado el histograma
     */
    public void crearHistograma(int[] histograma, JPanel jPanelHistograma, Color colorBarras) {
        //Creamos el dataSet y añadimos el histograma
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String serie = "Numero de pixeles";
        for (int i = 0; i < histograma.length; i++) {
            dataset.addValue(histograma[i], serie, "" + i);
        }
        //Creamos el chart
        JFreeChart chart = ChartFactory.createBarChart("Frecuencia del Histograma", null, null,
                dataset, PlotOrientation.VERTICAL, true, true, false);
        //Modificamos el diseño del chart

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, colorBarras);
        chart.setAntiAlias(true);

        chart.setBackgroundPaint(new Color(214, 217, 223));
        jPanelHistograma.removeAll();

        jPanelHistograma.repaint();
        jPanelHistograma.setLayout(new java.awt.BorderLayout());
        jPanelHistograma.add(new ChartPanel(chart));
        jPanelHistograma.validate();
    }
}
