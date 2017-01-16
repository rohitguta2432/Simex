package com.softage.paytm.service.imp;

import au.com.bytecode.opencsv.CSVWriter;
import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.dao.PaytmMasterDao;
import com.softage.paytm.models.*;
import com.softage.paytm.service.PaytmMasterService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by SS0085 on 22-12-2015.
 */
@Service
public class PaytmMasterServiceImp implements PaytmMasterService {
    @Autowired
    private PaytmMasterDao paytmMasterDao;

    @Autowired
    private CircleMastDao circleMastDao;

    @Override
    public String savePaytmMaster(List<Map<String, String>> paytmList) {

             List<PaytmMastEntity> custList=new ArrayList<PaytmMastEntity>();
             PaytmMastEntity paytmMastEntity=null;
             String circleCode=null;
             CircleMastEntity circleMastEntity=null;
             String result=null;
        try {
            int i=1;
            for (Map<String, String> map : paytmList) {
                paytmMastEntity = new PaytmMastEntity();
                //paytmMastEntity.setKycRequestId(map.get("kycRequestId"));

                System.out.println(map.get("kycRequestId"));
                paytmMastEntity.setCustomerId(map.get("CustomerID"));
                paytmMastEntity.setUsername(map.get("Username"));
                //it will be change
              //  paytmMastEntity.setCustomerPhone(map.get("CustomerPhone"));
                paytmMastEntity.setEmail(map.get("Email"));
                //paytmMastEntity.setAddressId(map.get("AddressID"));
               // paytmMastEntity.setTimeSlot(map.get("TimeSlot"));
              //  paytmMastEntity.setPriority(map.get("Priority"));
              //  paytmMastEntity.setAddressStreet1(map.get("AddressStreet1"));
              //  paytmMastEntity.setAddressStreet2(map.get("AddressStreet2"));
                paytmMastEntity.setCity(map.get("City"));
                paytmMastEntity.setState(map.get("State"));
                paytmMastEntity.setPincode(map.get("Pincode"));
                String pincode=map.get("Pincode");
                String state=map.get("State");
                String pincode1=pincode.substring(0,2);
                String pincode2=pincode.substring(0, 3);
                int pin1=Integer.parseInt(pincode1);
                int pin2=Integer.parseInt(pincode2);
                int circleCodevalue= getCircleCode(pin1,pin2,state);
                circleMastEntity= circleMastDao.findByPrimaryKey(circleCodevalue);
                if(circleMastEntity!=null){
                    paytmMastEntity.setCircleMastByCirCode(circleMastEntity);
                }
               // paytmMastEntity.setAlternatePhone(map.get("AddressPhone"));
                paytmMastEntity.setVendorName(map.get("VendorName"));
              //  paytmMastEntity.setStageId(map.get("StageId"));
               // paytmMastEntity.setSubStageId(map.get("SubStageId"));
                paytmMastEntity.setCreatedTimestamp(map.get("CreatedTimestamp"));
                paytmMastEntity.setImportDate(new Timestamp(new Date().getTime()));
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(10000);
               // paytmMastEntity.setOtp(new Integer(randomInt).toString());
              //  paytmMastEntity.setRefCode(randomInt);
                paytmMastEntity.setImportBy(map.get("importBy"));
                custList.add(paytmMastEntity);
                i++;
            }
             result=  paytmMasterDao.savePaytmMaster(custList);
        }catch (Exception e){
            e.printStackTrace();
            result="error";
        }

       return  result;
    }

