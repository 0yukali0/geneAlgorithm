import java.awt.*;
import java.util.*;

public class baseStand {
    private environment myEnv;
    private int Power = -1;
    private Point place = new Point(-1,-1);
    
    public baseStand(environment myEnv){
        this.myEnv = myEnv;
        myEnv.getMapSize();
        Random ran = new Random();

        int x, y;
        x = ran.nextInt(myEnv.getMapSize());
        y = ran.nextInt(myEnv.getMapSize());
        place =new Point(x, y);
    }    

    public int getPower(){
        return Power;
    }

    public void setPower(int power) {
         this.Power = power;
    }

    public Point getPlace() {
        return place;
    }

    public void setPlace(Point place) {
        this.place = place;   
    }
}