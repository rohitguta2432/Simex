package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AcceptedEntryDao;
import com.softage.paytm.service.AcceptedEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0097 on 1/24/2017.
 */
@Service
public class AcceptedEntryServiceimpl implements AcceptedEntryService {
    @Autowired
    private AcceptedEntryDao acceptedEntryDao;


   /* @Override
    public String insertAcceptedEntryDetails(String setEntryBy, String setCusPOACode,  String setCusPoiCode, String setCusPoiNumber, int setCustomerId, String setDocStatus, String setFolder_name, int setPage_count, String setSim_no, String setImagePath, String setSRF, String setcPOA, String setcPOI, String OrigPoa, String OrigPoi, int origPoaPc, int origPoiPc, String photo, int PhotoPc, int poaPc, int poiPc, int SrfPc) {
        return acceptedEntryDao.saveAcceptedEntryData(setEntryBy,setCusPOACode,setCusPOACode,setCusPoiCode,setCusPoiNumber,setCustomerId,setDocStatus,setFolder_name,setPage_count,setSim_no,setImagePath,setSRF,setcPOA,setcPOI,OrigPoa,OrigPoi,origPoaPc,origPoiPc,photo,PhotoPc,poaPc,poiPc,SrfPc);
    }*/

    @Override
    public String insertAcceptedEntryDetails(String setEntryBy, String setCusPOACode, String setCusPoaNumber, String setCusPoiCode, String setCusPoiNumber, int setCustomerId, String setDocStatus, String setFolder_name, int setPage_count, String setSim_no, String setImagePath, String setSRF, String setcPOA, String setcPOI, String OrigPoa, String OrigPoi, int origPoaPc, int origPoiPc, String photo, int PhotoPc, int poaPc, int poiPc, int SrfPc) {
        return acceptedEntryDao.saveAcceptedEntryData(setEntryBy,setCusPOACode,setCusPOACode,setCusPoiCode,setCusPoiNumber,setCustomerId,setDocStatus,setFolder_name,setPage_count,setSim_no,setImagePath,setSRF,setcPOA,setcPOI,OrigPoa,OrigPoi,origPoaPc,origPoiPc,photo,PhotoPc,poaPc,poiPc,SrfPc);
    }
}
