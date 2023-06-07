package com.example.demoflowchart;
import com.example.demoflowchart.Draw.BaseElem;
import com.example.demoflowchart.Draw.Drawflows;
import com.example.demoflowchart.Draw.Ifelement;
import com.example.demoflowchart.Draw.SimpleElement;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //////
        BaseElem baseElem = new BaseElem("C:\\Users\\vadyk\\Desktop\\myproject\\demoflowchart\\src\\main\\resources\\test.txt");
        Drawflows drawflows = new Drawflows(baseElem,"0",new Point2D(300,30));
        double minX = Drawflows.getMinMaxX(drawflows.getGroup())[0];
        if(minX<-100){
            drawflows = new Drawflows(baseElem,"0",new Point2D(minX+1000,10));
        }
        Group onte = drawflows.getGroup();
        double maxX = Drawflows.getMinMaxX(onte)[1]+100;
        double maxY = Drawflows.getMinMaxY(onte)[1]+100;

        /*/////
        double size = 100;
        double x = 600;
        double y = 250;
        Point2D startPoints = new Point2D(250,250);
        Group test = new Group();
        Line startline = createVerticalLine(startPoints.getX(),startPoints.getY(),50);
        Group diamond = createDiamond(startline.getEndX(), startline.getEndY(),100,"true");
        Point2D[] diamodpoints = getDiamondPoint(100,startline.getEndX(),startline.getEndY());
        Line ifline = createHorizontalLine(diamodpoints[0].getX(),diamodpoints[0].getY(),50);
        Line elseline = createHorizontalLine(diamodpoints[2].getX(),diamodpoints[2].getY(),-50);
        Point2D endpoint = new Point2D(elseline.getStartX(),elseline.getEndY());
        Group test2 = new Group();
        Line startline2 = createVerticalLine(endpoint.getX(),endpoint.getY(),50);
        Group diamond2 = createDiamond(startline2.getEndX(), startline2.getEndY(),100,"true");
        Point2D[] diamodpoints2 = getDiamondPoint(100,startline2.getEndX(),startline2.getEndY());
        Line ifline2 = createHorizontalLine(diamodpoints2[0].getX(),diamodpoints2[0].getY(),50);
        Line elseline2 = createHorizontalLine(diamodpoints2[2].getX(),diamodpoints2[2].getY(),-50);
        test2.getChildren().addAll(diamond2,startline2,ifline2,elseline2);
        test.getChildren().addAll(diamond,startline,ifline,elseline,test2);
        //////////////////////////////////

        

        Group diamond1 = createDiamond(x, y, size, "first");
        Point2D[] points = getDiamondPoint(size, x,y);
        Line line = createHorizontalLine(points[0].getX(),points[0].getY(),size);
        Line line2 = createVerticalLine(line.getStartX(), line.getEndY(), size);
        Line linen1 = createHorizontalLine(points[2].getX(),points[2].getY(),size);
        Line linen2 = createVerticalLine(linen1.getEndX(), linen1.getEndY(), size*3+size/2);
        Line linen3 = createHorizontalLine(linen2.getEndX(),linen2.getEndY(),(size+size/2)*-1);
        diamond2 = createDiamond(line2.getEndX()-size/2, line2.getEndY(), size, "second");
        points = getDiamondPoint(size, line2.getEndX()-size/2,line2.getEndY());
        Line line3 = createVerticalLine(points[3].getX(),points[3].getY(),size);
        Line line4 = createHorizontalLine(line3.getEndX(),line3.getEndY(),(size+size/2)*-1);
        Line line5 = createVerticalLine(line4.getEndX(),line4.getStartY(),(size+size/2)*-1);
        Line line6 = createHorizontalLine(line5.getEndX(),line5.getEndY(),(size));
        Line line7 = createHorizontalLine(points[2].getX(),points[2].getY(),(size/2));
        Line line8 = createVerticalLine(line7.getEndX(),line7.getStartY(),(size+size/2+size/4));
        Line line9 = createHorizontalLine(line8.getEndX(),line8.getEndY(),(size)*-1);
        Line line10 = createVerticalLine(line9.getEndX(),line9.getStartY(),size/4);
        Line line11 = createHorizontalLine(line10.getEndX(),line10.getEndY(),(size+size/2));
        Line line12 = createVerticalLine(line11.getEndX(),line11.getStartY(),size/4);
        Group rectangle = createRectangle(line12.getEndX()-50,line12.getEndY(),50,100,"прямоугольник");
        Point2D point2D = new Point2D(line12.getEndX()-50, line12.getEndY());
        Group svastik = new Group();
        Group retest = new SimpleElement(new BaseElem(),point2D).getGroup();
        ArrayList<Drawflows> testes = new ArrayList<Drawflows>();
        testes.add(new SimpleElement(new BaseElem(),point2D));
        testes.add(new SimpleElement(new BaseElem(),points[0]));
        retest = testes.get(0).getGroup();
        Point2D testpo = testes.get(0).getEndpoint();
        Line line13 = createVerticalLine(testpo.getX(),testpo.getY(),200);
        // Добавляем все фигуры на группу*/
        Group group = new Group(onte);
        // Создаем сцену и добавляем группу на нее
        Scene scene = new Scene(group, maxX, maxY, Color.WHITE);
        File outputFile = new File("snapshot1.png");

        WritableImage snapshot = scene.snapshot(null);
        PixelReader pixelReader = snapshot.getPixelReader();

        int width = (int) snapshot.getWidth();
        int height = (int) snapshot.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                int alpha = (int) (color.getOpacity() * 255);
                int red = (int) (color.getRed() * 255);
                int green = (int) (color.getGreen() * 255);
                int blue = (int) (color.getBlue() * 255);
                pixels[y * width + x] = (alpha << 24) | (red << 16) | (green << 8) | blue;
            }
        }

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setRGB(0, 0, width, height, pixels, 0, width);

        ImageIO.write(bufferedImage, "png", outputFile);
        // Отображаем сцену
        stage.setScene(scene);
        stage.show();
    }
    public static Group createRectangle(double x, double y, double h1, double h2, String text) {
        double width = Math.abs(h2);
        double height = Math.abs(h1);
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.BLACK);
        // Добавляем текст в центр прямоугольника
        Text textNode = new Text(text);
        textNode.setFont(new Font(20));
        textNode.setFill(Color.BLACK);
        textNode.setX(x + (width / 2) - (textNode.getLayoutBounds().getWidth() / 2));
        textNode.setY(y + (height / 2) + (textNode.getLayoutBounds().getHeight() / 4));

        // Создаем группу из прямоугольника и текста и возвращаем ее
        Group group = new Group(rectangle, textNode);
        return group;
    }
    public static Group createDiamond(double x, double y, double size, String text) {
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[]{
                x, y ,
                x + (size / 2), y + size/2,
                x , y + size,
                x - (size / 2), y+(size / 2) });
        diamond.setFill(Color.LIGHTGRAY);
        diamond.setStroke(Color.BLACK);

        // Добавляем текст в центр ромба
        Text textNode = new Text(text);
        textNode.setFont(new Font(15));
        textNode.setFill(Color.BLACK);
        textNode.setX(x  - (textNode.getLayoutBounds().getWidth() / 2)); 
        textNode.setY(y + (size / 2) + (textNode.getLayoutBounds().getHeight() / 4));

        // Создаем группу из ромба и текста и возвращаем ее
        Group group = new Group(diamond, textNode);
        return group;
    }
    public static Point2D[] getDiamondPoint(double size, double x, double y) {
        Point2D[] temp = new Point2D[4];
        double bottomX = x + size/2 ;
        double bottomY = y + size;
        double leftX = x - size;
        double leftY = y + size/2 ;
        double upperX = x - size/2 ;
        double upperY = y ;
        double rightX = x + size ;
        double rightY = y + size/2 ;
        temp[0]= new Point2D(leftX, leftY);
        temp[1]= new Point2D(upperX, upperY);
        temp[2]= new Point2D(rightX, rightY);
        temp[3]= new Point2D(bottomX, bottomY);
        return temp;
    }
    public static Line createVerticalLine(double x, double y, double size) {
        Line line = new Line(x, y, x , y + size );
        return line;
    }
    public static Line createHorizontalLine(double x, double y, double size) {
        Line line = new Line(x, y, x + size, y );
        return line;
    }
    public static void main(String[] args) {

        launch(args);

    }
}