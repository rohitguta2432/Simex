
package com.softage.paytm.service.imp;








      import java.util.ArrayList;
      import java.util.Collection;
      import java.util.List;

        import com.softage.paytm.dao.UserDao;
        import com.softage.paytm.models.EmplogintableEntity;
        import com.softage.paytm.service.UserService;
      import org.json.simple.JSONObject;
      import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;



@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public EmplogintableEntity getUserByEmpcode(String empCode) {
        return userDao.getUserByEmpcode(empCode);
    }
    public JSONObject getEmpFtpDetails(int circleCode){
        return userDao.getEmpFtpDetailsDao(circleCode);
    }
}



/*



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









/*

public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String empcode)
            throws UsernameNotFoundException {
     //   User user = userService.findBySso(ssoId);
       EmplogintableEntity user= userDao.getUserByEmpcode(empcode);
        System.out.println("User : "+user);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmpCode(), user.getEmpPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(EmplogintableEntity user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getEmpStatus()));

        System.out.print("authorities :"+authorities);
        return authorities;
    }

}
*/






















/**
 * Created by SS0085 on 09-01-2016.
 */






