/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfdc.podio.model;

/**
 *
 * @author Siddhrajsinh_Atodari
 */
public class SalesforcePodioItemModel {
    
    public String propsfdcid;
    public String letter;
    public String cutdate;
    public String legacy;
    public String masterservice;
    public String contract;

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getMasterservice() {
        return masterservice;
    }

    public void setMasterservice(String masterservice) {
        this.masterservice = masterservice;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public String getCutdate() {
        return cutdate;
    }

    public void setCutdate(String cutdate) {
        this.cutdate = cutdate;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getPropsfdcid() {
        return propsfdcid;
    }

    public void setPropsfdcid(String propsfdcid) {
        this.propsfdcid = propsfdcid;
    }

    public String getPropaddress() {
        return propaddress;
    }

    public void setPropaddress(String propaddress) {
        this.propaddress = propaddress;
    }

    public String getPropname() {
        return propname;
    }

    public void setPropname(String propname) {
        this.propname = propname;
    }

    public String getPropaccnumber() {
        return propaccnumber;
    }

    public void setPropaccnumber(String propaccnumber) {
        this.propaccnumber = propaccnumber;
    }

    public String getPropdwellings() {
        return propdwellings;
    }

    public void setPropdwellings(String propdwellings) {
        this.propdwellings = propdwellings;
    }
    public String propaddress;
    public String propname;
    public String propaccnumber;
    public String propdwellings;
    public String billingsys;
    public String pmsd;
    public String nmsd;

    public String getBillingsys() {
        return billingsys;
    }

    public void setBillingsys(String billingsys) {
        this.billingsys = billingsys;
    }

    public String getPmsd() {
        return pmsd;
    }

    public void setPmsd(String pmsd) {
        this.pmsd = pmsd;
    }

    public String getNmsd() {
        return nmsd;
    }

    public void setNmsd(String nmsd) {
        this.nmsd = nmsd;
    }

    public String getBillingopticket() {
        return billingopticket;
    }

    public void setBillingopticket(String billingopticket) {
        this.billingopticket = billingopticket;
    }

    public String getEqcable() {
        return eqcable;
    }

    public void setEqcable(String eqcable) {
        this.eqcable = eqcable;
    }

    public String getEqdata() {
        return eqdata;
    }

    public void setEqdata(String eqdata) {
        this.eqdata = eqdata;
    }
    public String billingopticket;
    public String eqcable;
    public String eqdata;
    
}
