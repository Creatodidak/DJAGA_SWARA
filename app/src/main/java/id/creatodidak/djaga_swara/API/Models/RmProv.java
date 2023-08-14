package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.PartaiProv;

public class RmProv {
    @SerializedName("calon")
    private List<DataCalon.DPRDProv> calon;

    @SerializedName("partai")
    private List<PartaiProv> partai;

    public List<DataCalon.DPRDProv> getCalon() {
        return calon;
    }

    public List<PartaiProv> getPartai() {
        return partai;
    }
}
