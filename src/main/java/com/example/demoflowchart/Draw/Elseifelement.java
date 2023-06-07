package com.example.demoflowchart.Draw;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Elseifelement extends Drawflows{
    private Group group = new Group();
    private static BaseElem baseElem;
    private static ArrayList<Point2D> ifelemAL = new ArrayList<Point2D>();
    private static Point2D endpoint = new Point2D(0,0);
    private static Point2D elsepoint = new Point2D(0,0);
    public Elseifelement(BaseElem element, double corIflinesize, Point2D startPoints) {
        ArrayList<Point2D> ifelemAL = new ArrayList<Point2D>();
        Group group = new Group();
        baseElem = element;
        Point2D endpoint = new Point2D(startPoints.getX(),startPoints.getY());
        Line elseline = createHorizontalLine(endpoint.getX(),endpoint.getY(),linesize+corIflinesize);
        Text label = new Text(element.getCondition());
        label.setX((elseline.getStartX() + elseline.getEndX()) / 2 - 10);
        label.setY((elseline.getStartY() + elseline.getEndY()) / 2 - 5);
        Point2D elsepoint= new Point2D(elseline.getEndX(),elseline.getEndY());
        endpoint = new Point2D(elseline.getEndX(),elseline.getEndY());
        Point2D absoluteelsepoints=elsepoint;
        group.getChildren().add(elseline);
        group.getChildren().add(label);
        System.out.println(absoluteelsepoints + " abs");
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
                        ifelement = new Ifelement(elem,corziz,endpoint);
                    }
                    try{
                        if(!(element.getElemArrayList().get(i+1).getType().equals("else") ||
                                element.getElemArrayList().get(i+1).getType().equals("else-if"))){
                            endpoint= ifelement.getEndpoint();
                            elsepoint= ifelement.getElsepoints();
                            group.getChildren().add( createVerticalLine(endpoint.getX(),endpoint.getY(),10));
                            endpoint = new Point2D(endpoint.getX(),endpoint.getY()+10);
                            group.getChildren().add(createEmptyelseline(elsepoint,endpoint));
                            endpoint = new Point2D(ifelement.getDiamodpoints()[1].getX(),endpoint.getY());
                            group.getChildren().add(ifelement.getGroup());
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
                        group.getChildren().add(createEmptyelseline(ifelement.getElsepoints(),endpoint));
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
            i ++;
            this.endpoint= endpoint;
            this.elsepoint = absoluteelsepoints;
            this.group = group;
            this.ifelemAL = ifelemAL;
        }

    }
    public Group getGroup(){
        return group;
    }
    public Point2D getEndpoint() {
        return endpoint;
    }
    public Point2D getElsepoints(){
        return elsepoint;
    }
}
