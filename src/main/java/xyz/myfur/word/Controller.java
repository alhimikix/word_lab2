package xyz.myfur.word;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller implements Initializable {

    int startX = 0, endX = 100;
    int startY = 0, endY = 100;

    @FXML
    private Pane centerPane;

    @FXML
    private Canvas canvas;

    Point[] ALetter = {
            new Point(4,2,true),
            new Point(6,2),
            new Point(9,10),
            new Point(7,10),
            new Point(6,7),
            new Point(4,7),
            new Point(3,10),
            new Point(3,10),
            new Point(1,10),
            new Point(4,2),
            new Point(5,3,true),
            new Point(6,5),
            new Point(4,5),
            new Point(5,3),
    };

    Point[] OLetter = {
            new Point(4,2,true),
            new Point(6,2),
            new Point(7,3),
            new Point(7,9),
            new Point(6,10),
            new Point(4,10),
            new Point(3,9),
            new Point(3,3),
            new Point(4,2),

            new Point(5,3,true),
            new Point(6,5),
            new Point(6,8),
            new Point(5,9),
            new Point(4,8),
            new Point(4,4),
            new Point(5,3),

    };

    public void drawA() {
        int x = startX,
                y = startY,
                height = endX - startX,
                width = endY - startY;

        drawLine(ALetter,x,y,height,width);

    }

    public void draw0(){
        int  x = startX + 100,
                y = startY ,
                height = endX - startX,
                width = endY - startY;
        drawLine(OLetter,x,y,height,width);
    }

    private void drawLine(Point[] points,int x,int y,int height,int width){
        for (Point point : points) {
            if (point.isStart()){
                newLine(x + getCord(point.getX(), width),y + getCord(point.getY(), height));
            }else {
                line(x + getCord(point.getX(), width),y + getCord(point.getY(), height));
            }
        }
    }

    int lineXStart,lineYStart;
    boolean started = false;

    private void lineStart(int x,int y){
        lineXStart = x;
        lineYStart = y;
        started = true;
    }

    private void newLine(int x,int y){
        lineStart(x,y);
        line(x,y);
    }

    private void line(int x,int y){
        line_raw(canvas.getGraphicsContext2D(),lineXStart,lineYStart,x,y);
        lineStart(x,y);
    }

    private void line_raw(GraphicsContext g, int x1, int y1, int x2, int y2) {
        int x = x1, y = y1;
        int dx = Math.abs(x2-x1), dy = Math.abs(y2-y1);
        int sx = (x2-x1)>0?1:((x2-x1)==0?0:-1);
        int sy = (y2-y1)>0?1:((y2-y1)==0?0:-1);
        int tx, ty;
        if (dx>=dy) {
            tx = sx; ty = 0;
        } else {
            int z=dx; dx=dy; dy=z;
            tx=0; ty=sy;
        }
        int scount = 2*dy;
        int count = scount-dx;
        int dcount = count-dx;
        for(;;) {
            dx-=1;
            if (dx<-1) break;
            g.strokeLine(x, y, x, y);
            if (count>=0) {
                x+=sx; y+=sy;
                count += dcount;
            } else {
                x+=tx; y+=ty;
                count += scount;
            }
        }
    }

    private int getCord(int x, int v) {
        return (v / 10) * x;
    }



    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas.widthProperty().bind(centerPane.widthProperty());
        canvas.heightProperty().bind(centerPane.heightProperty());
        canvas.widthProperty().addListener(e -> draw());
        canvas.heightProperty().addListener(e -> draw());
    }

    public void saveToFile() {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        String filename = JOptionPane.showInputDialog("Enter filename!");
        try {
            ImageIO.write(renderedImage, "png", new File("./" + filename + ".png"));
        } catch (Throwable ignored) {
        }
    }

    public void draw() {
        drawA();
        draw0();
    }

}
