package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.PartaiKab;

public class RmKab {
    @SerializedName("calon")
    private List<DataCalon.DPRDKab> calon;

    @SerializedName("partai")
    private List<PartaiKab> partai;

    public List<DataCalon.DPRDKab> getCalon() {
        return calon;
    }

    public List<PartaiKab> getPartai() {
        return partai;
    }
}
