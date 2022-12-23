

public class SendLogJob {
	
	public static void main(String[] args) {
		SendLogCronTrigger trigger = new SendLogCronTrigger("0 0 12 1/1 * ? *", StartSendLog.class);
		trigger.searchTest();
	}
}
