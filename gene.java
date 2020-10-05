import java.util.*;
import java.awt.Point;
public class gene {
    private environment myEnv = null;
    private static ArrayList<GMap> population = new ArrayList<GMap>();
    private static ArrayList<GMap> answer = new ArrayList<GMap>();
    private static Set<GMap> geneSet = new HashSet<GMap>();
    private static ArrayList<Float> geneGrade = new ArrayList<Float>();
    private static int index = 0;
    private int times;
    public gene(environment myEnv) {
        //System.out.println("*");
        this.myEnv = myEnv;
        times = 10;
    }

    public gene(environment myEnv, GMap temp) {
        //System.out.println("*");
        this.myEnv = myEnv;
 
        times = 10;
        //ArrayList<GMap> orin = myEnv.getPopulation();
        //System.out.println("Build with orin size: " + orin.size());
        //System.out.println(orin.size());
        //for(int index = 0, limit = orin.size();index < limit;index++) {
           // System.out.println(orin.get(index));
            //temp = orin.get(index);
            float ratio = temp.affectCalculate();
           /* System.out.println("before");
            updateGrade();
            */
            if(!geneSet.contains(temp)) {
                population.add(temp);
                geneSet.add(temp);
                geneGrade.add(ratio);
                if(ratio >= 1.00f) {
                    answer.add(temp);
                ArrayList<baseStand> place = temp.getBase();
                int place_size = place.size();
                System.out.print("[");
                for(int x = 0;x < place_size;x++) {
                    baseStand temp2 = place.get(x);
                    System.out.print(temp2.getPlace().getX() + "," + temp2.getPlace().getY());
                }
                System.out.println("] <- ANSWER" + ratio);
                }
                index++;
            }
            /*System.out.println("end");
            updateGrade();
            */
            //System.out.println("pop : "+population.size()+" geneset : "+geneSet.size());
        //}
        //myEnv.producePopulation();
        //System.out.println("population Builded: 32"+ population.size() + " with orin size");
    }

