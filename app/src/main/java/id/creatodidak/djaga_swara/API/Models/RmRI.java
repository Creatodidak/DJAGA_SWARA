package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.PartaiNas;

public class RmRI {
    @SerializedName("calon")
    private List<DataCalon.DPRRI> calon;

    @SerializedName("partai")
    private List<PartaiNas> partai;

    public List<DataCalon.DPRRI> getCalon() {
        return calon;
    }

    public List<PartaiNas> getPartai() {
        return partai;
    }
}
