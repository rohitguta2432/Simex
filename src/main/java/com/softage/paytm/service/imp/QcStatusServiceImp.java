package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.dao.PostCallingDao;
import com.softage.paytm.dao.QcStatusDao;
import com.softage.paytm.models.*;
import com.softage.paytm.service.QcStatusService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by SS0090 on 7/21/2016.
 */
@Service
public class QcStatusServiceImp implements QcStatusService {
    @Autowired
    private QcStatusDao qcStatusDao;
  @Autowired
  private PostCallingDao postCallingDao;
    @Autowired
    private AgentPaytmDao agentPaytmDao;

    @Override
    public String saveQcStatus(String mobile_no, String status, String rejected_page, String remarks) {
        String result=qcStatusDao.qcStatusSave(mobile_no,status,rejected_page,remarks);

        return result;
    }

    @Override
    public String updateQcStatus(String mobile_no, String status, String rejected_page, String remarks) {
       String result=qcStatusDao.qcStatusUpdate(mobile_no,status,rejected_page,remarks);

        JSONObject jsonObject =qcStatusDao.qcGetCustomerDetails(mobile_no);
        String agentcode  =(String)jsonObject.get("agentcode");
        String mobilenumber="";

         PaytmagententryEntity paytmagententryEntity =agentPaytmDao.findByPrimaryKey(agentcode);
        if(paytmagententryEntity!=null){
            mobilenumber  =paytmagententryEntity.getAphone();
        }

        String text = "Dear Customer your form is rejected because your document was not clear so please provide your documents again to the same official who processed the forms earlier";
        String text1= "Dear Agent your kyc for this customer is rejected in Qc mobileNumber = "+mobile_no+" so please contact customer again for the documents";

        if ("done".equalsIgnoreCase(result)&&status.equalsIgnoreCase("3")) {
            ReceiverMastEntity receiverMastEntity = postCallingDao.getRecivedByCode(1);
            ProcessMastEntity processMastEntity = postCallingDao.getProcessByCode(4);
            SmsSendlogEntity smsSendlogEntity = new SmsSendlogEntity();
            smsSendlogEntity.setMobileNumber("8588998890");
            smsSendlogEntity.setReceiverId("");
            smsSendlogEntity.setSmsText(text);
            smsSendlogEntity.setSmsDelivered("N");
            smsSendlogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setImportDate(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setProcessMastByProcessCode(processMastEntity);
            smsSendlogEntity.setReceiverMastByReceiverCode(receiverMastEntity);
            postCallingDao.saveSmsSendEntity(smsSendlogEntity);

        }
        if ("done".equalsIgnoreCase(result)&&status.equalsIgnoreCase("3")) {
            ReceiverMastEntity receiverMastEntity = postCallingDao.getRecivedByCode(2);
            ProcessMastEntity processMastEntity = postCallingDao.getProcessByCode(5);
            SmsSendlogEntity smsSendlogEntity = new SmsSendlogEntity();
            smsSendlogEntity.setMobileNumber(mobilenumber);
            smsSendlogEntity.setReceiverId(agentcode);
            smsSendlogEntity.setSmsText(text1);
            smsSendlogEntity.setSmsDelivered("N");
            smsSendlogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setImportDate(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setProcessMastByProcessCode(processMastEntity);
            smsSendlogEntity.setReceiverMastByReceiverCode(receiverMastEntity);
            postCallingDao.saveSmsSendEntity(smsSendlogEntity);

        }
        return result;
    }

    @Override
    public JSONObject getMobileNumber(Integer circode,String empcode) {
        JSONObject result=qcStatusDao.qcGetMobileNumber(circode,empcode);
        return result;
    }

    @Override
    public JSONObject qcCustomerDetails(String mobileNumber) {
        JSONObject jsonObject=new JSONObject();
        jsonObject=qcStatusDao.qcGetCustomerDetails(mobileNumber);
        return jsonObject;
    }

    @Override
    public JSONObject downloadList(String mobileNumber, String todate, String fromdate) {
        return qcStatusDao.downloadList(mobileNumber,todate,fromdate);
    }

    @Override
    public AuditStatusEntity getAuditStatusEntity(int status) {
        return qcStatusDao.getAuditStatusEntity(status);
    }

    @Override
    public TblScan getScanTableEntity(int scanID) {
        return qcStatusDao.getScanTableEntity(scanID);
    }

    @Override
    public String saveCircleAuditEntity(CircleAuditEntity circleAuditEntity) {
        return qcStatusDao.saveCircleAuditEntity(circleAuditEntity);
    }
    @Override
    public String updateTblSacnEntity(TblScan tblScan) {
        return qcStatusDao.updateTblScanEntity(tblScan);
    }

    @Override
    public String SaveScanimages(TblScan scantbl) {
        String result=null;
        result= qcStatusDao.savetblscan(scantbl);
        return result;
    }

    @Override
    public String savetbldocdetails(TblcustDocDetails tblcustDocDetails) {
        return qcStatusDao.saveTblDocdetails(tblcustDocDetails);
    }

    @Override
    public TblScan getUserScanDetails(int cust_uid) {
        return qcStatusDao.getScanDetails(cust_uid);
    }


}
