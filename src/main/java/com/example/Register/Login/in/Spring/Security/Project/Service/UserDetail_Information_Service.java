package com.example.Register.Login.in.Spring.Security.Project.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.UserDetail_Information;

public interface UserDetail_Information_Service {

	  // UserDetail
    
    public void saveDetailUsers(UserDetail_Information u);
    
    public UserDetail_Information findUserDetailInforById(long id);
    
    public UserDetail_Information findUserDetailByEmailDetail(String email);
}