    @Override
    public String savePaytmMasterExcel(List<Map<String, String>> paytmList) {
        List<PaytmMastEntity> custList=new ArrayList<PaytmMastEntity>();
        PaytmMastEntity paytmMastEntity=null;
        String circleCode=null;
        CircleMastEntity circleMastEntity=null;
        String result=null;
        try {
            int i=1;
            for (Map<String, String> map : paytmList) {
                paytmMastEntity = new PaytmMastEntity();
               // paytmMastEntity.setKycRequestId("");
                paytmMastEntity.setCustomerId(map.get("customerID"));
                paytmMastEntity.setUsername(map.get("name"));
                paytmMastEntity.setCustomerPhone(map.get("mobileNumber"));
                paytmMastEntity.setAddress(map.get("address"));
                paytmMastEntity.setCity(map.get("city"));
                paytmMastEntity.setAlternatePhone1(map.get("alternateNumber1"));
                paytmMastEntity.setAlternatePhone2(map.get("alternateNumber2"));
                paytmMastEntity.setChReasonDesc(map.get("ch_reason_desc"));
                paytmMastEntity.setSimType(map.get("sim_type"));
                paytmMastEntity.setSimPlanDesc(map.get("sim_plan_desc"));
                paytmMastEntity.setLotNo(map.get("lot_no"));
                paytmMastEntity.setRemarks(map.get("remarks"));
                paytmMastEntity.setCoID(map.get("co_id"));
                paytmMastEntity.setCoStatus(map.get("co_status"));
                //it will be change
                Date req_date=new Date(map.get("request_date"));
                paytmMastEntity.setRequestDate(req_date);
                //paytmMastEntity.setEmail("");
               //paytmMastEntity.setAddressId(map.get(""));
              //  paytmMastEntity.setTimeSlot("");
                //  paytmMastEntity.setPriority(map.get("Priority"));
               // paytmMastEntity.setAddressStreet1(map.get("address"));
               // paytmMastEntity.setAddressStreet2("");

                //paytmMastEntity.setState("");
                paytmMastEntity.setPincode(map.get("pincode"));
                String pincode=map.get("pincode");
                String pincode1=pincode.substring(0,2);
                String pincode2=pincode.substring(0,3);
                int pin1=Integer.parseInt(pincode1);
                int pin2=Integer.parseInt(pincode2);
                int circleCodevalue= getCircleCode(pin1,pin2,"");
                circleMastEntity= circleMastDao.findByPrimaryKey(circleCodevalue);
                if(circleMastEntity!=null){
                    paytmMastEntity.setCircleMastByCirCode(circleMastEntity);
                }
               // paytmMastEntity.setAlternatePhone("");
                paytmMastEntity.setVendorName("Vodafone");
                //paytmMastEntity.setStageId("");
                //paytmMastEntity.setSubStageId("");
                paytmMastEntity.setCreatedTimestamp("");
                paytmMastEntity.setImportDate(new Timestamp(new Date().getTime()));
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(10000);
                //paytmMastEntity.setOtp(new Integer(randomInt).toString());
                //paytmMastEntity.setRefCode(randomInt);
                paytmMastEntity.setImportBy(map.get("importBy"));
                custList.add(paytmMastEntity);
                i++;
            }
          //  result=  paytmMasterDao.savePaytmMaster(custList);
            for(PaytmMastEntity paytmMastEntity1:custList){
                result= paytmMasterDao.savePaytmMaster(paytmMastEntity1);
            }


        }catch (Exception e){
            e.printStackTrace();
            result="error";
        }

        return  result;
    }

    @Override
    public JSONObject getPaytmMastData(int cust_uid) {
        JSONObject jsonObject =paytmMasterDao.getPaytmMastData(cust_uid);
        return  jsonObject;

    }
    @Override
    public PaytmMastEntity getPaytmMaster(String mobileNo) {
        return paytmMasterDao.findOne(mobileNo);
    }
    @Override
    public PaytmMastEntity getPaytmMastDatas(int cust_uid){
        return paytmMasterDao.getPaytmMasterData(cust_uid);
    }

    @Override
    public PaytmcustomerDataEntity getPaytmCustomerData(int cust_uid) {
        return paytmMasterDao.getPaytmCustomerData(cust_uid);
    }

    @Override
    public PaytmMastEntity getPaytmMasterByDate(String mobileNo, Date date) {
        return paytmMasterDao.getPaytmMastEntityByDate(mobileNo,date);
    }

    @Override
    public PaytmMastEntity getPaytmmasterServiceDate(String cust_uid) {

        //int customer=Integer.parseInt(cust_uid);
        return paytmMasterDao.getpaytmmasterservice(cust_uid);
    }

    @Override
    public SpokeMastEntity spokeMastEntity(String spokecode) {
        return paytmMasterDao.getSpokemast(spokecode);
    }

    @Override
    public AllocationMastEntity getallocationMastEntity(int custid, int jobid) {
        return paytmMasterDao.getAllocationentity(custid,jobid);
    }

    @Override
    public JSONObject telecallingScreen(String userName,int cirCode) {
        JSONObject json=null;
        for(int i=0; i<=5; i++){
            json= paytmMasterDao.telecallingScreen(userName,cirCode);
            if(json.size()>1){
                break;
            }
        }
        return json;
    }
    @Override
    public List<StateMasterEntity> getStateList() {
       List<StateMasterEntity> listState=paytmMasterDao.getStatemaster();
        return listState;
    }
    @Override
    public List<CallStatusMasterEntity> getStatusList() {

           List<CallStatusMasterEntity>  statusList= paytmMasterDao.getStatusList();
           return statusList;
    }

