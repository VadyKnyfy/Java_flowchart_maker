package com.example.demoflowchart.Draw;

import javafx.geometry.Point2D;
import javafx.scene.Group;

public class Elseelement1 extends Drawflows{
    private Group group = new Group();
    private static BaseElem baseElem;
    private static Point2D endpoint = new Point2D(0,0);
    private static Point2D elsepoint = new Point2D(0,0);
    public Elseelement1(BaseElem element, String str, Point2D startPoints) {
        baseElem = element;
        endpoint = new Point2D(startPoints.getX(),startPoints.getY());
        elsepoint= new Point2D(startPoints.getX()+size,startPoints.getY());
        for(BaseElem elem: element.getElemArrayList()){
            int i =0;
            switch (elem.getType()){
                case "SimpleBlock":{
                    SimpleElement simpleg = new SimpleElement(elem,endpoint);
                    group.getChildren().add(simpleg.getGroup());
                    endpoint = simpleg.getEndpoint();
                    break;
                }
                case "if":{
                    Ifelement ifelement = new Ifelement(elem,0,endpoint);
                    endpoint= ifelement.getEndpoint();
                    group.getChildren().add(ifelement.getGroup());

                    try{
                        if(!(element.getElemArrayList().get(i+1).getType().equals("else") ||
                                element.getElemArrayList().get(i+1).getType().equals("else-if"))){
                            group.getChildren().add(createEmptyelseline(ifelement.getElsepoints(),ifelement.getEndpoint()));
                            elsepoint= new Point2D(startPoints.getX(),startPoints.getY());
                        }}
                    catch (IndexOutOfBoundsException e){
                        group.getChildren().add(createEmptyelseline(ifelement.getElsepoints(),ifelement.getEndpoint()));
                    }
                    break;
                }

            }
            i++;
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
