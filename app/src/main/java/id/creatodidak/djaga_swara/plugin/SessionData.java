package id.creatodidak.djaga_swara.plugin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.LogindetailItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.MLogin;

public class SessionData {
    public static void saveuserdata(Context context, LogindetailItem logindata){
        SharedPreferences sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sh.edit();
        if(logindata != null){
            ed.putString("nrp", logindata.getNrp());
            ed.putString("nik", logindata.getNik());
            ed.putString("nama", logindata.getNama());
            ed.putString("pangkat", logindata.getPangkat());
            ed.putString("satker", logindata.getSatker());
            ed.putString("satfung", logindata.getSatfung());
            ed.putString("jabatan", logindata.getJabatan());
            ed.putString("tanggallahir", logindata.getTanggalLahir());
            ed.putString("foto", logindata.getFoto());
            ed.putString("wa", logindata.getWa());
            ed.putBoolean("isLogin", true);
        }
        ed.apply();
    }

    public static boolean isUserLoggedIn(Context context){
        SharedPreferences sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);

        return sh.getBoolean("isLogin", false);
    }

    public static boolean isUserPinExist(Context context){
        SharedPreferences sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        String pin = sh.getString("PIN", null);

        return pin != null;
    }

    public static boolean isUserBiometricExist(Context context){
        SharedPreferences sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);

        return sh.getBoolean("BIOMETRIC", false);
    }

    public static LogindetailItem getuserdata(Context context){
        LogindetailItem data = new LogindetailItem();
        SharedPreferences sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        data.setNrp(sh.getString("nrp", null));
        data.setNik(sh.getString("nik", null));
        data.setNama(sh.getString("nama", null));
        data.setPangkat(sh.getString("pangkat", null));
        data.setSatker(sh.getString("satker", null));
        data.setSatfung(sh.getString("satfung", null));
        data.setJabatan(sh.getString("jabatan", null));
        data.setTanggalLahir(sh.getString("tanggallahir", null));
        data.setFoto(sh.getString("foto", null));
        data.setWa(sh.getString("wa", null));
        data.setPin(sh.getString("PIN", null));
        return data;
    }

    public static String getNamaPangkat(Context context){
        SharedPreferences sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        return sh.getString("pangkat", null) +" "+ sh.getString("nama", null);
    }

    public static boolean edituserdata(Context context, boolean isBoolean, boolean dataBoolean, String key, String data){
        SharedPreferences sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sh.edit();
        if(isBoolean){
            ed.putBoolean(key, dataBoolean);
        }else {
            ed.putString(key, data);
        }
        ed.apply();

        return true;
    }
}
