package com.softage.paytm.dao;

/**
 * Created by SS0097 on 1/24/2017.
 */
public interface AcceptedEntryDao {

    public String saveAcceptedEntryData(String setEntryBy, String setCusPOACode, String setCusPoaNumber, String setCusPoiCode, String setCusPoiNumber,
                                        int setCustomerId, String setDocStatus, String setFolder_name, int setPage_count, String setSim_no, String setImagePath,
                                        String setSRF, String setcPOA, String setcPOI, String OrigPoa, String OrigPoi, int origPoaPc, int origPoiPc, String photo, int PhotoPc, int poaPc, int poiPc, int SrfPc
    );
}
