package com.cronotesys.APIrest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cronoteSys.model.dao.ActivityDAO;
import com.cronoteSys.model.dao.LoginDAO;
import com.cronoteSys.model.vo.ActivityVO;
import com.cronoteSys.model.vo.LoginVO;
import com.cronoteSys.model.vo.UserVO;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
//    	ActivityVO atc = new ActivityDAO().find(1);
    	System.out.println("Aaaaa");
        return "Hello, Heroku!" /*+ atc.getTitle()*/;
    }
    
	@GET
    @Path("connection")
    @Produces(MediaType.APPLICATION_JSON)
    public String connection() {
		System.out.println("success");
    	return "SUCCESS";
    }
	
	@POST
	@Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public UserVO validade(LoginVO loginVO) {
		return new LoginDAO().verifiedUser(loginVO.getEmail(), loginVO.getPasswd());
	}
	
	@POST
	@Path("saveLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginVO addLogin(LoginVO loginVO) {
		System.out.println(loginVO.getEmail());
	  	return new LoginDAO().saveOrUpdate(loginVO);
	}
   
	@GET
    @Path("email_exists")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginVO emailExists(@QueryParam("email")String sEmail) {
		return new LoginDAO().loginExists(sEmail);
    }
}
