package id.creatodidak.djaga_swara.API.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class TugasList implements Parcelable {
    private String id;
    private String nomor;
    private String judul;
    private String kode;
    private String tahun;
    private String penerbit;
    private String tandatangan;
    private String status;
    private String dasar;
    private String namaops;
    private String perintah;
    private String tanggal;
    private String tanggalmulai;
    private String tanggalberakhir;
    private String type;
    private String created_at;
    private String updated_at;
    private TPS tps;

    public TugasList(String id, String nomor, String judul, String kode, String tahun, String penerbit, String tandatangan, String status, String dasar, String namaops, String perintah, String tanggal, String tanggalmulai, String tanggalberakhir, String type, String created_at, String updated_at, TPS tps) {
        this.id = id;
        this.nomor = nomor;
        this.judul = judul;
        this.kode = kode;
        this.tahun = tahun;
        this.penerbit = penerbit;
        this.tandatangan = tandatangan;
        this.status = status;
        this.dasar = dasar;
        this.namaops = namaops;
        this.perintah = perintah;
        this.tanggal = tanggal;
        this.tanggalmulai = tanggalmulai;
        this.tanggalberakhir = tanggalberakhir;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.tps = tps;
    }

    protected TugasList(Parcel in) {
        id = in.readString();
        nomor = in.readString();
        judul = in.readString();
        kode = in.readString();
        tahun = in.readString();
        penerbit = in.readString();
        tandatangan = in.readString();
        status = in.readString();
        dasar = in.readString();
        namaops = in.readString();
        perintah = in.readString();
        tanggal = in.readString();
        tanggalmulai = in.readString();
        tanggalberakhir = in.readString();
        type = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        tps = in.readParcelable(TPS.class.getClassLoader());
    }

    public static final Creator<TugasList> CREATOR = new Creator<TugasList>() {
        @Override
        public TugasList createFromParcel(Parcel in) {
            return new TugasList(in);
        }

        @Override
        public TugasList[] newArray(int size) {
            return new TugasList[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNomor() {
        return nomor;
    }

    public String getJudul() {
        return judul;
    }

    public String getKode() {
        return kode;
    }

    public String getTahun() {
        return tahun;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public String getTandatangan() {
        return tandatangan;
    }

    public String getStatus() {
        return status;
    }

    public String getDasar() {
        return dasar;
    }

    public String getNamaops() {
        return namaops;
    }

    public String getPerintah() {
        return perintah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTanggalmulai() {
        return tanggalmulai;
    }

    public String getTanggalberakhir() {
        return tanggalberakhir;
    }

    public String getType() {
        return type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public TPS getTps() {
        return tps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nomor);
        parcel.writeString(judul);
        parcel.writeString(kode);
        parcel.writeString(tahun);
        parcel.writeString(penerbit);
        parcel.writeString(tandatangan);
        parcel.writeString(status);
        parcel.writeString(dasar);
        parcel.writeString(namaops);
        parcel.writeString(perintah);
        parcel.writeString(tanggal);
        parcel.writeString(tanggalmulai);
        parcel.writeString(tanggalberakhir);
        parcel.writeString(type);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeParcelable(tps, i);
    }
}