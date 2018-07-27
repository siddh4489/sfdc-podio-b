/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfdc.podio.operation;

import com.podio.APIApplicationException;
import com.podio.app.AppAPI;
import com.podio.app.Application;
import com.podio.item.FieldValuesUpdate;
import com.podio.item.Item;
import com.podio.item.ItemAPI;
import com.podio.item.ItemCreate;
import com.podio.item.ItemUpdate;
import com.sfdc.podio.connection.SalesforcePodioConnectionPool;
import com.sfdc.podio.model.SalesforcePodioItemModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Siddhrajsinh_Atodari
 *
 * This SalesforcePodioOperation class is used to perform CRUD operation in
 * Podio.
 */
public class SalesforcePodioOperation {

    /**
     * This getApplicationId method is used get Application Id from Podio.
     *
     * @param AppName this is Podio Application name to Connect App.
     * @return Integer This is Application Id.
     */
    public static Integer getApplicationId(String AppName) {
        AppAPI appAPI = SalesforcePodioConnectionPool.podioConncetion().getAPI(AppAPI.class);
        List<Application> apps = appAPI.getApps();

        Integer AppId = null;
        for (Application a : apps) {
            if (a.getConfiguration().getName().equalsIgnoreCase(AppName)) {
                AppId = a.getId();
            }

        }
        return AppId;
    }

    /**
     * This newProperty method is used create new Property in Podio.
     *
     * @param AppId this is Podio Application Id.
     * @param SalesforcePodioItemModel This is data model.
     * @return Integer this is created property's Item Id.
     */
    public static Integer newProperty(Integer AppId, SalesforcePodioItemModel modelObj) {
        int itemId = 400;

        try {

            ItemAPI itemAPI = SalesforcePodioConnectionPool.podioConncetion().getAPI(ItemAPI.class);
            List<FieldValuesUpdate> fieldList = new ArrayList<FieldValuesUpdate>();
            fieldList.add(new FieldValuesUpdate("salesforce-id", "value", (modelObj.getPropsfdcid() != null && !modelObj.getPropsfdcid().isEmpty() ? modelObj.getPropsfdcid() : "-")));
            fieldList.add(new FieldValuesUpdate("index-numbertitle", "value", (modelObj.getPropname() != null && !modelObj.getPropname().isEmpty() ? modelObj.getPropname() : "-")));
            fieldList.add(new FieldValuesUpdate("property-street-address", "value", (modelObj.getPropaddress() != null && !modelObj.getPropaddress().isEmpty() ? modelObj.getPropaddress() : "-")));
            fieldList.add(new FieldValuesUpdate("property-account-number-2", "value", (modelObj.getPropaccnumber() != null && !modelObj.getPropaccnumber().isEmpty() ? modelObj.getPropaccnumber() : 0)));
            fieldList.add(new FieldValuesUpdate("number-of-dwellings", "value", (modelObj.getPropdwellings() != null && !modelObj.getPropdwellings().isEmpty() ? modelObj.getPropdwellings() : 0)));
            fieldList.add(new FieldValuesUpdate("billing-system", "value", (modelObj.getBillingsys() != null && !modelObj.getBillingsys().isEmpty() ? modelObj.getBillingsys() : "-")));
            fieldList.add(new FieldValuesUpdate("prior-master-service-description-ex-tvhsd-service-packa", "value", (modelObj.getPmsd() != null && !modelObj.getPmsd().isEmpty() ? modelObj.getPmsd() : "-")));
            fieldList.add(new FieldValuesUpdate("new-master-service-description", "value", (modelObj.getNmsd() != null && !modelObj.getNmsd().isEmpty() ? modelObj.getNmsd() : "-")));
            fieldList.add(new FieldValuesUpdate("eq-responsibility-cable", "value", (modelObj.getEqcable() != null && !modelObj.getEqcable().isEmpty() ? modelObj.getEqcable() : "-")));
            fieldList.add(new FieldValuesUpdate("eq-responsibility-data", "value", (modelObj.getEqdata() != null && !modelObj.getEqdata().isEmpty() ? modelObj.getEqdata() : "-")));
            fieldList.add(new FieldValuesUpdate("billing-ticket-number-to-update-accounts", "value", (modelObj.getBillingopticket() != null && !modelObj.getBillingopticket().isEmpty() ? modelObj.getBillingopticket() : 0)));
            fieldList.add(new FieldValuesUpdate("date-letter-sent", "start", (modelObj.getLetter() != null && !modelObj.getLetter().isEmpty() ? modelObj.getLetter() : "-")));
            fieldList.add(new FieldValuesUpdate("desired-contract-cutover-date", "start", (modelObj.getCutdate() != null && !modelObj.getCutdate().isEmpty() ? modelObj.getCutdate() : "-")));
            fieldList.add(new FieldValuesUpdate("legacy-company", "value", (modelObj.getLegacy() != null && !modelObj.getLegacy().isEmpty() ? modelObj.getLegacy() : "-")));
            fieldList.add(new FieldValuesUpdate("contract-type", "value", (modelObj.getContract() != null && !modelObj.getContract().isEmpty() ? modelObj.getContract() : "-")));

            List<Map<String, ?>> serviceList = new ArrayList<Map<String, ?>>();

            if (modelObj.getMasterservice() != null && modelObj.getMasterservice() != "") {
                String[] serviceArray = modelObj.getMasterservice().split(",");
                for (int i = 0; i < serviceArray.length; i++) {
                    Map<String, String> ObjMap = new HashMap<String, String>();
                    ObjMap.put("value", serviceArray[i]);
                    serviceList.add(ObjMap);
                }
                fieldList.add(new FieldValuesUpdate("new-master-services-to-be-delivered", serviceList));
            }

            itemId = itemAPI.addItem(AppId, new ItemCreate(null, fieldList, null, null), false);
        } catch (APIApplicationException ex) {
            System.out.println("newProperty :" + ex);
            return itemId;
        }
        return itemId;
    }

