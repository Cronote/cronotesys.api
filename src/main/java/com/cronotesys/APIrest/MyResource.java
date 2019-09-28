package com.cronotesys.APIrest;

import java.util.ArrayList;
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
import com.cronoteSys.model.bo.TeamBO;
import com.cronoteSys.model.dao.ActivityDAO;
import com.cronoteSys.model.dao.CategoryDAO;
import com.cronoteSys.model.dao.ExecutionTimeDAO;
import com.cronoteSys.model.dao.LoginDAO;
import com.cronoteSys.model.dao.ProjectDAO;
import com.cronoteSys.model.dao.TeamDAO;
import com.cronoteSys.model.dao.TeamUserDAO;
import com.cronoteSys.model.dao.UserDAO;
import com.cronoteSys.model.vo.ActivityVO;
import com.cronoteSys.model.vo.CategoryVO;
import com.cronoteSys.model.vo.EmailVO;
import com.cronoteSys.model.vo.ExecutionTimeVO;
import com.cronoteSys.model.vo.LoginVO;
import com.cronoteSys.model.vo.ProjectVO;
import com.cronoteSys.model.vo.TeamVO;
import com.cronoteSys.model.vo.UserVO;
import com.cronoteSys.model.vo.view.SimpleUser;
import com.cronoteSys.util.GsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

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
		return "Hello, Heroku!";
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
	public String validade(LoginVO loginVO) {
		UserVO user = new LoginDAO().verifiedUser(loginVO.getEmail(), loginVO.getPasswd());
		return GsonUtil.getGsonWithJavaTime().toJson(user);
	}

	@POST
	@Path("saveLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addLogin(LoginVO loginVO) {
		LoginVO login = new LoginDAO().saveOrUpdate(loginVO);
		return GsonUtil.getGsonWithJavaTime().toJson(login);

	}

	@POST
	@Path("saveUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser(UserVO userVO) {
		UserVO user = new UserDAO().saveOrUpdate(userVO);
		return GsonUtil.getGsonWithJavaTime().toJson(user);
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
	public String saveActivity(ActivityVO activityVO) {
		ActivityVO act = new ActivityDAO().saveOrUpdate(activityVO);
		return GsonUtil.getGsonWithJavaTime().toJson(act);
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
	public String listByActivity(@QueryParam("filter") ActivityFilter filter) {
		List<ActivityVO> lst = new ActivityDAO().getFiltredList(filter);
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@POST
	@Path("saveProject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveProject(ProjectVO projectVO) {
		ProjectVO project = new ProjectDAO().saveOrUpdate(projectVO);
		return GsonUtil.getGsonWithJavaTime().toJson(project);
	}

	@DELETE
	@Path("deleteProject")
	public void deleteProject(@QueryParam("id") int id) {
		new ProjectDAO().delete(id);
	}

	@GET
	@Path("getListProjectByUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String listProjectByActivity(@QueryParam("userid") int userId) {
		List<TeamVO> lstTeams = new TeamDAO().listByUserOwnerOrMember(userId);
		String teamIds = TeamVO.getIdsAsStringFromList(lstTeams);
		List<ProjectVO> lst = null;
		if (!teamIds.equals("")) {
			lst = new ProjectDAO().getList(userId, teamIds);
		} else {
			lst = new ProjectDAO().getList(userId);
		}
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@POST
	@Path("saveCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveCategory(CategoryVO categoryVO) {
		CategoryVO cat = new CategoryDAO().saveOrUpdate(categoryVO);
		String json = GsonUtil.getGsonWithJavaTime().toJson(cat);
		return json;
	}

	@POST
	@Path("saveTeam")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveTeam(TeamVO teamVO) {
		TeamVO team = new TeamDAO().saveOrUpdate(teamVO);
		String json = GsonUtil.getGsonWithJavaTime().toJson(team);
		return json;
	}

	@DELETE
	@Path("deleteTeam")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteTeam(@QueryParam("id") long id) {
		new TeamDAO().delete(id);
		return true;
	}

	@GET
	@Path("getListTeamsByUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String listTeamByUser(@QueryParam("userId") int userId) throws JsonProcessingException {
		List<TeamVO> lst = new TeamDAO().listByUserOwnerOrMember(userId);
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@GET
	@Path("test")
	public String testUser() {
		List<UserVO> lst = new UserDAO().listAll();
		return GsonUtil.getGsonWithJavaTime().toJson(lst.get(2));

	}

	@GET
	@Path("listAllTeam")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String listAllTeam() {
		List<TeamVO> lst = new TeamDAO().listAll();
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@GET
	@Path("listByNameOrEmail")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String listSimpleUserByNameOrEmail(@QueryParam("search") String search, @QueryParam("not") String filter) {

		List<UserVO> lst = new UserDAO().findByNameOrEmail(search, filter);
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@GET
	@Path("listLoggedUsers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String listLoggedUsers(@QueryParam("idsIn") String idsIn, @QueryParam("not") String idsOut) {

		List<UserVO> lst = new UserDAO().listLoggedUsers(idsIn, idsOut);
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@DELETE
	@Path("deleteCategory")
	public void deleteCategory(int id) {
		new CategoryDAO().delete(id);
	}

	@GET
	@Path("countByCategory")
	public String countByCategory(@QueryParam("categoryID") int categoryId) {
		return new ActivityDAO().countByCategory(categoryId).toString();
	}

	@GET
	@Path("listAllCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public String listAllCategory() {
		List<CategoryVO> lst = new CategoryDAO().getList();
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@GET
	@Path("listCategoryByUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String listAllCategory(@QueryParam("userID") int userId) {
		List<CategoryVO> lst = new CategoryDAO().getList(userId);
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;

	}

	@POST
	@Path("saveExecutionTime")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveExecutionTime(ExecutionTimeVO executionTimeVO) {
		ExecutionTimeVO exec = new ExecutionTimeDAO().saveOrUpdate(executionTimeVO);
		String json = GsonUtil.getGsonWithJavaTime().toJson(exec);
		return json;
	}

	@GET
	@Path("executionInProgress")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String executionInProgress(@QueryParam("activityID") int activityID) {
		ExecutionTimeVO exec = new ExecutionTimeDAO().executionInProgress(activityID);
		String json = GsonUtil.getGsonWithJavaTime().toJson(exec);
		return json;
	}

	@GET
	@Path("listExecutionTimeByActivity")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String listExecutionTimeByActivity(@QueryParam("activityID") int activityID) {
		List<ExecutionTimeVO> lst = new ExecutionTimeDAO().listByActivity(activityID);
		String json = GsonUtil.getGsonWithJavaTime().toJson(lst);
		return json;
	}

	@POST
	@Path("passwordChange")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int passwordChange(String infos) {
		String[] infosA = infos.split(";");
		return new LoginDAO().changePassword(infosA[0], infosA[1]);
	}

	@GET
	@Path("send_email")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String sendEmails(@QueryParam("receivers") EmailVO emailVO) {
		if (emailVO.getMessage().contains("team")) {
			String string = "";
			String[] receivers = emailVO.getReceiver();
			for (int i = 0; i < receivers.length; i++) {
				if (receivers[i] == null)
					continue;
				String[] receiverSplited = receivers[i].split(";");
				emailVO.setMessage(new MessageUtil().generateMessage("team", receiverSplited[1], receiverSplited[2],
						receiverSplited[3]));
				String[] receiver = { receiverSplited[0] };
				emailVO.setReceiver(receiver);
				string = GsonUtil.getGsonWithJavaTime().toJson(new EmailUtil().genericEmail(emailVO));
			}
			return string;
		}
		String string = GsonUtil.getGsonWithJavaTime().toJson(new EmailUtil().genericEmail(emailVO));
		return string;
	}

	@GET
	@Path("countProjectByTeam")
	@Consumes(MediaType.APPLICATION_JSON)
	public Integer countProjectByTeam(@QueryParam("teamId") long teamId) {
		return new ProjectDAO().countByTeam(teamId);
	}

	@GET
	@Path("teamAccepted")
	@Consumes(MediaType.APPLICATION_JSON)
	public String teamAccepted(@QueryParam("member") int member, @QueryParam("team") int team) {
		//TODO: recuperar o team user, verificar expiração e depois aceitar ou n
		boolean bInvitedAccepted = new TeamDAO().inviteAccepted2(member, team);
		if (bInvitedAccepted) {
			String teamName = new TeamBO().getTeamName(team);
			return "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<meta charset=\"utf-8\">\r\n" + "<head>\r\n"
					+ "	<title>Convite de Time</title>\r\n" + "</head>\r\n"
					+ "<body style=\"background-color: #2F3137; padding: 20px ;\">\r\n"
					+ "	<div style=\"background-color: #35393F; height: 500px; width: 30%; border-radius: 40px;padding: 5px; margin: 0 auto; align-items: center;\">\r\n"
					+ "		<div style=\"color: white; margin-left: 10px; text-align: justify-all;font-size: 20px\">\r\n"
					+ "			<img src=\"https://instagram.fcpq2-1.fna.fbcdn.net/vp/e8dc300af2b279a19c6098b9b1e53832/5DE2331B/t51.2885-15/e35/46570500_296239597698930_2847510385969770577_n.jpg?_nc_ht=instagram.fcpq2-1.fna.fbcdn.net\" style=\" width: 30% ;height:30%;margin: 0 200px; \">	<br>\r\n"
					+ "			<h1 style=\"color: 	#32CD32;\">BEM VINDO AO TIME: " + teamName + "</h1><br>	\r\n"
					+ "			Você conseguiu entrar no time " + teamName + ", bem vindo!<br>\r\n"
					+ "			<div style=\"padding: 40px;\">\r\n"
					+ "				Agora você pode participar dos projetos em time. Aproveite todas as funções que o sistema tem a oferecer!\r\n"
					+ "			</div>\r\n" + "		</div>\r\n" + "	</div>\r\n" + "</body>\r\n" + "</html>";
		} else {
			return "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"utf-8\">\r\n"
					+ "	<title>Convite de Time</title>\r\n" + "</head>\r\n"
					+ "<body style=\"background-color: #2F3137; padding: 20px ;\">\r\n"
					+ "	<div style=\"background-color: #35393F; height: 500px; width: 30%; border-radius: 40px;padding: 5px; margin: 0 auto; align-items: center;\">\r\n"
					+ "		<div style=\"color: white; margin-left: 10px; text-align: justify-all;font-size: 20px\">\r\n"
					+ "			<img src=\"https://instagram.fcpq2-1.fna.fbcdn.net/vp/e8dc300af2b279a19c6098b9b1e53832/5DE2331B/t51.2885-15/e35/46570500_296239597698930_2847510385969770577_n.jpg?_nc_ht=instagram.fcpq2-1.fna.fbcdn.net\" style=\" width: 30% ;height:30%;margin: 0 200px; \">	<br>\r\n"
					+ "			<h1 style=\"color: 	#CC0000;\">Erro!</h1><br>	\r\n"
					+ "			Não foi possivel entrar em um time, por favor tente mais tarde<br>\r\n"
					+ "		</div>\r\n" + "	</div>\r\n" + "</body>\r\n" + "</html>";
		}

	}
}
