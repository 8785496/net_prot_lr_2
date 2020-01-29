/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.thoughtworks.xstream.XStream;
import java.awt.*;
import java.io.*;
/**
 *
 * @author User
 */
public class Circle extends GrObject{
    double R,R0;
    int ns=0;
    public Circle(){}
    public Circle(double r0,Component p0){
        super(p0);
        R0=R=r0;
        }
    @Override
    public double size(){ return R/2;}
    public Circle(int xx,int yy,double r0,Component p0){
        this(r0,p0);
        x=xx; y=yy;
        }
    @Override
    public void paint(boolean fore){
        super.paint(fore);
        gg.fillOval((int)(x-R/2), (int)(y-R/2), (int)R, (int)R);
        }
    @Override
    public void step(){
        R=R0*(1+0.3*Math.sin(ns/20.)); ns++;
        }
    @Override
    public boolean inside(int xx,int yy){ return (x-xx)*(x-xx)+(y-yy)*(y-yy) < R*R;}
    public void Save(DataOutputStream F) throws IOException{
        super.Save(F);
        F.writeDouble(R0);
        }
    public void Load(DataInputStream F) throws IOException{
        super.Load(F);
        R=R0=F.readDouble();
        ns=0;
        }
    public void LoadText(BufferedReader F) throws IOException{
        super.LoadText(F);
        String ss;
        ss=F.readLine(); R0=Double.parseDouble(ss);
        }
    public void SaveText(BufferedWriter F) throws IOException {
        super.SaveText(F);
        F.write(""+R0); F.newLine();
        }    
   public static void setXMLParser(XStream xstream){
        xstream.alias("Circle", Circle.class);
        xstream.useAttributeFor(Circle.class, "R");
        xstream.useAttributeFor(Circle.class, "R0");
        xstream.useAttributeFor(Circle.class, "ns");
        }
    public void setComponent(Component p0){
        super.setComponent(p0);
        }
}
