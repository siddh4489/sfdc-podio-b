/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfdc.podio.rest;

import com.sfdc.podio.operation.SalesforcePodioOperation;
import com.sfdc.podio.parser.SalesforcePodioDataParser;
import com.sfdc.podio.quartz.QuartzJobScheduler;
import com.sfdc.podio.util.SalesforcePodioConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.quartz.SchedulerException;

/**
 * The SalesforcePodioRestApi class is the Starting point of Salesforce - Podio
 * Api.
 *
 * @author Siddharaj Atodaria
 */
@Path("/podio")
public class SalesforcePodioRestApi {

    /**
     * This newProperty method is used to create new Property in Podio.
     *
     * @param data this is Property data in json format.
     * @param itemId this is the Podio itemId to check existion Podio item.
     * @return String This is return the created Property's ItemId.
     */
    @POST
    @Path("/newProperty")
    public Response newProperty(String data, @HeaderParam("itemId") String itemId) {
        String itemref = "";
        System.out.println(" data : " + data);
        System.out.println(" itemId : " + itemId);
        System.out.println(" called method : newProperty ");
        Properties prop = SalesforcePodioConfiguration.loadConfiguration();
        String AppName = (System.getenv("PODIO_APP") != null
                ? System.getenv("PODIO_APP")
                : prop.getProperty("podioapp"));
        itemId = (!itemId.isEmpty() ? itemId : "0");
        if (SalesforcePodioOperation.existingPropertyCheck(Integer.parseInt(itemId))) {
            System.out.println(" In  : Update Property ");
            boolean checkUpdate = SalesforcePodioOperation.updateProperty(Integer.parseInt(itemId), SalesforcePodioDataParser.jsonToObject(data));
            System.out.println(" Update Property Status :" + checkUpdate);
            if (checkUpdate) {
                itemref = "Property Updated Successfully";
            } else {
                itemref = "Property Failed to Update";
            }
        } else {
            System.out.println(" In : New Property");
            itemref = SalesforcePodioOperation.newProperty(SalesforcePodioOperation.getApplicationId(AppName), SalesforcePodioDataParser.jsonToObject(data)).toString();
            System.out.println("Created Property itemid : " + itemref);
            itemref = "New ItemId :" + itemref;
        }
        return Response.status(201).entity(itemref.toString()).build();
    }

    @GET
    @Path("/start")
    @Produces(javax.ws.rs.core.MediaType.TEXT_HTML)
    public Response getInvoke() throws IOException, URISyntaxException, SchedulerException {
        System.out.println("Job Start Request");
        //Qschedular.invokeAction();
        QuartzJobScheduler quaObj = new QuartzJobScheduler();
        quaObj.Start();
        return Response.status(201).entity("Status : Job Started").build();
    }

    @GET
    @Path("/stop")
    @Produces(javax.ws.rs.core.MediaType.TEXT_HTML)
    public Response getStop() throws IOException, URISyntaxException, SchedulerException {
        System.out.println("Job Stop Request");
        QuartzJobScheduler quaObj = new QuartzJobScheduler();
        quaObj.stop();
        return Response.status(201).entity("Status : Job Stoped").build();
    }

}
