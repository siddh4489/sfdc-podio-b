/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfdc.podio.parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sfdc.podio.model.SalesforcePodioItemModel;

/**
 *
 * @author Siddhrajsinh_Atodari
 * 
 * This class SalesforcePodioDataParser is used to Parse the JSON data to Object.
 */
public class SalesforcePodioDataParser {
     /**
     * This jsonToObject method is used to convert JSON to Object.
     * @param jsonData this is property data in JSON formate.
     * @return SalesforcePodioItemModel This is converted model object.
     */
    public static SalesforcePodioItemModel jsonToObject(String jsonData) {
        SalesforcePodioItemModel modelObj = new SalesforcePodioItemModel();
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(jsonData).getAsJsonObject();
        modelObj.setPropsfdcid(object.get("sfdcid").toString().replace("\"",""));
        modelObj.setPropname(object.get("name").toString().replace("\"",""));
        modelObj.setPropaddress(object.get("address").toString().replace("\"",""));
        modelObj.setPropaccnumber(object.get("accountnumber").toString().replace("\"",""));
        modelObj.setPropdwellings(object.get("doors").toString().replace("\"",""));
        modelObj.setBillingsys(object.get("billingsys").toString().replace("\"",""));
        modelObj.setPmsd(object.get("pmsd").toString().replace("\"",""));
        modelObj.setNmsd(object.get("nmsd").toString().replace("\"",""));
        modelObj.setBillingopticket(object.get("billingopticket").toString().replace("\"",""));
        modelObj.setEqcable(object.get("eqcable").toString().replace("\"",""));
        modelObj.setEqdata(object.get("eqdata").toString().replace("\"",""));
        modelObj.setLetter(object.get("letter").toString().replace("\"",""));
        modelObj.setCutdate(object.get("cutdate").toString().replace("\"",""));
        modelObj.setLegacy(object.get("legacy").toString().replace("\"",""));
        modelObj.setMasterservice(object.get("masterservice").toString().replace("\"",""));
        modelObj.setContract(object.get("contract").toString().replace("\"",""));
        return modelObj;
    }
}