    /**
     * This updateProperty method is used update existion Property in Podio.
     *
     * @param ItemId this is Podio Item Id.
     * @param SalesforcePodioItemModel This is data model.
     * @return Boolean this is updation status.
     */
    public static boolean updateProperty(Integer ItemId, SalesforcePodioItemModel modelObj) {
        try {
            ItemAPI itemAPI = SalesforcePodioConnectionPool.podioConncetion().getAPI(ItemAPI.class);
            List<FieldValuesUpdate> lupdate = new ArrayList<FieldValuesUpdate>();
            lupdate.add(new FieldValuesUpdate("salesforce-id", "value", (modelObj.getPropsfdcid() != null && !modelObj.getPropsfdcid().isEmpty() ? modelObj.getPropsfdcid() : "-")));
            lupdate.add(new FieldValuesUpdate("index-numbertitle", "value", (modelObj.getPropname() != null && !modelObj.getPropname().isEmpty() ? modelObj.getPropname() : "-")));
            lupdate.add(new FieldValuesUpdate("property-street-address", "value", (modelObj.getPropaddress() != null && !modelObj.getPropaddress().isEmpty() ? modelObj.getPropaddress() : "-")));
            lupdate.add(new FieldValuesUpdate("property-account-number-2", "value", (modelObj.getPropaccnumber() != null && !modelObj.getPropaccnumber().isEmpty() ? modelObj.getPropaccnumber() : 0)));
            lupdate.add(new FieldValuesUpdate("number-of-dwellings", "value", (modelObj.getPropdwellings() != null && !modelObj.getPropdwellings().isEmpty() ? modelObj.getPropdwellings() : 0)));

            lupdate.add(new FieldValuesUpdate("billing-system", "value", (modelObj.getBillingsys() != null && !modelObj.getBillingsys().isEmpty() ? modelObj.getBillingsys() : "-")));
            lupdate.add(new FieldValuesUpdate("prior-master-service-description-ex-tvhsd-service-packa", "value", (modelObj.getPmsd() != null && !modelObj.getPmsd().isEmpty() ? modelObj.getPmsd() : "-")));
            lupdate.add(new FieldValuesUpdate("new-master-service-description", "value", (modelObj.getNmsd() != null && !modelObj.getNmsd().isEmpty() ? modelObj.getNmsd() : "-")));
            lupdate.add(new FieldValuesUpdate("eq-responsibility-cable", "value", (modelObj.getEqcable() != null && !modelObj.getEqcable().isEmpty() ? modelObj.getEqcable() : "-")));
            lupdate.add(new FieldValuesUpdate("eq-responsibility-data", "value", (modelObj.getEqdata() != null && !modelObj.getEqdata().isEmpty() ? modelObj.getEqdata() : "-")));
            lupdate.add(new FieldValuesUpdate("billing-ticket-number-to-update-accounts", "value", (modelObj.getBillingopticket() != null && !modelObj.getBillingopticket().isEmpty() ? modelObj.getBillingopticket() : 0)));
            lupdate.add(new FieldValuesUpdate("date-letter-sent", "start", (modelObj.getLetter() != null && !modelObj.getLetter().isEmpty() ? modelObj.getLetter() : "-")));
            lupdate.add(new FieldValuesUpdate("desired-contract-cutover-date", "start", (modelObj.getCutdate() != null && !modelObj.getCutdate().isEmpty() ? modelObj.getCutdate() : "-")));
            lupdate.add(new FieldValuesUpdate("legacy-company", "value", (modelObj.getLegacy() != null && !modelObj.getLegacy().isEmpty() ? modelObj.getLegacy() : "-")));
            lupdate.add(new FieldValuesUpdate("contract-type", "value", (modelObj.getContract() != null && !modelObj.getContract().isEmpty() ? modelObj.getContract() : "-")));  
            List<Map<String, ?>> serviceList = new ArrayList<Map<String, ?>>();

            if (modelObj.getMasterservice() != null && modelObj.getMasterservice() != "") {
                String[] serviceArray = modelObj.getMasterservice().split(",");
                for (int i = 0; i < serviceArray.length; i++) {
                    Map<String, String> ObjMap = new HashMap<String, String>();
                    ObjMap.put("value", serviceArray[i]);
                    serviceList.add(ObjMap);
                }
                lupdate.add(new FieldValuesUpdate("new-master-services-to-be-delivered", serviceList));
            }
            ItemUpdate updateObj = new ItemUpdate(null, lupdate);
            Item iobj = itemAPI.getItem(ItemId);
            updateObj.setRevision(iobj.getRevisions().get(0).getRevision());
            itemAPI.updateItem(ItemId, updateObj, true, false);
        } catch (APIApplicationException ex) {
            System.out.println("updateProperty :" + ex);
            return false;
        }

        return true;
    }

    /**
     * This existingPropertyCheck method is used check existion Property in
     * Podio.
     *
     * @param ItemId this is Podio Item Id.
     * @return Boolean this is return true is available and false if not
     * available.
     */
    public static boolean existingPropertyCheck(Integer itemId) {
        boolean existing = false;
        try {
            ItemAPI itemAPI = SalesforcePodioConnectionPool.podioConncetion().getAPI(ItemAPI.class);
            Item iobj = itemAPI.getItem(itemId);
            if (iobj != null) {
                existing = true;
            }
        } catch (APIApplicationException apiEx) {
            if (apiEx.getStatus().toString().equalsIgnoreCase("Forbidden")) {
                existing = false;
            }

        }

        return existing;
    }
}
