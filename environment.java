import java.lang.Math.*;
import java.util.*;
import java.text.*;
import java.io.*;

class environment{
    private ArrayList<GMap> population = new ArrayList<>();
    private ArrayList<baseStand> allBase = new ArrayList<>();
    private int basePower = 0;
    private int mapSize = 0;
    private int populationSize = 10;
    private int miniPower = 1;
    private int maxBaseNumber = 1;

    //randomise the number of base
    public void randomBase(){
        allBase = new ArrayList<>();
        Random ran = new Random();
        int maxNum = 0;
        int count = 0;

        if(mapSize <= 0){
            System.out.println("Error! Size of map must greater than 0.");
        }else{

            maxNum = ran.nextInt(maxBaseNumber)+1;
            for(count = 1; count <= maxNum ; count++){
                baseStand base = new baseStand(this);
                allBase.add(base);
                //System.out.println("env:30 "+allBase.size());
            }
        }
    }
    public ArrayList<baseStand> getAllBase(){
        return allBase;
    }
    

    //produce population
    int size=1;

    public void producePopulation(){
    for(int i = 0;i< populationSize;i++){
            GMap map = new GMap(this);
 
            randomBase();
            map.setBase(allBase);
            //System.out.println("size="+size);
            /*if(size<populationSize){
                size++;*/
                gene ge = new gene(this, map);
            //}
            
            //population.add(map);
    }
        //System.out.println("popsize: "+population.size());
    }
    public ArrayList<GMap> getPopulation(){
        return population;
    }

    //set and get power of base
    public int getBasePower(){
        return basePower;
    }
    public void setBasePower(int input){
        if(input <= 0){
            System.out.println("Error! Power of basestand must greater than 0.");
        }else{
            this.basePower = input;
        }
    }

    //set and get size of map
    public int getMapSize(){
        return mapSize;
    }
    public void setMapSize(int input){
        if(input <= 0){
            System.out.println("Error! Size of map must greater than 0.");
        }else{
            this.mapSize = input;
        }
    }

    //set and get size of population
    public int getPopulationSize(){
        return populationSize;
    }
    public void setPopulationSize(int input){
        if(input <= 0){
            System.out.println("Error! Size of population must greater than 0.");
        }else{
            this.populationSize = input;
        }
    }

    //set and get minimum number of basestand
    public int getMiniPoWer(){
        return miniPower;
    }
    public void setMiniPower(int input){
        if(input <= 0){
            System.out.println("Error! Minimum power of basestand must greater than 0.");
        }else{
            this.miniPower = input;
        }
    }

    //set and get max number of basestand
    public int getMaxBaseNumber(){
        return maxBaseNumber;
    }
    public void setMaxBaseNumber(int input){
        if(input <= 0){
            System.out.println("Error! Max number of basestand must greater than 0.");
        }else{
            this.maxBaseNumber = input;
        }
    }
}