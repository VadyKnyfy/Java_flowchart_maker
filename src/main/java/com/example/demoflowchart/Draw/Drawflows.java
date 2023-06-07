package com.example.demoflowchart.Draw;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Drawflows {
    private static ArrayList<Point2D> ifelemAL = new ArrayList<Point2D>();
    protected static double allheight =0;
    protected static double allwidth =0;
    protected final static double size = 100;
    protected final static double linesize = 25;
    protected final static double height =50;
    private static BaseElem baseElem;
    private Group group = new Group();
    private static Point2D endpoint = new Point2D(0,0);
    private static Point2D elsepoint = new Point2D(0,0);
    public  Drawflows(BaseElem element, String str, Point2D startPoints)  {
        baseElem = element;
        group.getChildren().add(drawEllipse(startPoints.getX(),startPoints.getY(),size/2,height/2,"Початок"));
        endpoint = new Point2D(startPoints.getX(),startPoints.getY()+height/2);
        int i=0;
        for(BaseElem elem: element.getElemArrayList()){
            switch (elem.getType()){
                case "SimpleBlock":{
                    SimpleElement simpleg = new SimpleElement(elem,endpoint);
                    group.getChildren().add(simpleg.getGroup());
                    endpoint = simpleg.getEndpoint();
                    break;
                }
                case "if":{
                    Ifelement ifelement = new Ifelement(elem,0,endpoint);
                    if(getMinMaxX(ifelement.getinsideGroup())[1]>endpoint.getX()-10){
                        double corziz=getMinMaxX(ifelement.getinsideGroup())[1]-(endpoint.getX()-10);
                        ifelement = new Ifelement(elem,corziz,endpoint);
                    }
                    try{
                    if(!(element.getElemArrayList().get(i+1).getType().equals("else") ||
                            element.getElemArrayList().get(i+1).getType().equals("else-if"))){
                        endpoint= ifelement.getEndpoint();
                        elsepoint= ifelement.getElsepoints();
                        Text label = new Text("Ні");
                        label.setX((elsepoint.getX())+10);
                        label.setY((elsepoint.getY())-2);
                        group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                        endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                        group.getChildren().add(createEmptyelseline(elsepoint,endpoint));
                        endpoint = new Point2D(ifelement.getDiamodpoints()[1].getX(),endpoint.getY());
                        group.getChildren().add(ifelement.getGroup());
                        group.getChildren().add(label);
                        group.getChildren().add(ifelement.getinsideGroup());
                    }else{
                        ifelemAL.add(ifelement.getDiamodpoints()[3]);
                        endpoint= ifelement.getEndpoint();
                        group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                        endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                        ifelemAL.add(endpoint);
                        group.getChildren().add(ifelement.getGroup());
                        group.getChildren().add(ifelement.getinsideGroup());
                        elsepoint= ifelement.getElsepoints();
                    }}
                    catch (IndexOutOfBoundsException e){
                        ifelement.ifend(endpoint);
                        endpoint= ifelement.getEndpoint();
                        elsepoint= ifelement.getElsepoints();
                        group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                        endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                        group.getChildren().add(createEmptyelseline(elsepoint,endpoint));
                        endpoint = new Point2D(ifelement.getDiamodpoints()[1].getX(),endpoint.getY());
                        group.getChildren().add(ifelement.getGroup());
                        group.getChildren().add(ifelement.getinsideGroup());
                    }
                    break;
                }
                case "else-if":{
                    Elseifelement elseifelement = new Elseifelement(elem, 0, elsepoint);
                    if(getMinMaxX(elseifelement.getGroup())[0]+10<(elsepoint.getX()+(linesize/2))){
                        double minx = getMinMaxX(elseifelement.getGroup())[0];
                        double corziz=(elsepoint.getX()-minx)+10;
                        elseifelement = new Elseifelement(elem,corziz,elsepoint);
                    }
                    try{
                        if(!(element.getElemArrayList().get(i+1).getType().equals("else") ||
                                element.getElemArrayList().get(i+1).getType().equals("else-if"))){
                            endpoint= elseifelement.getEndpoint();
                            group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                            endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                            ifelemAL.add(endpoint);
                            group.getChildren().add(elseifelement.getGroup());
                            group.getChildren().add((Group)drowIfElseIFElseendlines(ifelemAL).get(0));
                            endpoint =(Point2D)drowIfElseIFElseendlines(ifelemAL).get(1);
                            //group.getChildren().add(createVerticalLine(endpoint.getX(), endpoint.getY(), 100));
                            elsepoint = elseifelement.getElsepoints();
                            ifelemAL.clear();
                        }
                        else{
                            endpoint= elseifelement.getEndpoint();
                            group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                            endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                            double maxxgroup = getMinMaxX(elseifelement.getGroup())[1];
                            elsepoint= elseifelement.getElsepoints();
                            Line elselinefinish = new Line(elsepoint.getX(),elsepoint.getY(),maxxgroup,elsepoint.getY());
                            elsepoint =new Point2D(elselinefinish.getEndX(),elselinefinish.getEndY());
                            group.getChildren().add(elselinefinish);
                            ifelemAL.add(endpoint);
                            group.getChildren().add(elseifelement.getGroup());
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        endpoint = elseifelement.getEndpoint();
                        group.getChildren().add(createVerticalLine(endpoint.getX(), endpoint.getY(), 10));
                        endpoint = new Point2D(endpoint.getX(), endpoint.getY() + 10);
                        ifelemAL.add(endpoint);
                        group.getChildren().add(elseifelement.getGroup());
                        group.getChildren().add((Group) drowIfElseIFElseendlines(ifelemAL).get(0));
                        endpoint = (Point2D) drowIfElseIFElseendlines(ifelemAL).get(1);
                        //group.getChildren().add(createVerticalLine(endpoint.getX(), endpoint.getY(), 100));
                        elsepoint = elseifelement.getElsepoints();
                        ifelemAL.clear();
                    }
                    break;
                }
                case "else":{
                    Elseelement elseelement = new Elseelement(elem, 0, elsepoint);
                    if(getMinMaxX(elseelement.getGroup())[0]+10<(elsepoint.getX()+(linesize/2))){
                        double minx = getMinMaxX(elseelement.getGroup())[0];
                        double corziz=(elsepoint.getX()-minx)+10;
                        System.out.println(corziz);
                        elseelement = new Elseelement(elem,corziz,elsepoint);
                    }
                    endpoint= elseelement.getEndpoint();
                    group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                    endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                    ifelemAL.add(endpoint);
                    group.getChildren().add(elseelement.getGroup());
                    group.getChildren().add((Group)drowIfElseIFElseendlines(ifelemAL).get(0));
                    endpoint =(Point2D)drowIfElseIFElseendlines(ifelemAL).get(1);
                    //group.getChildren().add(createVerticalLine(endpoint.getX(), endpoint.getY(), 100));
                    elsepoint = elseelement.getElsepoints();
                    ifelemAL.clear();
                    break;
                }
                case "while","for":{
                    Whileelement whileelement = new Whileelement(elem,endpoint);
                    endpoint = whileelement.getEndpoint();
                    group.getChildren().add(whileelement.getGroup());
                    break;
                }
            }
            i++;
        }
        group.getChildren().add(createVerticalLine(endpoint.getX(),endpoint.getY(),linesize));
        group.getChildren().add(drawEllipse(endpoint.getX(),endpoint.getY()+linesize*2,size/2,height/2,"Кінець"));
    }

    protected static  ArrayList<Point2D> getIfelemAL(){
        return ifelemAL;
    }

    public static Group drawEllipse(double x, double y, double width, double height, String text) {
        Ellipse ellipse = new Ellipse(x, y, width, height);
        ellipse.setFill(Color.LIGHTGRAY);
        ellipse.setStroke(Color.BLACK);
        Text textNode = new Text(text);
        textNode.setFont(new Font(15));
        textNode.setFill(Color.BLACK);
        textNode.setX(x-width/2);
        textNode.setY(y+height/3);

        // Создаем группу из ромба и текста и возвращаем ее
        Group group = new Group(ellipse, textNode);
        return group;
    }
    protected static Group createDiamond(double x, double y, double size, String text) {
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[]{
                x, y ,
                x + size, y + size/4,
                x , y + size/2,
                x - size, y+(size / 4) });
        diamond.setFill(Color.LIGHTGRAY);
        diamond.setStroke(Color.BLACK);

        // Добавляем текст в центр ромба
        Text textNode = new Text(text);
        textNode.setFont(new Font(15));
        textNode.setFill(Color.BLACK);
        textNode.setX(x  - (textNode.getLayoutBounds().getWidth() / 2));
        textNode.setY(y + (size / 4) + (textNode.getLayoutBounds().getHeight() / 4));

        // Создаем группу из ромба и текста и возвращаем ее
        Group group = new Group(diamond, textNode);
        return group;
    }
    protected static Point2D[] getDiamondPoint(double size, double x, double y) {
        Point2D[] temp = new Point2D[4];
        double bottomX = x;
        double bottomY = y + size/2;
        double leftX = x - size;
        double leftY = y + size/4 ;
        double upperX = x ;
        double upperY = y ;
        double rightX = x + size;
        double rightY = y + size/4 ;
        temp[0]= new Point2D(leftX, leftY);
        temp[1]= new Point2D(upperX, upperY);
        temp[2]= new Point2D(rightX, rightY);
        temp[3]= new Point2D(bottomX, bottomY);
        return temp;
    }
    public static double[] getMinMaxX(Group group) {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (Node node : group.getChildren()) {
            Bounds bounds = node.localToScene(node.getBoundsInLocal());
            double nodeMinX = bounds.getMinX();
            double nodeMaxX = bounds.getMaxX();
            if (nodeMinX < minX) {
                minX = nodeMinX;
            }
            if (nodeMaxX > maxX) {
                maxX = nodeMaxX;
            }
        }
        return new double[]{minX, maxX};
    }
    public static double[] getMinMaxY(Group group) {
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for (Node node : group.getChildren()) {
            Bounds bounds = node.localToScene(node.getBoundsInLocal());
            double nodeMinY = bounds.getMinY();
            double nodeMaxY = bounds.getMaxY();
            if (nodeMinY < minY) {
                minY = nodeMinY;
            }
            if (nodeMaxY > maxY) {
                maxY = nodeMaxY;
            }
        }
        return new double[]{minY, maxY};
    }
    protected static ArrayList<Object> drowIfElseIFElseendlines(ArrayList<Point2D> ifelemAL){
        double endpointifblockX = ifelemAL.get(0).getX();
        Group group1 = new Group();
        //find max "y";
        double maxY = getMaxY(ifelemAL);
        //
        for(int i =1;i<ifelemAL.size();i++){
            System.out.println(maxY-ifelemAL.get(i).getY());
                group1.getChildren().add(createVerticalLine(ifelemAL.get(i).getX(),ifelemAL.get(i).getY(),maxY-ifelemAL.get(i).getY()));
        }
        group1.getChildren().add(createHorizontalLine(ifelemAL.get(ifelemAL.size()-1).getX(),maxY,(ifelemAL.get(ifelemAL.size()-1).getX()-ifelemAL.get(1).getX())*-1));
        ArrayList<Object> alt = new ArrayList<Object>();
        alt.add(group1);
        alt.add(new Point2D(endpointifblockX,maxY));
        return alt;
    }
    private static double getMaxY(ArrayList<Point2D> point2DS) {
        double maxY = Double.MIN_VALUE;
        for(int i =1;i<point2DS.size();i++){
            //System.out.println(point.getY());
            if (point2DS.get(i).getY() > maxY) {
                maxY = point2DS.get(i).getY();
            }
        }
        return maxY;
    }
    protected static BaseElem getBaseElement(){
    return baseElem;
    }
    private static ArrayList<Point2D> broken (ArrayList<Drawflows> al){
       ArrayList<Point2D> point2DS = new ArrayList<Point2D>();
        for(Drawflows draw: al){
            System.out.println("method +" + draw.getBaseElement().getType());
            point2DS.add(draw.getEndpoint());
        }
        return point2DS;
    }
    public Drawflows(BaseElem sBlock, Point2D startsPoints){
    }

    public Drawflows() {
    }

    public Group getGroup(){
        return group;
    }
    protected static Group makeendline(Point2D endpoint){
        Group endline = new Group();
        Line line1 = createVerticalLine( endpoint.getX(),endpoint.getY(),linesize);
        Line line2 = createHorizontalLine(line1.getEndX(),line1.getEndY(),(linesize+size/2));
        endline.getChildren().addAll(line2,line1);
        endpoint = new Point2D(line2.getEndX(),line2.getEndY());
        return endline;
    }
    protected static Group createEmptyelseline( Point2D elsepoint,Point2D endpoint){
        Line line1 = new Line(elsepoint.getX(),elsepoint.getY(),elsepoint.getX(),endpoint.getY());
        Line line2 = new Line(line1.getEndX(),line1.getEndY(),endpoint.getX(),line1.getEndY());
        return new Group(line1,line2);
    }
    protected static Line createVerticalLine(double x, double y, double size) {
        Line line = new Line(x, y, x , y + size );
        return line;
    }
    protected static Line createHorizontalLine(double x, double y, double size) {
        Line line = new Line(x, y, x + size, y );
        return line;
    }

    public Point2D getEndpoint() {
        return endpoint;
    }
    public Point2D getElsepoints(){
        return elsepoint;
    }
}
