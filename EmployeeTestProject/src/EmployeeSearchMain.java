import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeSearchMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("검색할 종류를 입력하세요");
		System.out.println("이름 - name");
		System.out.println("부서 - department");
		System.out.println("직급 - position");
		String kind = sc.nextLine();
		System.out.println("검색할 내용을 입력하세요>");
		String search = sc.nextLine();
		try {
			search = URLEncoder.encode(search,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String apiUrl = "http://localhost:9999/search.do?kind="+kind+"&search="+search;
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String result = "";
			while(true) {
				String str = br.readLine();
				if(str == null) break;
				result += str;
			}
//			System.out.println(result);
			JSONObject json = new JSONObject(result);
			if(json.getInt("code") == 500)
				throw new Exception(json.getInt("code") + "\t" 
										+ json.getString("message"));
			JSONArray array = json.getJSONArray("result");
			for(int i=0;i<array.length();i++) {
				JSONObject obj = array.getJSONObject(i);
				System.out.println(
						obj.getString("eno") + "\t"
						+ obj.getString("name") + "\t"
						+ obj.getString("department") + "\t"
						+ obj.getInt("position")
						);
				
			}
				
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//서버쪽에서 결과 코드를 500 보냈을떄 로그로 처리하는 부분
			try {
				FileOutputStream fos = new FileOutputStream("error.txt",true);
				PrintWriter pw = new PrintWriter(fos);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_hh:mm:ss");
				String str = sdf.format(Calendar.getInstance().getTime())+"\t"+
				e.getMessage();
				pw.println(str);
				pw.flush();
				pw.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

}










