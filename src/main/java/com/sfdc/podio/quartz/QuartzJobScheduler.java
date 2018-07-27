/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfdc.podio.quartz;

import com.sfdc.podio.util.SalesforcePodioConfiguration;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Siddhrajsinh_Atodari
 */
public class QuartzJobScheduler {

    public static SchedulerFactory schedulerFactory;
    public static Scheduler scheduler;

    public void Start() throws SchedulerException {
        System.out.println(" Invoke Start Job");
        this.schedulerFactory = new StdSchedulerFactory();
        this.scheduler = schedulerFactory.getScheduler();
        Properties prop = SalesforcePodioConfiguration.loadConfiguration();
        String interval = (System.getenv("INTERVAL") != null
                ? System.getenv("INTERVAL")
                : prop.getProperty("interval"));
        try {
            JobDetail jobDetail = JobBuilder.newJob(UpdatePropertyStatusToSalesforceJob.class).withIdentity("updateJob", "PodiotoSalesforce").build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("updateJobTrigger", "PodiotoSalesforce")
                    .startNow()
                    .withSchedule(
                            SimpleScheduleBuilder.repeatMinutelyForever(Integer.parseInt(interval))
                    )
                    .build();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            System.out.println(" Error in QuartzJobScheduler :" + e);
        }

    }

    public void stop() throws SchedulerException {
        System.out.println(" Invoke Stop Job");
        this.scheduler.shutdown();
    }

}
