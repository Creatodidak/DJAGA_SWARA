package id.creatodidak.djaga_swara.plugin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class DATATYPEMANAGER {
    public static List<String> listType(Context context){
        SharedPreferences sh = context.getSharedPreferences("DATAMANAGER", Context.MODE_PRIVATE);
        List<String> type = new ArrayList<>();

        if(sh.getBoolean("PRESIDEN", false)){type.add("PRESIDEN");}
        if(sh.getBoolean("GUBERNUR", false)){type.add("GUBERNUR");}
        if(sh.getBoolean("BUPATI", false)){type.add("BUPATI");}
        if(sh.getBoolean("KADES", false)){type.add("KADES");}
        if(sh.getBoolean("DPD-RI", false)){type.add("DPD-RI");}
        if(sh.getBoolean("DPR-RI", false)){type.add("DPR-RI");}
        if(sh.getBoolean("DPRD-PROV", false)){type.add("DPRD-PROV");}
        if(sh.getBoolean("DPRD-KAB", false)){type.add("DPRD-KAB");}

        return type;
    }
}
