/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import java.awt.*;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ankur
 */
public class DashboardPanel extends JPanel {

    private ProductDAO productDAO;

    private JLabel totalStockValueLabel;
    //private JLabel lowStockCountLabel;
    private JLabel totalItemsLabel;

    private ChartPanel chartPanel;

    public DashboardPanel(ProductDAO productDAO) {
        this.productDAO = productDAO;

        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // KPI panels container
        JPanel kpiPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        kpiPanel.setOpaque(false);

        totalStockValueLabel = createKpiLabel();
        //lowStockCountLabel = createKpiLabel();
        totalItemsLabel = createKpiLabel();

        kpiPanel.add(wrapInBox(totalStockValueLabel, "Total Stock Value"));
        //kpiPanel.add(wrapInBox(lowStockCountLabel, "Low Stock Items"));
        kpiPanel.add(wrapInBox(totalItemsLabel, "Total Category Items"));

        // Chart panel for visualization
        chartPanel = createChartPanel(); // Assign to class-level variable
        add(kpiPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        loadDashboardData(); // Load KPIs
    }

    private JLabel createKpiLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    private JPanel wrapInBox(JLabel label, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new TitledBorder(title),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private ChartPanel createChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Product> products = productDAO.getAllProducts();
        Map<String, Integer> categoryQuantities = new LinkedHashMap<>();

        for (Product p : products) {
            String category = p.getCategory();
            if (category == null || category.trim().isEmpty()) {
                category = "Unknown";
            }

            categoryQuantities.put(
                    category,
                    categoryQuantities.getOrDefault(category, 0) + p.getQuantity()
            );
        }

        for (Map.Entry<String, Integer> entry : categoryQuantities.entrySet()) {
            dataset.addValue(entry.getValue(), "Quantity", entry.getKey());
        }

        JFreeChart categoryChart = ChartFactory.createBarChart(
                "Stock Quantity by Category",
                "Category",
                "Quantity",
                dataset
        );

        CategoryPlot plot = categoryChart.getCategoryPlot();
        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4)
        );

        // Custom renderer for colored bars
        BarRenderer renderer = new BarRenderer() {
            private final Paint[] colors = new Paint[]{
                    new Color(79, 129, 189),  // Blue
                    new Color(192, 80, 77),   // Red
                    new Color(155, 187, 89),  // Green
                    new Color(128, 100, 162), // Purple
                    new Color(247, 150, 70),  // Orange
                    new Color(75, 172, 198),  // Teal
                    Color.GRAY, Color.MAGENTA, Color.PINK
            };

            @Override
            public Paint getItemPaint(int row, int column) {
                return colors[column % colors.length];
            }
        };

        plot.setRenderer(renderer);

        ChartPanel panel = new ChartPanel(categoryChart);
        panel.setPreferredSize(new Dimension(600, 350));
        return panel;
    }

    private void loadDashboardData() {
        List<Product> products = productDAO.getAllProducts();

        double totalStockValue = 0;
        int lowStockCount = 0;
        int totalItems = products.size();

        for (Product p : products) {
            totalStockValue += p.getQuantity() * p.getPrice();

            if (p.getQuantity() < 10) {
                lowStockCount++;
            }
        }

        totalStockValueLabel.setText(String.format("$%.2f", totalStockValue));
        //lowStockCountLabel.setText(String.valueOf(lowStockCount));
        totalItemsLabel.setText(String.valueOf(totalItems));
    }
}