/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfdc.podio.quartz;

import com.podio.filter.FilterByValue;
import com.podio.filter.TitleFilterBy;
import com.podio.item.ItemsResponse;
import com.sfdc.podio.operation.PodioSalesforceOperation;
import com.sfdc.podio.operation.SalesforcePodioOperation;
import com.sfdc.podio.util.SalesforcePodioConfiguration;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Siddhrajsinh_Atodari
 */
public class UpdatePropertyStatusToSalesforceJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Job Start Date Time :" + dateFormat.format(date));
        try {
            PodioSalesforceOperation.getPropertyStatusDataFromPodio();
        } catch (IOException ex) {
            Logger.getLogger(UpdatePropertyStatusToSalesforceJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Job End Date Time :" + dateFormat.format(date));

    }

}
