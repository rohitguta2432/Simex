package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.MobileAppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by SS0085 on 01-08-2017.
 */

@Repository
public class MobileAppDaoImp implements MobileAppDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSourceMobileApp){
        this.jdbcTemplate = new JdbcTemplate(dataSourceMobileApp);
       /* this.doucumentEntryProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("usp_DistEntry_V2");*/
        try {
            System.out.println("Sunny Sutaaaaaa >>>>>>>>>>>>>> "+dataSourceMobileApp.getConnection().getCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String test(String value) {
        System.out.println(" calling to database");
        String name =  jdbcTemplate.queryForObject("SELECT Username FROM simedDelhi26072017.paytm_mast where cust_uid='1000000001'", String.class);
        System.out.println(" ending   >>>>  "+name);

        return name;
    }
}
