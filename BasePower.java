import java.io.SyncFailedException;
import java.util.*;

public class BasePower {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //System.out.println("輸入最大基地台數量:");
        int MaxBase=0;
        //System.out.println("輸入基地台中心量值:");
        int basePower=0;
        //System.out.println("輸入地圖邊長:");
        int MapSize=0;
        //System.out.println("輸入最小基地台覆蓋能量:");
        int miniPower=0;
        //user input
        /*MaxBase = input.nextInt();
        basePower = input.nextInt();
        MapSize = input.nextInt();
        miniPower = input.nextInt();*/
        //new envirnoment object
        Window window = new Window();
        int flag=0;
        while(flag==0){
            MaxBase = window.MaxBase();
            basePower = window.basePower();
            MapSize = window.MapSize();
            miniPower = window.miniPower();
            flag = window.flag();
            System.out.println(flag);
        }
        environment env = new environment();
        //input envirnoment
        env.setBasePower(basePower);
        env.setMapSize(MapSize);
        env.setMiniPower(miniPower);
        env.setMaxBaseNumber(MaxBase);
        env.producePopulation();
        //new gene object
        gene gen = new gene(env);

        //run gene
        gen.startGene();
        window.setanswer(gen.getAnswer());
        window.drawMap();
    }
}