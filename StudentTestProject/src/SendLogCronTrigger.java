

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SendLogCronTrigger {
	private String timer;
	private Class<? extends Job> job;
	private Scheduler scheduler;
	
	public SendLogCronTrigger(String timer, Class<? extends Job> job) {
		super();
		this.timer = timer;
		this.job = job;
	}
	
	public void searchTest() {
		SchedulerFactory factory = new StdSchedulerFactory();
		JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(job.getName(), "group").build();
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("triger", "group")
			.withSchedule(CronScheduleBuilder.cronSchedule(timer)).build();
		File file = new File("error.txt");
		FileReader fr = null;
		BufferedReader br = null;
			
		try {
			scheduler = factory.getScheduler();
			scheduler.start();
			scheduler.scheduleJob(jobDetail, cronTrigger);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			while(true) {
				String str = br.readLine();
				if(str == null) break;
				System.out.println(str);
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
				if(fr != null) fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}