    public void startGene() {
        //for(int i=0;i<population.size();i++){
        //    System.out.println("population = "+population.get(i).mapsize());
        //}
        
        int record = times;
        while(times > 0) {
        System.out.println("For least times: " + times + " population size: " + population.size());
            childProduce();
            //updateGrade();
            times--;
        }
        times = record;
        showResult();
    }
    //select,change,recalculate
    private void childProduce() {
        int count = population.size();
        int range = 1000;
        Random ran = new Random();
        boolean d1, d2;
        int b1, b2;
        int num1, num2;
        for(int target = count / 4;target > 0;target--) {     
            count = geneGrade.size();
            //System.out.println("count: " + count);
            int retry = 0; 
            int maxNum = myEnv.getMaxBaseNumber();
            d1 = d2 = false;
            num1 = num2 = 0;
            b1 = b2 = 0;
            while(!d1) {
                //System.out.println("num="+num1+" count="+count);
                if(num1 == count && !d1) {
                    num1 = 0;
                    retry += 5;
                }
                // range: 1 - 1000  < 0 - 1000(grade) + retry(when all fail)
                if(ran.nextInt(range)+1 < (range * geneGrade.get(num1) + retry)) {
                    d1 = true;
                    b1 = ran.nextInt(maxNum) + 1; // 1 - max
                    maxNum -= b1;
                    //System.out.println("d1=" + d1 + " b1=" + b1 + " max=" + maxNum);
                    continue;
                }
                num1++;
            }
            retry = 0;
            if(maxNum > 0) {
                while(!d2) {
                    if(num2 == count && !d2) {
                        num2 = 0;
                        retry += 5;
                    }
                    if(ran.nextInt(range)+1 < (range * geneGrade.get(num2) + retry)) {
                        d2 = true;
                        b2 = ran.nextInt(maxNum) + 1; // 1 - max
                        continue;
                    }
                    num2++;
                }
            }
            //select
            System.out.println("b1 "+ b1 + " b2"+ b2);  
            ArrayList<baseStand> select = new ArrayList<baseStand>();
            ArrayList<baseStand> selected = population.get(num1).getBase();
            Set<baseStand> selectSet = new HashSet<baseStand>();
            int start = 0;
            int selectedSize = selected.size();
            //System.out.println("selected size="+selected.size());
            //System.out.println("b1 start"+b1);
            int ed;
            for(start = 0, count = 0, ed = selected.size();count < b1 && start < ed;count++) { //5 choice 3->0,1,2,3
                baseStand temp;
                temp = selected.get(start);
                if(!selectSet.contains(temp)) {
                    select.add(temp);
                    selectSet.add(temp);
                }
                start++;
            }
            //count=1
            System.out.println(select.get(0).getPlace().getX()+","+select.get(0).getPlace().getY());  
            selected = population.get(num2).getBase();
            selectedSize = selected.size();
            //System.out.println("b2 start");
            for(start = 0, count = 0, ed = selected.size();count < b2 &&  start < ed;count++) { //5 choice 3->0,1,2,3
                baseStand temp;
                temp = selected.get(start);
                if(!selectSet.contains(temp)) {
                    select.add(temp);
                    selectSet.add(temp);
                }
                start++;
            }
            //count=0
            GMap result = new GMap(myEnv);
            result.setBase(select);
            float percent = result.affectCalculate();
            int size = select.size();
            maxNum = myEnv.getMaxBaseNumber();
            ArrayList<baseStand> place1 = result.getBase();
            //change
            /*
            x == limit_x || x < limit_x and y==100% || y < 100%
            con1: x < limit_x || x == limit_x, y==100% change base--
            con2: x < limit_x, y < 100% change base++ (select 0)
            con3: x == limiy_x, y < 100% base-- calculate before base++, (select 0)
            */
            if(percent >= 1.00f) {
                if(ran.nextInt(100) + 1 >= (int)(100 * ((float)size / (float)maxNum))) {
                    boolean ok = false;
                    ArrayList<baseStand> suit = new ArrayList<baseStand>(select);
                    suit.remove(ran.nextInt(size));
                    GMap temp = new GMap(myEnv);
                    float ratio;
                    temp.setBase(suit);
                    ratio = temp.affectCalculate();
                    if(ratio >= 1)
                        select = suit;
                }
            } else {
                //System.out.println(size +  " & " + maxNum);
                if(size == maxNum) {
                    int delete = ran.nextInt(size) + 1;
                    for(start = 0;start < delete;start++) {
                        //System.out.println("remove" + start);
                        select.remove(start);
                    }
                }
                int add_limit;
                boolean zero = true;
                size = select.size();
                //System.out.println("max: " + maxNum + " size: " + size);
                add_limit = maxNum - size;
                add_limit = ran.nextInt(add_limit) + 1;
                for(int add_count = 0, power = myEnv.getBasePower(), length = myEnv.getMapSize();add_count < add_limit;add_count++) {
                    int x = 0, y = 0;
                    int len = myEnv.getMapSize();
                    int[][] state = new int[len][len];
                    for(int m = 0;m < len;m ++){
                        for(int n = 0;n < len;n ++){
                            state[m][n] = 0;
                        }
                    }
                    len = select.size();
                    for(int countInit = 0;countInit < len;countInit++) {
                        baseStand bas = select.get(countInit);
                        Point place = bas.getPlace();
                        state[(int)(place.getX())][(int)(place.getY())] = 1;
                    }
                    int m = 0, n = 0;
                    int t = 1;
                    for(m = 0, t = 1;m < len && t == 1;m++){
                        for(n = 0;n < len && t == 1;n++){
                            if(state[m][n] == 0)
                                t = 0;
                        }
                    }
                    x = ran.nextInt(length);
                    y = ran.nextInt(length);
                    if(state[x][y] != 0) {
                        x = m;
                        y = n;
                    }
                    baseStand addBase = new baseStand(myEnv);
                    addBase.setPower(power);
                    addBase.setPlace(new Point(x,y));
                    select.add(addBase);
                }
            }
            result = new GMap(myEnv);
            result.setBase(select);
            float ratio;
            ratio = result.affectCalculate();
            if(!geneSet.contains(result)) {
                population.add(result);
                geneSet.add(result);
                geneGrade.add(ratio);
                ArrayList<baseStand> place = result.getBase();
                int place_size = place.size();
                System.out.print("[");
                for(int x = 0;x < place_size;x++) {
                    baseStand temp = place.get(x);
                    System.out.print(temp.getPlace().getX() + "," + temp.getPlace().getY());
                }
                if(ratio >= 1.00f) {
                    answer.add(result);
                System.out.println("] <- ANSWER: " + ratio);
                } else {
                    System.out.println("]" + ratio);
                }
            }
        }
    }

    private void updateGrade() {
        int count = population.size();
        for(int an = 0, length = myEnv.getMapSize();an < count;an++) {
            ArrayList<baseStand> place = population.get(an).getBase();
            int place_size = place.size();
            System.out.print(an + " [");
            for(int x = 0;x < place_size;x++) {
                baseStand temp = place.get(x);
                System.out.print(temp.getPlace().getX() + "," + temp.getPlace().getY());
            }
            System.out.println("]");
        }
    }

    public void showResult() {
        int count = answer.size();
        if(count > 0) {
            System.out.println("Answer count: " + count + " in times: " + times + "\n");
            for(int an = 0, length = myEnv.getMapSize();an < count;an++) {
                ArrayList<baseStand> place = answer.get(an).getBase();
                int place_size = place.size();
                System.out.print("index:"+ an + "[");
                for(int x = 0;x < place_size;x++) {
                    baseStand temp = place.get(x);
                    System.out.print(temp.getPlace().getX() + " , " + temp.getPlace().getY());
                }
                System.out.println("]");
            }
        } else {
            count = population.size();
            System.out.println("No Answer in population:" + population.size() + " in times: " + times + "\n");
                for(int an = 0, length = myEnv.getMapSize();an < count;an++) {
                ArrayList<baseStand> place = population.get(an).getBase();
                int place_size = place.size();
                System.out.print("index:"+ an + " [");
                for(int x = 0;x < place_size;x++) {
                    baseStand temp = place.get(x);
                    System.out.print(temp.getPlace().getX() + "," + temp.getPlace().getY());
                }
                System.out.println("] " + geneGrade.get(an));
            }
        }
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public ArrayList<GMap> getAnswer() {
        return new ArrayList<GMap>(answer);
    }
}