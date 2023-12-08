package org.example.results;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import org.example.questions.NumberRangeQuestion;
import org.example.questions.Question;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Histogram extends AbstractResult implements Result{
    @ElementCollection
    private Map<String, Integer> answerCount;
    private String question;

    private int questionID;

    private int range;

    private int min;

    private int max;

    /**
     * Constructor that takes in the question to initializes the map and String question
     *
     * @param q the question
     * */
    public Histogram(NumberRangeQuestion q)
    {
        this.question = q.getQuestion();
        this.answerCount = new HashMap<>();
        this.questionID = q.getId();
        this.range = q.getRange();
        this.min = q.getMinNumber();
        this.max = q.getMaxNumber();
    }

    /**
     * Default constructor
     * */
    public Histogram()
    {
        this.answerCount = new HashMap<>();
    }

    /**
     * Adds response to map or updates the response count if it already exists.
     *
     * @param s to add to map
     * */
    @Override
    public void addResponse(String s) {
        if (this.answerCount.containsKey(s))
        {
            this.answerCount.put(s, this.answerCount.get(s) + 1);
        }
        else
        {
            this.answerCount.put(s, 1);
        }
    }

    /**
     * Getter for the question associated with the result
     *
     * @return String
     * */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for the question associated with the result
     *
     * @param question
     * */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets map for answers and how many times they are chosen
     *
     * @return map
     * */
    public Map<String, Integer> getAnswerCount() {
        return answerCount;
    }

    @Override
    public String createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<Integer> keySetToInt = new ArrayList<>();

        for (int x = min; x <= max; x += range)
        {

            int count = 0;

            for (String s : answerCount.keySet())
            {
                int i = Integer.parseInt(s);

                System.out.println("i: " + i);

                if ((i >= x) && (i < (x + range)))
                {
                    System.out.println("x: " + x + "\ni: " + i);
                    count += answerCount.get(s);
                }
            }

            dataset.addValue(count, "Frequency", Integer.toString(x));

            if ((max - x) < 5)
            {
                dataset.addValue(count, "Frequency", Integer.toString(max));
                break;
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(question,
                "Number Range", "Frequency", dataset, PlotOrientation.VERTICAL,
                false, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickUnit(new NumberTickUnit(1));

        File file = new File("src/main/resources/public/" + questionID + "_Histogram.png");
        BufferedImage bufferedImage = chart.createBufferedImage(1000, 1000);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
        System.out.println(questionID + "_Histogram.png saved");

        return questionID + "_Histogram.png";
    }
}
