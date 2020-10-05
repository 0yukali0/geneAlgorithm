import java.lang.*;
import java.util.*;
import java.awt.*;

public class GMap{
    private environment myEnv;
    private ArrayList<baseStand> allBase = new ArrayList<baseStand>();
    private int[][] state;
    private float coverage;

    public GMap(environment myEnv){
        this.myEnv = myEnv;
        this.allBase = myEnv.getAllBase();
        state = new int[myEnv.getMapSize()][myEnv.getMapSize()];
        for(int m = 0;m < myEnv.getMapSize();m ++){
            for(int n = 0;n < myEnv.getMapSize();n ++){
                state[m][n] = 0;
            }
        }
    }

    public void caculateRadio(int x,int y,int radiopower,environment myEnv,ArrayList<Integer> mapsize){
        int length = myEnv.getMapSize();
        //Point(x,y)
        int [][] mat = new int[length][length];
        int extend=radiopower;

        ArrayList<Integer> list = new ArrayList<Integer>();
 
        for(int i=0;i<length;i++){
            for(int j=0;j<length;j++){
                mat[i][j]=0;
            }
        }
        mat[x][y]=radiopower;
        mapsize.set(x*length+y,mat[x][y]);
        System.out.println("x="+x+" y="+y);
        while(extend !=0){
            extend--;
            for(int i=0;i<mapsize.size();i++){
                int a=0,b=0;
                if(mapsize.get(i)==extend+1){
                    b=i%length;
                    a=(i-b)/length;
                }
                if(a-1>=0&&mat[a-1][b]==0){
                    mat[a-1][b]=extend;
                    mapsize.set((a-1)*length+b,mat[a-1][b]);
                }
                if(a+1<length&&mat[a+1][b]==0){
                    mat[a+1][b]=extend;
                    mapsize.set((a+1)*length+b,mat[a+1][b]);
                }
                if(b-1>=0&&mat[a][b-1]==0){
                    mat[a][b-1]=extend;
                    mapsize.set((a)*length+b-1,mat[a][b-1]);
                }
                if(b+1<length&&mat[a][b+1]==0){
                    mat[a][b+1]=extend;
                    mapsize.set((a)*length+b+1,mat[a][b+1]);
                }
            }
        }

        /*for(int i = 0;i < radiopower;i ++){
            if((y - i) >= 0 && (y - i) < length){
                 mapsize.set(x * length+ y - i,radiopower - i);
            }
        }
        for(int i = radiopower;i < 2*(radiopower);i ++){
            if((y + i) >= 0 && (y + i) < length){
                 mapsize.set(x * length+ y + i,radiopower - i);
            }
        }
        if(radiopower > 0){
            if(x > 0 && mapsize.get((x-1) * length+ y)==0){
                 caculateRadio(x-1,y,radiopower-1,myEnv,mapsize);
            }
            else if(x == 0 && mapsize.get((length - 1) * length+ y)==0){
                caculateRadio(length+(x-1),y,radiopower-1,myEnv,mapsize);
            }
            if(x+1 < length && mapsize.get((x+1) * length+ y) == 0){
                caculateRadio(x+1,y,radiopower-1,myEnv,mapsize);
            }
            else if(x+1 == length && mapsize.get(y) == 0 ){
                caculateRadio(0,y,radiopower-1,myEnv,mapsize);
            }
        }*/
    }

    public void affectCalculate(){
        ArrayList<Integer> mapsize = new ArrayList<Integer>();
        ArrayList<Integer> finalradio = new ArrayList<Integer>();
        int odd = 0, even = 0,radionum = 0,length = myEnv.getMapSize();
        ArrayList<ArrayList<Integer>> basestandnum = new ArrayList<ArrayList<Integer>>();
        int allnumber = allBase.size();

        for(int i = 0;i < allBase.size();i ++){
            mapsize = new ArrayList<Integer>();
            for(int n = 0;n < length * length;n ++){
                mapsize.add(0);
            }
            basestandnum.add(mapsize);
        }
        for(int i = 0; i < allBase.size();i ++){
            Point temp;
            ArrayList<Point> check = new ArrayList<Point>();
            temp = allBase.get(i).getPlace();
            if(!check.contains(temp)){
                caculateRadio((int)(temp.getX()),(int)(temp.getY()),myEnv.getBasePower(),myEnv,mapsize);
                System.out.println(mapsize);
                //for(int a = 0;a < length * length;a ++){
                //     System.out.println("GMap:72 "+mapsize.get(a));
                // }

                basestandnum.set(i,mapsize);
                for(int n = 0;n < length * length;n ++){
                    mapsize.set(n,0);
                }
            }
        }

        for(int n = 0;n < myEnv.getMapSize() * myEnv.getMapSize();n ++){
                finalradio.add(n,0);
        }

        for(int n = 0;n < length * length;n ++){
            for(int i = 0; i < allBase.size();i ++){
                if(basestandnum.get(i).get(n) % 2 == 0){
                    even += (-basestandnum.get(i).get(n));
                }
                else{
                    odd += basestandnum.get(i).get(n);
                }
            }
            finalradio.set(n,Math.abs(odd+even));
        }

        for(int n = 0;n < length * length;n ++){
            if(finalradio.get(n) != 0){
                radionum ++;
            }
        }

        coverage = radionum / length;
    }

    public float getCoverage(){
        return coverage;
    }

    public void setState(Point place){
        state = new int[(int)(place.getX())][(int)(place.getY())];
    }

    public int[][] getState(){
        return state;
    }

    public void setBase(ArrayList<baseStand> allBase){
        this.allBase = myEnv.getAllBase();
    }

    public ArrayList<baseStand> getBase(){
        return allBase;
    }
}
