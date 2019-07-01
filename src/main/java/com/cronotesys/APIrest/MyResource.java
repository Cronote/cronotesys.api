package com.cronotesys.APIrest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cronoteSys.filter.ActivityFilter;
import com.cronoteSys.model.dao.ActivityDAO;
import com.cronoteSys.model.dao.CategoryDAO;
import com.cronoteSys.model.dao.ExecutionTimeDAO;
import com.cronoteSys.model.dao.LoginDAO;
import com.cronoteSys.model.dao.ProjectDAO;
import com.cronoteSys.model.dao.TeamDAO;
import com.cronoteSys.model.dao.UserDAO;
import com.cronoteSys.model.vo.ActivityVO;
import com.cronoteSys.model.vo.CategoryVO;
import com.cronoteSys.model.vo.ExecutionTimeVO;
import com.cronoteSys.model.vo.LoginVO;
import com.cronoteSys.model.vo.ProjectVO;
import com.cronoteSys.model.vo.TeamVO;
import com.cronoteSys.model.vo.UserVO;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIt() {
//    	ActivityVO atc = new ActivityDAO().find(1);
		return "Hello, Heroku!" /* + atc.getTitle() */;
	}

	@GET
	@Path("connection")
	@Produces(MediaType.APPLICATION_JSON)
	public String connection() {
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
		return new LoginDAO().saveOrUpdate(loginVO);
	}

	@POST
	@Path("saveUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserVO addUser(UserVO userVO) {
		return new UserDAO().saveOrUpdate(userVO);
	}

	@GET
	@Path("email_exists")
	@Produces(MediaType.APPLICATION_JSON)
	public Long emailExists(@QueryParam("email") String sEmail) {
		return new LoginDAO().loginExists(sEmail);
	}

	@POST
	@Path("saveActivity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ActivityVO saveActivity(ActivityVO activityVO) {
		return new ActivityDAO().saveOrUpdate(activityVO);
	}

	@DELETE
	@Path("deleteActivity")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteActivity(@QueryParam("id") int id) {
		ActivityDAO acDao = new ActivityDAO();
		acDao.delete(id);
		return true;
	}

	@GET
	@Path("getActivityList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ActivityVO> listByActivity(@QueryParam("filter") ActivityFilter filter) {
		List<ActivityVO> lst = new ActivityDAO().getFiltredList(filter);

		return lst;
	}

	@POST
	@Path("saveProject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProjectVO saveProject(ProjectVO projectVO) {
		return new ProjectDAO().saveOrUpdate(projectVO);
	}

	@DELETE
	@Path("deleteProject")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProject(int id) {
		new ProjectDAO().delete(id);
	}

	@GET
	@Path("getListProjectByUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ProjectVO> listProjectByActivity(@QueryParam("userid") int userId) {
		return new ProjectDAO().getList(userId);
	}

	@POST
	@Path("saveCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryVO saveCategory(CategoryVO categoryVO) {
		return new CategoryDAO().saveOrUpdate(categoryVO);
	}

	@POST
	@Path("saveTeam")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TeamVO saveTeam(TeamVO teamVO) {
		return new TeamDAO().saveOrUpdate(teamVO);
	}

	@GET
	@Path("getListTeamsByUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<TeamVO> listTeamByUser(@QueryParam("userId") int userId) {
		return new TeamDAO().listByUserOwnerOrMember(userId);
	}

	@DELETE
	@Path("deleteCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCategory(int id) {
		new CategoryDAO().delete(id);
	}

	@GET
	@Path("countByCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public int countByCategory(@QueryParam("categoryID") int categoryId) {
		return new ActivityDAO().countByCategory(categoryId);
	}

	@GET
	@Path("listAllCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategoryVO> listAllCategory() {
		return new CategoryDAO().getList();
	}

	@GET
	@Path("listCategoryByUser")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategoryVO> listAllCategory(@QueryParam("userID") int userId) {
		return new CategoryDAO().getList(userId);
	}

	@POST
	@Path("saveExecutionTime")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ExecutionTimeVO saveExecutionTime(ExecutionTimeVO executionTimeVO) {
		return new ExecutionTimeDAO().saveOrUpdate(executionTimeVO);
	}

	@GET
	@Path("executionInProgress")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ExecutionTimeVO executionInProgress(@QueryParam("activityID") int activityID) {
		return new ExecutionTimeDAO().executionInProgress(activityID);
	}

	@GET
	@Path("listExecutionTimeByActivity")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ExecutionTimeVO> listExecutionTimeByActivity(@QueryParam("activityID") int activityID) {
		return new ExecutionTimeDAO().listByActivity(activityID);
	}

	@POST
	@Path("passwordChange")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int passwordChange(String infos) {
		String[] infosA = infos.split(";");
		return new LoginDAO().changePassword(infosA[0], infosA[1]);
	}
}
