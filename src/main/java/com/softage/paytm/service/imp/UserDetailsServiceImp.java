package com.softage.paytm.service.imp;

import com.softage.paytm.dao.UserDao;
import com.softage.paytm.models.EmplogintableEntity;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;


    @Service
    public class UserDetailsServiceImp implements UserDetailsService {
        @Autowired
        private UserDao userDao;
        @Autowired
        private ApplicationContext appCtx;

        @Override
        @Transactional(readOnly = true)
        public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
            User securityuser = null;
            userDao = appCtx.getBean(UserDao.class);
            EmplogintableEntity user = userDao.getUserByEmpcode(userCode);
            if (user != null) {
                try {
                    String password = user.getEmpPassword();
                    boolean enabeled = user.getEmpStatus()==1?true:false;
                    boolean accountNonExpired = user.getEmpStatus()==1?true:false;
                    boolean credetialnonExpired = user.getEmpStatus()==1?true:false;
                    boolean accountNoLocked = user.getEmpStatus()==1?true:false;

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
