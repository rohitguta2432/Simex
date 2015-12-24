package com.softage.paytm.spring.controllers;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.service.PaytmMasterService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for the application home page.
 */
@Controller
class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private PaytmMasterService paytmMasterService;

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
			if(count!=0) {
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

	public JSONObject getTeleCallingData(){
		JSONObject jsonObject =new JSONObject();
		PaytmMastEntity paytmMastData=paytmMasterService.getPaytmMastData();
		   System.out.println(paytmMastData);
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