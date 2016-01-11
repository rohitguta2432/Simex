/*
package com.softage.paytm.service.imp;

import com.softage.paytm.dao.UserDao;
import com.softage.paytm.models.EmplogintableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

*/
/**
 * Created by SS0085 on 10-01-2016.
 *//*



    @Service("userDetailsService")
    public class UserDetailsServiceImp implements UserDetailsService {
        @Autowired
        private UserDao userDao;

        @Override
        @Transactional(readOnly = true)
        public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
            User securityuser = null;
            EmplogintableEntity user = userDao.getUserByEmpcode(userCode);
            if (user != null) {
                try {
                    String password = user.getEmpPassword();
                    boolean enabeled = user.getEmpStatus();
                    boolean accountNonExpired = user.getEmpStatus();
                    boolean credetialnonExpired = user.getEmpStatus();
                    boolean accountNoLocked = user.getEmpStatus();

                    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                    authorities.add(new GrantedAuthority() {
                        @Override
                        public String getAuthority() {
                            return user.getRoles();
                        }
                    });
                    securityuser = new User(user.getEmpCode(), password, enabeled, accountNonExpired, credetialnonExpired, accountNoLocked, authorities);

                } catch (Exception e) {
                    e.printStackTrace();
                    ;
                }
            } else {
                throw new UsernameNotFoundException(" User Not Found");
            }
            return securityuser;
        }



}
*/
