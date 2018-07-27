/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfdc.podio.operation;

import com.podio.filter.FilterByValue;
import com.podio.filter.TitleFilterBy;
import com.podio.item.FieldValuesUpdate;
import com.podio.item.Item;
import com.podio.item.ItemAPI;
import com.podio.item.ItemBadge;
import com.podio.item.ItemUpdate;
import com.podio.item.ItemsResponse;
import com.sfdc.podio.connection.SalesforcePodioConnectionPool;
import com.sfdc.podio.util.SalesforcePodioConfiguration;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Siddhrajsinh_Atodari
 */
public class PodioSalesforceOperation {

    public static String sendPropertyStatusDataToSalesforce(String statusData) throws UnsupportedEncodingException, IOException {
        Properties prop = SalesforcePodioConfiguration.loadConfiguration();
        String salesforceEndpoint = (System.getenv("SFDC_ENDPOINT") != null
                ? System.getenv("SFDC_ENDPOINT")
                : prop.getProperty("sfdcendpoint"));

        StringEntity entity = new StringEntity(statusData);
        System.out.println("-" + salesforceEndpoint + "-");
        entity.setContentType("application/json");
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(salesforceEndpoint);
        request.setEntity(entity);

        HttpResponse res = httpClient.execute(request);
        //System.out.println("--->" + res);
        //System.out.println(res.getStatusLine().getStatusCode());
        //System.out.println(res.getEntity().getContentLength());
        ResponseHandler<String> handler = new BasicResponseHandler();
        String result = handler.handleResponse(res);
        System.out.println("Update Salesforce Status Result :" + result);
        return result.replace("\"", "");
    }

    public static void getPropertyStatusDataFromPodio() throws IOException {
        Properties prop = SalesforcePodioConfiguration.loadConfiguration();
        String AppName = (System.getenv("PODIO_APP") != null
                ? System.getenv("PODIO_APP")
                : prop.getProperty("podioapp"));
        ItemAPI itemAPI = SalesforcePodioConnectionPool.podioConncetion().getAPI(ItemAPI.class);
        ItemsResponse response = itemAPI.getItems(
                SalesforcePodioOperation.getApplicationId(AppName),
                null,
                null,
                null,
                null,
                new FilterByValue<String>(new TitleFilterBy(),
                        "Yes"));
        List<Integer> itemId = new ArrayList<Integer>();
        System.out.println("Property Size " + response.getItems().size());
        String jsonData = "[";
        Boolean flag = false;
        for (ItemBadge d : response.getItems()) {
            //System.out.println("--->" + d.getTitle());
            //System.out.println("--->" + d.getFields().get(1).getExternalId());
            //System.out.println("--->" + d.getFields().get(2).getExternalId());
            if (d.getFields().get(1).getExternalId().equalsIgnoreCase("salesforce-id") && d.getFields().get(2).getExternalId().equalsIgnoreCase("status")) {
                itemId.add(d.getId());
                //System.out.println("-2-->" + d.getFields().get(1).getValues().get(0).values().toArray()[0]);
                String id = d.getFields().get(1).getValues().get(0).values().toArray()[0].toString();
                //System.out.println("-2-->" + d.getFields().get(2).getValues().get(0).values().toArray()[0].toString().replace("{", "").replace("}", "").split(",")[1].split("=")[1]);
                String status = d.getFields().get(2).getValues().get(0).values().toArray()[0].toString().replace("{", "").replace("}", "").split(",")[1].split("=")[1];
                jsonData += "{\\\"sfdcId\\\": \\\"" + id + "\\\" ,\\\"status\\\" : \\\"" + status + "\\\"},";
                flag = true;
            }
        }
        jsonData = jsonData.substring(0, jsonData.length() - 1);
        jsonData += "]";
        if (flag) {
            String payload = "{\"propertyStatusData\":\"" + jsonData + "\"}";
            System.out.println(" Json Data :" + payload);
            if (sendPropertyStatusDataToSalesforce(payload).equalsIgnoreCase("success")) {
                for (Integer in : itemId) {
                    List<FieldValuesUpdate> lupdate = new ArrayList<FieldValuesUpdate>();
                    lupdate.add(new FieldValuesUpdate("ready-to-send-status", "value", "No"));
                    ItemUpdate updateObj = new ItemUpdate(null, lupdate);
                    //System.out.println("--loop1--->" + in);
                    Item iobj = itemAPI.getItem(in);
                    //System.out.println("---" + iobj.getRevisions().get(0).getRevision());
                    updateObj.setRevision(iobj.getRevisions().get(0).getRevision());
                    itemAPI.updateItem(in, updateObj, true, false);
                }
                System.out.println("Successfully Reset Podio Flag");
            }
        }
    }
    public static void main(String[] args) throws IOException {
        getPropertyStatusDataFromPodio();
    }
}
