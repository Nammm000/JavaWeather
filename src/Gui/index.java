                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  package Gui;

import Controller.HandleApi;
import data.JsonResult;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class index {
    private GDND weather;
    private JsonResult result;
    private int idx = 0;
    private int n = 40;
    private List<ChartFrame> list = new ArrayList<ChartFrame>();
    
    public index(){
        weather = new GDND();
        prepare();
    }
    
    public GDND getWeather() {
        return weather;
    }
    
    private void prepare() {
        weather.getBtn_find().setActionCommand("find");
        weather.getBtn_find().addActionListener(new ButtonClick());
        weather.value().addKeyListener(new KeyPressed());

        weather.getBtn_next().setActionCommand("next");
        weather.getBtn_next().addActionListener(new ButtonClick());

        weather.getBtn_pre().setActionCommand("pre");
        weather.getBtn_pre().addActionListener(new ButtonClick());
    }
    
    private void show() {
        int a = (int)result.getList()[idx].getMain().getTemp();
        a = a-273;
        String date = " Ngày: " + result.getList()[idx].getDt_txt();
        String location = " Thành phố: " + result.getCity().getName() + " - " + result.getCity().getCountry();
        //String nation = " Quốc gia: " + result.getCity().getCountry();
        String weath = " Thời tiết: " + result.getList()[idx].getWeather()[0].getDescription();
        String nhietDo = " Nhiệt độ: " + a/*result.getList()[idx].getMain().getTemp()*/ + " °C";
        String apXuat = " Ap suất: " + result.getList()[idx].getMain().getPresure();
        String doAm = " Độ ẩm: " + result.getList()[idx].getMain().getHumidity() +"%";
        String winSpeed = " Tốc độ gió: " + result.getList()[idx].getWind().getSpeed();
        String clouds = " Mây: " + result.getList()[idx].getClouds().getAll() + "%";
        
        weather.getLb_apxuat().setText(apXuat);
        weather.getLb_city().setText(location);
        weather.getLb_cloud().setText(clouds);
        weather.getLb_date().setText(date);
        weather.getLb_doam().setText(doAm);
        //weather.getLb_nation().setText(nation);
        weather.getLb_nhietdo().setText(nhietDo);
        weather.getLb_thoitiet().setText(weath);
        weather.getLb_tocdogio().setText(winSpeed);

        //LineChartExample.startLineChart();
        LineChartExample();

    }

    public void LineChartExample() {

        // Create dataset
        DefaultCategoryDataset dataset = createDataset();
        // Create chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Nhiệt độ theo thời gian", // Chart title
                "Thời gian", // X-Axis Label
                "Nhiệt độ", // Y-Axis Label
                dataset
        );

        /*ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);*/


        ChartFrame frame = new ChartFrame("Nhiệt độ theo thời gian", chart);
        list.add(frame);
        int i = list.size();
        if(i==2){
            list.get(0).dispose();
            list.remove(0);
        }
        frame.setSize(600, 400);
        //frame.dispose();
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(1000, 100, 600, 400);
        frame.setVisible(true);

    }

    private DefaultCategoryDataset createDataset() {

        String series1 = "Nhiệt độ theo thời gian";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < n; i++) {
            int a = (int)result.getList()[i].getMain().getTemp();
            a = a-273;
            String time = (result.getList()[i].getDt_txt()).split(" ", 2)[1];
            /*String hour = time.split(":", 2)[0];
            int numberHour = Integer.parseInt(hour);*/
            System.out.println(a+" " + time);
            dataset.addValue(a, series1, time);
        }

        return dataset;
    }

    public class ButtonClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String command = ae.getActionCommand();
            switch (command) {
                case "find":
                    find();
                    idx = 0;
                    break;
                case "next":
                    idx = idx == 39 ? idx : idx + 1;
                    break;
                case "pre":
                    idx = idx == 0 ? idx : idx - 1;
                    break;
                default:
                    break;
            }
            show();
        }

        private void find() {
            result = HandleApi.getJsonData((String) weather.value().getText());
        }
    }
    
    public class KeyPressed implements KeyListener {

        @Override
        public void keyPressed(KeyEvent ae) {
            if (ae.getKeyCode()==KeyEvent.VK_ENTER) {
                find();
                idx = 0;
                show();
            }
        }
        
        @Override
        public void keyReleased(KeyEvent arg0) {
            // TODO Auto-generated method stub

        }
        
        @Override
        public void keyTyped(KeyEvent arg0) {

        }

        private void find() {
            result = HandleApi.getJsonData((String) weather.value().getText());
        }
    }
}