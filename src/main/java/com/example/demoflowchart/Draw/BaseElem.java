package com.example.demoflowchart.Draw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
Правила:
1.Кожен дія алгоритма повинна бути на новому рядку
2.Використання циклів та опреторів повинно бути за таким прикладом: <operator>(condition){
3. Не можно допускати повторення знаків ввідкриття та закриття алгоритму(один символ на рядок) наприклад:
Привильно:
    while(){
    if(){
    }
    }
Не правильно:
    while(){
    if(){}}
4.Відсутність відступів від лівого краю
6. Форматування if else else if:
if(condition){
do
}
else if(condition){
do
}
else{
do
}
5. case та defolut повинні бути в такому ситаксисі
Правильно:
    case <condition>:{
        <code>
    }
    defoult:{
        <code>
    }
Не првильно:
    case <condition>:
        <code>
    defoult:
        <code>

*/
public class BaseElem {
    private String line = null;
    private  String type = "";
    private String condition = "";
    private File inside;
    private ArrayList<BaseElem> elemArrayList = new ArrayList<BaseElem>();

    public ArrayList<BaseElem> getElemArrayList() {
        return elemArrayList;
    }
    private BaseElem(String line,String type){
        this.line =line;
        this.type = "SimpleBlock";
    }
    public BaseElem(){

    }
    public String getLine() {
        return line;
    }

    public String getType() {
        return type;
    }

    private BaseElem(String type, String condition, String path){
        this.condition = condition;
        this.type= type;
        inside = new File(path);
        try{
            Scanner scanner = new Scanner(inside);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                byte types =gettypeline(line);
                switch (gettypeline(line)){
                    case 0:
                        elemArrayList.add(new BaseElem(line,"sb"));
                        break;
                    case 1: //if
                        iftypeblock(scanner,line);
                        break;
                    case 2: //else if
                        eiftypeblock(scanner,line);
                        break;
                    case 3: //else
                        elsetypeblock(scanner,line);
                        break;
                    case 4: //for
                        fortypeblock(scanner,line);
                        break;
                    case 5: //while
                        whiletypeblock(scanner,line);
                        break;
                    case 6: //switch
                        switchtypeblock(scanner,line);
                        break;
                    case 7: //case
                        casetypeblock(scanner,line);
                        break;
                    case 8: //default
                        defaulttypeblock(scanner,line);
                        break;
                }
            }
            scanner.close();
            if(!new File(inside.getPath()).delete()) System.out.println("notdelete");;
        }catch (FileNotFoundException e){

        }

    }

    public BaseElem(String path){
        inside = new File(path);
        try{
            Scanner scanner = new Scanner(inside);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                byte types =gettypeline(line);
                switch (gettypeline(line)){
                    case 0:
                        elemArrayList.add(new BaseElem(line,"sb"));
                        break;
                    case 1: //if
                        iftypeblock(scanner,line);
                        break;
                    case 2: //else if
                        eiftypeblock(scanner,line);
                        break;
                    case 3: //else
                        elsetypeblock(scanner,line);
                        break;
                    case 4: //for
                        fortypeblock(scanner,line);
                        break;
                    case 5: //while
                        whiletypeblock(scanner,line);
                        break;
                    case 6: //switch
                        switchtypeblock(scanner,line);
                        break;
                    case 7: //case
                        casetypeblock(scanner,line);
                        break;
                    case 8: //default
                        defaulttypeblock(scanner,line);
                        break;
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }
    private byte gettypeline(String line){
        byte type= 0;
        if(line.startsWith("if")){
            type =1;
        }else if(line.startsWith("else if")){
            type =2 ;
        }else if(line.startsWith("else")){
            type = 3;
        }else if(line.startsWith("for")){
            type = 4;
        }else if(line.startsWith("while")) {
            type = 5;
        }else if(line.startsWith("switch")) {
            type = 6;
        }else if(line.startsWith("case")) {
            type = 7;
        }else if(line.startsWith("default")) {
            type = 8;
        }else{
            type = 0;
        }
        return type;
    }
    private void iftypeblock(Scanner scanner, String line){
        String type = "if";
        String filename = type +"name";
        elemArrayList.add(new BaseElem(type,getCondition(line),getInside(scanner,filename)));
    }
    private void eiftypeblock(Scanner scanner, String line){
        String type = "else-if";
        String filename = type +"name";
        elemArrayList.add(new BaseElem(type,getCondition(line),getInside(scanner,filename)));
    }
    private void elsetypeblock(Scanner scanner, String line){
        String type = "else";
        String filename = type +"name";
        elemArrayList.add(new BaseElem(type,getCondition(line),getInside(scanner,filename)));
    }
    private void fortypeblock(Scanner scanner, String line){
        String type = "for";
        String filename = type +"name";
        elemArrayList.add(new BaseElem(type,getCondition(line),getInside(scanner,filename)));
    }
    private void whiletypeblock(Scanner scanner, String line){
        String type = "while";
        String filename = type +"name";
       elemArrayList.add(new BaseElem(type,getCondition(line),getInside(scanner,filename)));
    }
    private void switchtypeblock(Scanner scanner, String line){
        String type = "switch";
        String filename = type +"name";
        elemArrayList.add(new BaseElem(type,getCondition(line),getInside(scanner,filename)));
    }
    private void casetypeblock(Scanner scanner, String line){
        String type = "case";
        String filename = type +"name";
        elemArrayList.add(new BaseElem(type,getCondition(line,true),getInside(scanner,filename)));
    }
    private void defaulttypeblock(Scanner scanner, String line){
        String type = "default";
        String filename = type +"name";
        elemArrayList.add(new BaseElem(type,getCondition(line),getInside(scanner,filename)));
    }
    public String getCondition(String line,boolean csa){
        String condition="";
        char[] chars = line.toCharArray();
        int i=5;
        while(i<chars.length){
           if(chars[i]!='{')condition+=chars[i];
        i++;
        }
        return condition;
    }
    private String getCondition(String line){
        String condition="";
        char[] chars = line.toCharArray();
        int i =0;
        int j=0;
        while(i<chars.length){
            if(j!=0){
                if( chars[i]== ')'){
                    j--;
                    condition += chars[i];
                }
                else if( chars[i]== '('){
                    j++;
                    condition += chars[i];
                }else condition += chars[i];
            }else if( chars[i]== ')'){
                j--;
                condition += chars[i];
            }
            else if( chars[i]== '('){
                j++;
                condition += chars[i];
            }
            i++;
        }
        chars = condition.toCharArray();
        i=1;
        condition="";
        while(chars.length-1>i){
            condition += chars[i];
            i++;
        }
        return condition;
    }

    private String getInside(Scanner scanner,String filename){
        int j = 1;
        File inside =  makefile(filename);
        try {
        FileWriter fileWriter = new FileWriter(inside);
        while(j!=0){
            String line = scanner.nextLine();
            if(line.contains("{")){
                j++;

            }
            if(line.contains("}")){
                j--;
                if(j==0){
                    fileWriter.close();
                    return inside.getPath();
                }
            }


                fileWriter.write(line+"\n");

            }
            fileWriter.close();
        }
            catch (IOException e){
                System.out.println("exption");
            }
        return inside.getPath();
    }

    public String getCondition() {
        return condition;
    }

    private File makefile(String filename){
        int i = 1;
      while(true){
          if(new File(filename+i+".txt").exists()){
              i++;
          }else{
              try {
                  new File(filename+i+".txt").createNewFile();
                  return new File(filename+i+".txt");
              }catch (IOException e){
                  i++;
              }

          }
      }
    }

}
