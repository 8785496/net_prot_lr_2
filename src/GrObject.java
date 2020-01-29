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
public class GrObject implements Runnable, Serializable{
    double x,y,dx,dy;
    double V0=0.7;
    Color cl;
    transient int maxx,maxy;
    transient boolean stop=false;
    transient boolean pause=false;
    transient Graphics gg;
    transient Component pp;
    public GrObject(){}
    public GrObject(Component p0){ 
        if (p0!=null){
            pp=p0; 
            maxx=pp.getWidth();
            maxy=pp.getHeight();    
            gg=pp.getGraphics();
            x=Math.random()*maxx;
            y=Math.random()*maxy;
            }
        dx=Math.random()*V0;
        dy=Math.random()*V0;
        cl=new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
        }
    public void setComponent(Component p0){
        pp=p0;
        gg=pp.getGraphics();
        maxx=pp.getWidth();
        maxy=pp.getHeight();    
        }
    void Save(DataOutputStream F) throws IOException{
        F.writeDouble(x);
        F.writeDouble(y);
        F.writeDouble(dx);
        F.writeDouble(dy);
        F.writeInt(cl.getRGB());
    }
    void Load(DataInputStream F) throws IOException{
        x=F.readDouble();
        y=F.readDouble();
        dx=F.readDouble();
        dy=F.readDouble();
        cl=new Color(F.readInt());
    }
    void SaveText(BufferedWriter F) throws IOException{
        F.write(""+x); F.newLine();
        F.write(""+y); F.newLine();
        F.write(""+dx);F.newLine();
        F.write(""+dy);F.newLine();
        F.write(""+cl.getRGB());F.newLine();
    }
    void LoadText(BufferedReader F) throws IOException{
        String ss;
        ss=F.readLine(); x=Double.parseDouble(ss);
        ss=F.readLine(); y=Double.parseDouble(ss);
        ss=F.readLine(); dx=Double.parseDouble(ss);
        ss=F.readLine(); dy=Double.parseDouble(ss);
        ss=F.readLine();
        cl=new Color(Integer.parseInt(ss));
        }
    //------------------------------------------------------------------------------------
    void step(){}
    double size(){ return 0;}
    boolean inside(int xx,int yy){ return false;}
    //-------------------------------------------------------------------------------------
    public void paint(boolean fore){
        if (fore) gg.setColor(Color.white);
        else gg.setColor(cl);
    }
    public void run(){
        while(!stop){
            try {
                Thread.sleep(10);
    synchronized (pp){
                if (pause){
                    pause=false;
                    pp.wait();
                    }
                paint(true);
                step();
                x+=dx;
                y+=dy;
                double sz=size();
                if (dx>0 && x+sz>maxx || dx<0 && x<sz) dx=-dx;
                if (dy>0 && y+sz>maxy || dy<0 && y<sz) dy=-dy;
                paint(false);
                }
                } catch (InterruptedException ex) {}
        }
    paint(true);
    }
    public static void setXMLParser(XStream xstream){
        xstream.alias("GrObject", GrObject.class);
        xstream.useAttributeFor(GrObject.class, "x");
        xstream.useAttributeFor(GrObject.class, "y");
        xstream.useAttributeFor(GrObject.class, "dx");
        xstream.useAttributeFor(GrObject.class, "dy");
        xstream.useAttributeFor(GrObject.class, "V0");
        xstream.useAttributeFor(GrObject.class, "cl");
        }
}
