package com.example.demoflowchart.Draw;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class SimpleElement extends  Drawflows {
    private Group group = new Group();
    private static Point2D endpoint = new Point2D(0,0);

    public SimpleElement(BaseElem sBlock, Point2D startsPoints) {
    if(!sBlock.getLine().startsWith("-(add")){
     if(sBlock.getLine().startsWith("return")){
        String line = sBlock.getLine();
        char[] chars = line.toCharArray();
        String inside ="";
        int i=7;
        while (i<line.length()-1){
            inside+= chars[i];
            i++;
        }
        Line line2 = createVerticalLine(startsPoints.getX(),startsPoints.getY(),linesize);
         group.getChildren().add(line2);
        group.getChildren().add(drawEllipse(line2.getEndX(),line2.getEndY()+linesize,size/2,height/2,inside));
        endpoint = new Point2D(startsPoints.getX(),startsPoints.getY()+linesize+height);
    }
    else {
         group.getChildren().add(createRectangle(startsPoints.getX(),startsPoints.getY(),height,size,sBlock.getLine()));
         endpoint = new Point2D(startsPoints.getX(),startsPoints.getY()+linesize+height);
     }}else{
            String line = sBlock.getLine();
            char[] chars = line.toCharArray();
            String inside ="";
            int i=6;
            while (i<line.length()-1){
                inside+= chars[i];
                i++;
            }
            group.getChildren().add(createPalalelo(startsPoints.getX(),startsPoints.getY(),height,size,inside));
            endpoint = new Point2D(startsPoints.getX(),startsPoints.getY()+linesize+height);
        }

    }
    private static Group createPalalelo(double x, double y, double h1, double h2, String text) {
        double width = Math.abs(h2);
        double height = Math.abs(h1);
        Line startline = createVerticalLine(x,y,linesize);
        x=startline.getEndX()-width/2;
        y =startline.getEndY();
        Polygon palalelo = new Polygon();
        palalelo.getPoints().addAll(new Double[]{
                x, y ,
                x + width + width/4, y,
                x + width,y +height,
                x - width/4 , y + height});
        palalelo.setFill(Color.LIGHTGRAY);
        palalelo.setStroke(Color.BLACK);
        Text textNode = new Text(text);
        textNode.setFont(new Font(15));
        textNode.setFill(Color.BLACK);
        textNode.setX(x + (width / 2) - (textNode.getLayoutBounds().getWidth() / 2));
        textNode.setY(y + (height / 2) + (textNode.getLayoutBounds().getHeight() / 4));

        // Создаем группу из прямоугольника и текста и возвращаем ее
        Group group = new Group(palalelo, textNode,startline);
        return group;
    }
    private static Group createRectangle(double x, double y, double h1, double h2, String text) {
        double width = Math.abs(h2);
        double height = Math.abs(h1);
        Line startline = createVerticalLine(x,y,linesize);
        x=startline.getEndX()-width/2;
        y =startline.getEndY();
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.BLACK);

        // Добавляем текст в центр прямоугольника
        Text textNode = new Text(text);
        textNode.setFont(new Font(15));
        textNode.setFill(Color.BLACK);
        textNode.setX(x + (width / 2) - (textNode.getLayoutBounds().getWidth() / 2));
        textNode.setY(y + (height / 2) + (textNode.getLayoutBounds().getHeight() / 4));

        // Создаем группу из прямоугольника и текста и возвращаем ее
        Group group = new Group(rectangle, textNode,startline);
        return group;
    }
    public Group getGroup(){
        return group;
    }
    public Point2D getEndpoint() {
        return endpoint;
    }
    
}
