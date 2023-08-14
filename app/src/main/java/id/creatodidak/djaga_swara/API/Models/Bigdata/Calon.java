package id.creatodidak.djaga_swara.API.Models.Bigdata;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.Bupati;
import id.creatodidak.djaga_swara.API.Models.Multi.DPDRI;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDKab;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDProv;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRRI;
import id.creatodidak.djaga_swara.API.Models.Multi.Gubernur;
import id.creatodidak.djaga_swara.API.Models.Multi.Kades;
import id.creatodidak.djaga_swara.API.Models.Multi.PartaiKab;
import id.creatodidak.djaga_swara.API.Models.Multi.PartaiNas;
import id.creatodidak.djaga_swara.API.Models.Multi.PartaiProv;
import id.creatodidak.djaga_swara.API.Models.Multi.Presiden;

public class Calon {
    private List<PartaiNas> pnas;
    private List<PartaiProv> pprov;
    private List<PartaiKab> pkab;
    private List<Presiden> presiden;
    private List<Gubernur> gubernur;
    private List<Bupati> bupati;
    private List<Kades> kades;
    private List<DPRRI> dprri;
    private List<DPDRI> dpdri;
    private List<DPRDProv> dprdprov;
    private List<DPRDKab> dprdkab;

    // Getter and setter for pnas
    public List<PartaiNas> getPnas() {
        return pnas;
    }

    public void setPnas(List<PartaiNas> pnas) {
        this.pnas = pnas;
    }

    // Getter and setter for pprov
    public List<PartaiProv> getPprov() {
        return pprov;
    }

    public void setPprov(List<PartaiProv> pprov) {
        this.pprov = pprov;
    }

    // Getter and setter for pkab
    public List<PartaiKab> getPkab() {
        return pkab;
    }

    public void setPkab(List<PartaiKab> pkab) {
        this.pkab = pkab;
    }

    // Getter and setter for presiden
    public List<Presiden> getPresiden() {
        return presiden;
    }

    public void setPresiden(List<Presiden> presiden) {
        this.presiden = presiden;
    }

    // Getter and setter for gubernur
    public List<Gubernur> getGubernur() {
        return gubernur;
    }

    public void setGubernur(List<Gubernur> gubernur) {
        this.gubernur = gubernur;
    }

    // Getter and setter for bupati
    public List<Bupati> getBupati() {
        return bupati;
    }

    public void setBupati(List<Bupati> bupati) {
        this.bupati = bupati;
    }

    // Getter and setter for kades
    public List<Kades> getKades() {
        return kades;
    }

    public void setKades(List<Kades> kades) {
        this.kades = kades;
    }

    // Getter and setter for dprri
    public List<DPRRI> getDprri() {
        return dprri;
    }

    public void setDprri(List<DPRRI> dprri) {
        this.dprri = dprri;
    }

    // Getter and setter for dpdri
    public List<DPDRI> getDpdri() {
        return dpdri;
    }

    public void setDpdri(List<DPDRI> dpdri) {
        this.dpdri = dpdri;
    }

    // Getter and setter for dprdprov
    public List<DPRDProv> getDprdprov() {
        return dprdprov;
    }

    public void setDprdprov(List<DPRDProv> dprdprov) {
        this.dprdprov = dprdprov;
    }

    // Getter and setter for dprdkab
    public List<DPRDKab> getDprdkab() {
        return dprdkab;
    }

    public void setDprdkab(List<DPRDKab> dprdkab) {
        this.dprdkab = dprdkab;
    }
}
