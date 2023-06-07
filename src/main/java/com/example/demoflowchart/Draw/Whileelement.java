package com.example.demoflowchart.Draw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Whileelement extends Drawflows{
    private static Point2D absoluteendpoints; //useless
    private static Point2D absoluteelsepoints; //useless
    private static ArrayList<Point2D> ifelemAL = new ArrayList<Point2D>();
    private static Group insideGroup = new Group();
    private static Point2D endpoint = new Point2D(0,0);
    private static Point2D elsepoint = new Point2D(0,0);
    private Group group = new Group();
    private static BaseElem baseElem;
    private Point2D[] diamodpoints;
    public static Point2D getAbsoluteelsepoints() {
        return absoluteelsepoints;
    }

    public static Point2D getAbsoluteendpoints() {
        return absoluteendpoints;
    }
    private Whileelement(Point2D ifpoint){

    }
    public Whileelement(BaseElem element, Point2D startPoints) {
        ArrayList<Point2D> ifelemAL = new ArrayList<Point2D>();
        Group insideGroup = new Group();
        Group group = new Group();
         baseElem = element;
         Line startline = createVerticalLine(startPoints.getX(),startPoints.getY(),linesize);
         Group diamond = createDiamond(startline.getEndX(), startline.getEndY(),size,element.getCondition());
        Point2D[] diamodpoints = getDiamondPoint(size,startline.getEndX(),startline.getEndY());
        Point2D endpoint  = diamodpoints[3];
        Point2D elsepoint= diamodpoints[2];
         Group startGroup = new Group(diamond,startline);
         group.getChildren().add(startGroup);
        Point2D absoluteelsepoints=elsepoint;
        int i =0;
        for(BaseElem elem: element.getElemArrayList()){
            switch (elem.getType()){
                case "SimpleBlock":{
                    SimpleElement simpleg = new SimpleElement(elem,endpoint);
                    group.getChildren().add(simpleg.getGroup());
                    endpoint = simpleg.getEndpoint();
                    break;
                }
                case "if":{
                    System.out.println("ifelemside");
                    Ifelement ifelement = new Ifelement(elem,0,endpoint);
                    if(getMinMaxX(ifelement.getinsideGroup())[1]>endpoint.getX()-10){
                        double corziz=getMinMaxX(ifelement.getinsideGroup())[1]-(endpoint.getX()-10);
                        System.out.println(corziz);
                        ifelement = new Ifelement(elem,corziz,endpoint);
                    }
                    try{
                        if(!(element.getElemArrayList().get(i+1).getType().equals("else") ||
                                element.getElemArrayList().get(i+1).getType().equals("else-if"))){
                            endpoint= ifelement.getEndpoint();
                            elsepoint= ifelement.getElsepoints();
                            Text label2 = new Text("Ні");
                            label2.setX((elsepoint.getX())+10);
                            label2.setY((elsepoint.getY())-2);
                            group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                            endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                            group.getChildren().add(createEmptyelseline(elsepoint,endpoint));
                            endpoint = new Point2D(ifelement.getDiamodpoints()[1].getX(),endpoint.getY());
                            group.getChildren().add(ifelement.getGroup());
                            group.getChildren().add(label2);
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
                        endpoint= ifelement.getEndpoint();
                        group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                        endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                        Text label2 = new Text("Ні");
                        label2.setX((ifelement.getElsepoints().getX())+10);
                        label2.setY((ifelement.getElsepoints().getY())-2);
                        group.getChildren().add(createEmptyelseline(ifelement.getElsepoints(),endpoint));
                        group.getChildren().add(label2);
                        endpoint = new Point2D(ifelement.getDiamodpoints()[1].getX(),endpoint.getY());
                        group.getChildren().add(ifelement.getGroup());
                    }
                    break;
                }
                case "else-if":{
                    Elseifelement elseifelement = new Elseifelement(elem, 0, elsepoint);
                    if(getMinMaxX(elseifelement.getGroup())[0]<(elsepoint.getX()+(linesize/2))){
                        double minx = getMinMaxX(elseifelement.getGroup())[0];
                        double corziz=(elsepoint.getX()-minx)+10;
                        System.out.println(corziz);
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
                            System.out.println("maxxelif-"+ maxxgroup);
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
                    //getChildren().add(createVerticalLine(endpoint.getX(), endpoint.getY(), 100));
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
            i ++;}
            ObservableList<Node> newChildrenList = FXCollections.observableArrayList();
            for (Node child : group.getChildren()) {
                if (child != startGroup) {
                    newChildrenList.add(child);
                }
            }
            insideGroup = new Group(newChildrenList);
            this.insideGroup = insideGroup;
            Line line1 = createVerticalLine(endpoint.getX(),endpoint.getY(),10);
            endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
            double minx = getMinMaxX(insideGroup)[0]-10;
            if(minx>diamodpoints[0].getX()) minx = diamodpoints[0].getX()-10;
            double maxx = getMinMaxX(insideGroup)[1]+10;
            if(maxx<diamodpoints[2].getX()) maxx = diamodpoints[2].getX()+10;
            Line line2 = new Line(endpoint.getX(),endpoint.getY(),minx,endpoint.getY());
            Line line3 = new Line(minx,endpoint.getY(),minx,diamodpoints[0].getY());
            Line line4 = new Line(minx,diamodpoints[0].getY(),diamodpoints[0].getX(),diamodpoints[0].getY());
            Line line5 = new Line(diamodpoints[2].getX(),diamodpoints[2].getY(),maxx,diamodpoints[2].getY());
            Line line6 = new Line(maxx,diamodpoints[2].getY(),maxx,endpoint.getY()+10);
            Line line7 = new Line(maxx,endpoint.getY()+10,endpoint.getX(),endpoint.getY()+10);
            Group lineg = new Group(line1,line2,line3,line4,line5,line6,line7);
            endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
            this.endpoint= endpoint;
            this.group = group;
            this.group.getChildren().add(insideGroup);
            this.group.getChildren().add(lineg);
            this.diamodpoints = diamodpoints;
            this.ifelemAL = ifelemAL;


    }


    public Point2D[] getDiamodpoints(){
        return diamodpoints;
    }
    public void ifend(Point2D endpoint){
        group.getChildren().add(makeendline(endpoint));
    }

    public Group getGroup(){
        return group;
    }
    public Group getinsideGroup(){
        return insideGroup; }
    public static  ArrayList<Point2D> getIfelemAL(){
        return ifelemAL;
    }
    public Point2D getEndpoint() {
        return endpoint;
    }
    public Point2D getElsepoints(){
        return elsepoint;
    }
    public Point2D getabsElsepoints(){
        return elsepoint;
    }
}
