package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class DataCalon {
    public static class Presiden {
        @SerializedName("id")
        private int id;
        @SerializedName("id_calon")
        private String id_calon;
        @SerializedName("no_urut")
        private String no_urut;
        @SerializedName("capres")
        private String capres;
        @SerializedName("cawapres")
        private String cawapres;
        @SerializedName("tahun")
        private String tahun;
        @SerializedName("periode")
        private String periode;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;

        public Presiden(int id, String id_calon, String no_urut, String capres, String cawapres, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_calon = id_calon;
            this.no_urut = no_urut;
            this.capres = capres;
            this.cawapres = cawapres;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getNo_urut() {
            return no_urut;
        }

        public void setNo_urut(String no_urut) {
            this.no_urut = no_urut;
        }

        public String getCapres() {
            return capres;
        }

        public void setCapres(String capres) {
            this.capres = capres;
        }

        public String getCawapres() {
            return cawapres;
        }

        public void setCawapres(String cawapres) {
            this.cawapres = cawapres;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class DPRRI {
        private int id;
        private String id_calon;
        private String id_dapil;
        private String id_partai;
        private String nomorurut;
        private String nama;
        private String tahun;
        private String periode;
        private String created_at;
        private String updated_at;

        // Konstruktor
        public DPRRI(int id, String id_calon, String id_dapil, String id_partai, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_calon = id_calon;
            this.id_dapil = id_dapil;
            this.id_partai = id_partai;
            this.nomorurut = nomorurut;
            this.nama = nama;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        // Getter dan Setter

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getId_dapil() {
            return id_dapil;
        }

        public void setId_dapil(String id_dapil) {
            this.id_dapil = id_dapil;
        }

        public String getId_partai() {
            return id_partai;
        }

        public void setId_partai(String id_partai) {
            this.id_partai = id_partai;
        }

        public String getNomorurut() {
            return nomorurut;
        }

        public void setNomorurut(String nomorurut) {
            this.nomorurut = nomorurut;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class DPDRI {
        private int id;
        private String id_calon;
        private String id_prov;
        private String nomorurut;
        private String nama;
        private String tahun;
        private String periode;
        private String created_at;
        private String updated_at;

        public DPDRI(int id, String id_calon, String id_prov, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_calon = id_calon;
            this.id_prov = id_prov;
            this.nomorurut = nomorurut;
            this.nama = nama;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getId_prov() {
            return id_prov;
        }

        public void setId_prov(String id_prov) {
            this.id_prov = id_prov;
        }

        public String getNomorurut() {
            return nomorurut;
        }

        public void setNomorurut(String nomorurut) {
            this.nomorurut = nomorurut;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class Gubernur {
        private int id;
        private String id_prov;
        private String id_calon;
        private String no_urut;
        private String cagub;
        private String cawagub;
        private String tahun;
        private String periode;
        private String created_at;
        private String updated_at;

        // Konstruktor
        public Gubernur(int id, String id_prov, String id_calon, String no_urut, String cagub, String cawagub, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_prov = id_prov;
            this.id_calon = id_calon;
            this.no_urut = no_urut;
            this.cagub = cagub;
            this.cawagub = cawagub;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        // Getter dan Setter

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_prov() {
            return id_prov;
        }

        public void setId_prov(String id_prov) {
            this.id_prov = id_prov;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getNo_urut() {
            return no_urut;
        }

        public void setNo_urut(String no_urut) {
            this.no_urut = no_urut;
        }

        public String getCagub() {
            return cagub;
        }

        public void setCagub(String cagub) {
            this.cagub = cagub;
        }

        public String getCawagub() {
            return cawagub;
        }

        public void setCawagub(String cawagub) {
            this.cawagub = cawagub;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class DPRDProv {
        private int id;
        private String id_calon;
        private String id_dapil;
        private String id_partai;
        private String nomorurut;
        private String nama;
        private String tahun;
        private String periode;
        private String created_at;
        private String updated_at;

        public DPRDProv(int id, String id_calon, String id_dapil, String id_partai, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_calon = id_calon;
            this.id_dapil = id_dapil;
            this.id_partai = id_partai;
            this.nomorurut = nomorurut;
            this.nama = nama;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getId_dapil() {
            return id_dapil;
        }

        public void setId_dapil(String id_dapil) {
            this.id_dapil = id_dapil;
        }

        public String getId_partai() {
            return id_partai;
        }

        public void setId_partai(String id_partai) {
            this.id_partai = id_partai;
        }

        public String getNomorurut() {
            return nomorurut;
        }

        public void setNomorurut(String nomorurut) {
            this.nomorurut = nomorurut;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class Bupati {
        private int id;
        private String id_kab;
        private String id_calon;
        private String no_urut;
        private String cabup;
        private String cawabup;
        private String tahun;
        private String periode;
        private String created_at;
        private String updated_at;

        public Bupati(int id, String id_kab, String id_calon, String no_urut, String cabup, String cawabup, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_kab = id_kab;
            this.id_calon = id_calon;
            this.no_urut = no_urut;
            this.cabup = cabup;
            this.cawabup = cawabup;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_kab() {
            return id_kab;
        }

        public void setId_kab(String id_kab) {
            this.id_kab = id_kab;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getNo_urut() {
            return no_urut;
        }

        public void setNo_urut(String no_urut) {
            this.no_urut = no_urut;
        }

        public String getCabup() {
            return cabup;
        }

        public void setCabup(String cabup) {
            this.cabup = cabup;
        }

        public String getCawabup() {
            return cawabup;
        }

        public void setCawabup(String cawabup) {
            this.cawabup = cawabup;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class DPRDKab {
        private int id;
        private String id_calon;
        private String id_dapil;
        private String id_partai;
        private String nomorurut;
        private String nama;
        private String tahun;
        private String periode;
        private String created_at;
        private String updated_at;

        public DPRDKab(int id, String id_calon, String id_dapil, String id_partai, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_calon = id_calon;
            this.id_dapil = id_dapil;
            this.id_partai = id_partai;
            this.nomorurut = nomorurut;
            this.nama = nama;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getId_dapil() {
            return id_dapil;
        }

        public void setId_dapil(String id_dapil) {
            this.id_dapil = id_dapil;
        }

        public String getId_partai() {
            return id_partai;
        }

        public void setId_partai(String id_partai) {
            this.id_partai = id_partai;
        }

        public String getNomorurut() {
            return nomorurut;
        }

        public void setNomorurut(String nomorurut) {
            this.nomorurut = nomorurut;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static  class Kades {
        private int id;
        private String id_des;
        private String id_calon;
        private String no_urut;
        private String cakades;
        private String tahun;
        private String periode;
        private String created_at;
        private String updated_at;

        public Kades(int id, String id_des, String id_calon, String no_urut, String cakades, String tahun, String periode, String created_at, String updated_at) {
            this.id = id;
            this.id_des = id_des;
            this.id_calon = id_calon;
            this.no_urut = no_urut;
            this.cakades = cakades;
            this.tahun = tahun;
            this.periode = periode;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_des() {
            return id_des;
        }

        public void setId_des(String id_des) {
            this.id_des = id_des;
        }

        public String getId_calon() {
            return id_calon;
        }

        public void setId_calon(String id_calon) {
            this.id_calon = id_calon;
        }

        public String getNo_urut() {
            return no_urut;
        }

        public void setNo_urut(String no_urut) {
            this.no_urut = no_urut;
        }

        public String getCakades() {
            return cakades;
        }

        public void setCakades(String cakades) {
            this.cakades = cakades;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getPeriode() {
            return periode;
        }

        public void setPeriode(String periode) {
            this.periode = periode;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }


}
