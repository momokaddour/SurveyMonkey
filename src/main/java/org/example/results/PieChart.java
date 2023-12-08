package org.example.results;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.example.questions.AbstractQuestion;
import org.example.questions.MultipleChoiceQuestion;
import org.example.questions.Question;
import org.ibex.nestedvm.util.Seekable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PieChart extends AbstractResult implements Result{

    @ElementCollection
    private Map<String, Integer> answerCount;

    private String question;
    private Integer questionID;
    private int numAnswers;

    /**
     * Constructor that takes in the question and initializes map.
     *
     * @param question
     * */
    public PieChart(MultipleChoiceQuestion question)
    {
        this.question = question.getQuestion();
        this.questionID = question.getId();
        this.numAnswers = 0;
        this.answerCount = new HashMap<>();
        for (String s : question.getChoices()) {
            answerCount.put(s, 0);
        }
    }

    /**
     * Default Constructor
     * */
    public PieChart() {
        this.answerCount = new HashMap<>();
    }

    /**
     * Adds a response to the map of String s
     *
     * @param s
     * */
    @Override
    public void addResponse(String s) {
        answerCount.put(s, answerCount.get(s) + 1);
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
    public void setQuestion(Question question) {
        this.question = question.toString();
    }

    /**
     * Getter for the map holding the count for each result
     *
     * @return Map of answers mapped to the amount of time that option was chosen
     * */
    public Map<String, Integer> getAnswerCount()
    {
        return this.answerCount;
    }

    public String createChart() {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry: answerCount.entrySet()) {
            pieDataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(question, pieDataset);

        pieChart.setBackgroundPaint(Color.WHITE);

        File file = new File("src/main/resources/public/" + questionID + "_PieChart.png");
        BufferedImage bufferedImage = pieChart.createBufferedImage(1000, 1000);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }

        System.out.println(questionID + "_PieChart.png saved");

        return questionID + "_PieChart.png";
    }

}
