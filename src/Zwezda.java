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
public class Zwezda extends GrObject{
    double R,FI,df;
    int N;
    double V00=0.05;
    transient double xx[],yy[];
    transient double PI2=2*Math.PI;
    public Zwezda(){}
    public Zwezda(double r0,int n0, Component p0){
        super(p0);
        R=r0;
        N=n0;
        FI=0;
        df=Math.random()*V00;
        xx=new double[N];
        yy=new double[N];
        }
    public Zwezda(int xx,int yy,double r0,int n0,Component p0){
        this(r0,n0,p0);
        x=xx; y=yy;
        }
    @Override
    public void paint(boolean fore){
        super.paint(fore);
        for (int i=0;i<N;i++)
            gg.drawLine((int)(x+xx[i]), (int)(y+yy[i]), (int)(x+xx[(i+2)%N]), (int)(y+yy[(i+2)%N]));
        }
    @Override
    public boolean inside(int xx,int yy){ return (x-xx)*(x-xx)+(y-yy)*(y-yy) < R*R;}
    @Override
    public void step(){
        FI+=df;
        for (int i=0;i<N;i++){
            xx[i]=R*Math.cos(FI+i*PI2/N);
            yy[i]=R*Math.sin(FI+i*PI2/N);
            }
        }
    @Override
    public double size(){return R; }
    public void Save(DataOutputStream F) throws IOException{
        super.Save(F);
        F.writeDouble(R);
        F.writeDouble(FI);
        F.writeDouble(df);
        F.writeInt(N);
        }
    public void Load(DataInputStream F) throws IOException{
        super.Load(F);
        R=F.readDouble();
        FI=F.readDouble();
        df=F.readDouble();
        N=F.readInt();
        xx=new double[N];
        yy=new double[N];
        }
    public void SaveText(BufferedWriter F) throws IOException{
        super.SaveText(F);
        F.write(""+R); F.newLine();
        F.write(""+FI); F.newLine();
        F.write(""+df); F.newLine();
        F.write(""+N); F.newLine();
        }
    public void LoadText(BufferedReader F) throws IOException{
        super.LoadText(F);
        String ss;
        ss=F.readLine(); R=Double.parseDouble(ss);
        ss=F.readLine(); FI=Double.parseDouble(ss);
        ss=F.readLine(); df=Double.parseDouble(ss);
        ss=F.readLine(); N=Integer.parseInt(ss);
        xx=new double[N];
        yy=new double[N];
        }
    public static void setXMLParser(XStream xstream){
        xstream.alias("Zwezda", Zwezda.class);
        xstream.useAttributeFor(Zwezda.class, "R");
        xstream.useAttributeFor(Zwezda.class, "FI");
        xstream.useAttributeFor(Zwezda.class, "df");
        xstream.useAttributeFor(Zwezda.class, "N");
        xstream.useAttributeFor(Zwezda.class, "V00");
        }
    public void setComponent(Component p0){
        super.setComponent(p0);
        xx=new double[N];
        yy=new double[N];
        }    
}
