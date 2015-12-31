package com.softage.paytm.spring.controllers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.models.StateMasterEntity;
import com.softage.paytm.service.AgentPaytmService;
import com.softage.paytm.service.CircleService;
import com.softage.paytm.service.PaytmMasterService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for the application home page.
 */
@Controller
class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private PaytmMasterService paytmMasterService;
	@Autowired
	private AgentPaytmService agentPaytmService;
	@Autowired
	public CircleService circleService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		return "app";
	}

	@RequestMapping(value = "/getFilePath", method = RequestMethod.GET)
	public void getFilePath(HttpServletRequest request) {
//		 File input = new File("/x/data.csv");
//		 File output = new File("/x/data.json");

		String csvFile = "D:/CSVFile/TestFile.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();

  try{
		br = new BufferedReader(new FileReader(csvFile));

	  int count=0;
		while ((line = br.readLine()) != null) {
			String[] customerData = line.split(cvsSplitBy);
			if(count!=0 && count==1) {
				HashMap<String, String> map = new HashMap<String, String>();
				System.out.println(customerData[0]);
				map.put("kycRequestId", customerData[0]);
				map.put("CustomerID", customerData[1]);
				map.put("Username", customerData[2]);
				map.put("CustomerPhone", customerData[3]);
				map.put("Email", customerData[4]);
				map.put("AddressID", customerData[5]);
				map.put("TimeSlot", customerData[6]);
				map.put("Priority", customerData[7]);
				map.put("AddressStreet1", customerData[8]);
				map.put("AddressStreet2", customerData[9]);
				map.put("City", customerData[10]);
				map.put("State", customerData[11]);
				map.put("Pincode", customerData[12]);
				map.put("AddressPhone", customerData[13]);
				map.put("VendorName", customerData[14]);
				map.put("StageId", customerData[15]);
				map.put("SubStageId", customerData[16]);
				map.put("CreatedTimestamp", customerData[17]);
				/*map.put("ImportBy", customerData[18]);
				map.put("ImportDate", customerData[19]);
				map.put("otp", customerData[20]);
				map.put("Ref_Code", customerData[21]);*/
				list.add(map);
			}
              count++;
		}
          System.out.println("list   "+list);
	  paytmMasterService.savePaytmMaster(list);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/*	try {

			CSVReader reader = new CSVReader(new FileReader("data.csv"), ',', '"', 1);
			//Read all rows at once
			List<String[]> allRows = reader.readAll();

			//Read CSV line by line and use the string array as you want
			for (String[] row : allRows) {
				System.out.println(Arrays.toString(row));
			}
		} catch (Exception e) {

		}*/
	}
	@RequestMapping(value = "/getTeleCallData", method = RequestMethod.GET)
	public JSONObject getTeleCallingData(){
		JSONObject jsonObject =new JSONObject();
		PaytmMastEntity paytmMastData=paytmMasterService.getPaytmMastData();
		System.out.println(paytmMastData);
		return  jsonObject;
	}

	@RequestMapping(value = "/telecallingScreen", method = RequestMethod.GET)
	public JSONObject telecallingScreen(){
		String userName="";
		JSONObject jsonObject =new JSONObject();
		List list=paytmMasterService.telecallingScreen(userName);
        List<StateMasterEntity> stateList=paytmMasterService.getStateList();
		List<CallStatusMasterEntity> statusList= paytmMasterService.getStatusList();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar date = Calendar.getInstance();
		String dateList1[]=new String[7];
		List<String> dateList=new ArrayList<>();
		for(int i = 0; i < 7;i++){

			dateList1[i]  = format.format(date.getTime());
			date.add(Calendar.DATE  , 1);
			dateList.add(dateList1[i]);
		}

		return jsonObject;
	}
	@RequestMapping(value = "/agentRegistration", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String agentRegistration(HttpServletRequest request){
		String msg="";
		try {
			String agentName = request.getParameter("agent_name");
			String agentCode = request.getParameter("agent_code");
			String empCode = request.getParameter("employee");
			String mobileNo = request.getParameter("phone");
			String circleOffice = request.getParameter("circle_office");
			String spokeCode = request.getParameter("spoke_code");
			String avalTime = request.getParameter("avl_tim");
			String pincode = request.getParameter("pin_code");
			String multipin = request.getParameter("multi_pin");
			String email = request.getParameter("email");
			PaytmagententryEntity paytmagententryEntity = new PaytmagententryEntity();
			       paytmagententryEntity.setAfullname(agentName);
			       paytmagententryEntity.setAcode(agentCode);
			       paytmagententryEntity.setEmpcode(empCode);
			       paytmagententryEntity.setAphone(mobileNo);
			       paytmagententryEntity.setAspokecode(spokeCode);
			       paytmagententryEntity.setAavailslot(avalTime);
			       paytmagententryEntity.setApincode(pincode);
			       paytmagententryEntity.setMulitplePin(multipin);
			       paytmagententryEntity.setAemailId(email);
			       paytmagententryEntity.setImportby("system");
			       paytmagententryEntity.setImportdate(new Timestamp(new Date().getTime()));
		           msg= agentPaytmService.saveAgent(paytmagententryEntity);

		}catch (Exception e){
			e.printStackTrace();
		}
		    return  msg;
	}
	@RequestMapping(value = "/customerCalling", method = {RequestMethod.GET,RequestMethod.POST})
	public String Call(){
		String msg=null;
		String url = "http://27.124.7.54:5000/ConvoqueAPI/placeACall.jsp?src=8882905998&dst=8588875378&callerID=8882905998";
   try {
	      URL obj = new URL(url);
	      HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	// optional default is GET
	      con.setRequestMethod("GET");
	      con.setRequestMethod("POST");

	//add request header
//	con.setRequestProperty("User-Agent", USER_AGENT);

	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();

	//print result
	System.out.println(response.toString());
} catch (Exception e){

  }
	return  msg;
	}

	@RequestMapping(value = "/getCirles", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
public JSONObject getCircleName(){

		JSONObject jsonObject=new JSONObject();
		List<String> circles= circleService.getCirleList();
         jsonObject.put("circles",circles);
	 return  jsonObject;
}
	@RequestMapping(value = "/getSpokeCode", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
public JSONObject getSpokeCode(@RequestParam (value = "circleName")String circleName){
	JSONObject jsonObject=new JSONObject();

	return  jsonObject;
}

}
/*


	 }
*/

		// List<Map<?, ?>> data = readObjectsFromCsv(input);
		// writeAsJson(data, output);



/*	public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
		CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);

		return mappingIterator.readAll();
	}

	public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(file, data);
	}*/