    @Override
    public void uploadRejectedData(List<Map<String, String>> list,File fileName) {
        CSVWriter csvOutput=null;
        try {

            csvOutput = new CSVWriter(new FileWriter(fileName, true), '|');
            // use FileWriter constructor that specifies open for appending



            // if the file didn't already exist then we need to write out the header line

//
//            map.put("kycRequestId", customerData[0]);
//            map.put("CustomerID", customerData[1]);
//            map.put("Username", customerData[2]);
//            map.put("CustomerPhone", customerData[3]);
//            map.put("Email", customerData[4]);
//            map.put("AddressID", customerData[5]);
//            map.put("TimeSlot", customerData[6]);
//            map.put("Priority", customerData[7]);
//            map.put("AddressStreet1", customerData[8]);
//            map.put("AddressStreet2", customerData[9]);
//            map.put("City", customerData[10]);
//            map.put("State", customerData[11]);
//            map.put("Pincode", customerData[12]);
//            map.put("AddressPhone", customerData[13]);
//            map.put("VendorName", customerData[14]);
//            map.put("StageId", customerData[15]);
//            map.put("SubStageId", customerData[16]);
//            map.put("CreatedTimestamp", customerData[17]);

            String s[]={"kycRequestId","CustomerID","Username","CustomerPhone","Email","AddressID","TimeSlot","Priority","AddressStreet1","AddressStreet2","City","State","Pincode","AddressPhone","VendorName","StageId","SubStageId","CreatedTimestamp","Status"};

            csvOutput.writeNext(s);
            for (Map<String,String> map :list){

                String str[]={map.get("kycRequestId"),map.get("CustomerID"),map.get("Username"),map.get("CustomerPhone")
                        ,map.get("Email"),map.get("AddressID"),map.get("TimeSlot"),map.get("Priority")
                        ,map.get("AddressStreet1"),map.get("AddressStreet2"),map.get("City"),map.get("State")
                        ,map.get("Pincode"),map.get("AddressPhone"),map.get("VendorName"),map.get("StageId")
                        ,map.get("SubStageId"),map.get("CreatedTimestamp"),"Rejected"};
                csvOutput.writeNext(str);
             }



            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                csvOutput.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
/*

    private void writeToFile(String output, File fileName) throws FileNotFoundException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
            writer.close();}catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Set<String> collectHeaders(List<Map<String, String>> flatJson) {
        Set<String> headers = new TreeSet<String>();
        for (Map<String, String> map : flatJson) {
            headers.addAll(map.keySet());
        }
        return headers;
    }

    private String getCommaSeperatedRow(Set<String> headers, Map<String, String> map) {
        List<String> items = new ArrayList<String>();
        for (String header : headers) {
            String value = map.get(header) == null ? "" : map.get(header).replace(",", "");
            items.add(value);
        }
        return StringUtils.join(items, ",");
    }
*/


    public int getCircleCode(int pin1,int pin2,String state){
        int circode = 0;
        String circleCode = "0";
        try {
            if (state.equalsIgnoreCase("GUJARAT") || (pin1 >= 36 && pin1 <= 39)) {
                circleCode = "1";
            } else if (state.equalsIgnoreCase("KARNATAKA") || (pin1 >= 56 && pin1 <= 59)) {
                circleCode = "2";
            } else if ((state.equalsIgnoreCase("BIHAR")) || (state.equalsIgnoreCase("JHARKHAND")) || (pin1 >= 80 && pin1 <= 85) || pin1 == 92) {
                circleCode = "3";
            } else if (state.equalsIgnoreCase("DELHI") || (pin1 == 11 || pin2 == 201 || pin2 == 222 || pin2 == 121)) {
                circleCode = "4";
            } else if (state.equalsIgnoreCase("HARYANA") || (pin2 > 122 && pin1 >= 12 && pin1 <= 13)) {
                circleCode = "5";
            } else if (state.equalsIgnoreCase("HIMANCHAL PRADESH") || pin1 == 17) {
                circleCode = "6";
            } else if (state.equalsIgnoreCase("RAJASTHAN") || (pin1 >= 30 && pin1 <= 34)) {
                circleCode = "7";
            } else if (state.equalsIgnoreCase("MADHYA PRADESH") || state.equalsIgnoreCase("CHATTISGARH") || (pin1 >= 45 && pin1 <= 49)) {
                circleCode = "8";
            } else if (state.equalsIgnoreCase("ODISHA") || (pin1 >= 75 && pin1 <= 77)) {
                circleCode = "18";
            } else if (state.equalsIgnoreCase("ASSAM") || (pin1 >= 78 && pin1 <= 79)) {
                circleCode = "9";
            } else if (state.equalsIgnoreCase("MAHARASHTRA") || (pin1 >= 40 && pin1 <= 44)) {
                circleCode = "10";
            } else if (state.equalsIgnoreCase("PUNJAB") || (pin1 >= 14 && pin1 <= 16)) {
                circleCode = "11";
            } else if (state.equalsIgnoreCase("WEST BENGAL") || state.equalsIgnoreCase("SIKKIM") || state.startsWith("ANDAMAN") || (pin1 >= 70 && pin1 <= 74)) {
                circleCode = "12";
            }else if (state.equalsIgnoreCase("UTTARAKHAND") || (pin1 >= 24 && pin1 <= 28)) {
                circleCode = "14";
            } else if (state.equalsIgnoreCase("CHENNAI") || ( pin1 >= 60 && pin1 <= 61)) {
                circleCode = "17";
            } else if (state.equalsIgnoreCase("HYDERABAD") || ( pin1 >= 50 && pin1 <= 51)) {
                circleCode = "16";
            }  else if (state.equalsIgnoreCase("UTTAR PARDESH") || (pin2 > 201 && pin1 >= 20 && pin1 <= 23)) {
                circleCode = "13";
            } else {
                circleCode = "0";
            }

            circode = Integer.parseInt(circleCode);
        }catch (Exception e){
          e.printStackTrace();
        }

      return  circode;
    }


}
