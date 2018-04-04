package org.slsale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.slsale.common.Constant;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.User;
import org.slsale.service.function.FunctionService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController extends BaseController {
	private Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private UserService userService;

	@Resource
	private FunctionService functionService;

	@Resource
	private RedisAPI redisAPI;

	@RequestMapping("login")
	public String goLogin() {
		return "index";
	}

	@RequestMapping("/main.html")
	public String show(HttpSession session,Model model) {
		User user = this.getUser();
		List<Menu> mList = null;
		if (user != null) {
			model.addAttribute("user", user);
			/**
			 * key : menuList + roleId
			 * */
			if (!redisAPI.exist("menuList" + user.getRoleId())) {
				mList = getFunctionByUser(user.getRoleId());
				if(mList != null){
					JSONArray array = JSONArray.fromObject(mList);
					String json = array.toString();
					logger.debug("进入main中的json数据如下：" + json);
					model.addAttribute("mList", json);
					//保存数据去redis中
					redisAPI.set("menuList" + user.getRoleId(), json);
				}
			}else{
				String redisMenuList = redisAPI.get("menuList" + user.getRoleId());
				logger.debug(redisMenuList);
				if(redisMenuList != null && !"".equals(redisMenuList)){
					model.addAttribute("mList", redisMenuList);
				}
			}
		}
		session.setAttribute(Constant.SESSION_BASE_MODEL, model);
		return "main";
	}

	protected List<Menu> getFunctionByUser(Integer roleId) {
		List<Menu> list = new ArrayList<Menu>();
		Authority authority = new Authority();
		authority.setRoleId(roleId);
		try {
			List<Function> mList = functionService
					.getMainFunctionList(authority);
			if (mList != null) {
				for (Function function : mList) {
					Menu menu = new Menu();
					menu.setMainMenu(function);
					function.setRoleId(roleId);
					List<Function> subList = functionService
							.getSubFunctionList(function);
					if (subList != null) {
						menu.setSubMenus(subList);
					}
					list.add(menu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/login.html")
	@ResponseBody
	public Object login(HttpSession session, @RequestParam String user) {
		logger.debug("进入login方法");
		if (user == null || "".equals(user)) {
			return "failed";
		} else {
			// 数据类型转化
			JSONObject jso = JSONObject.fromObject(user);
			User u = (User) jso.toBean(jso, User.class);

			try {
				if (userService.loginCodeIsExists(u) == 0) {
					return "errorloginCode";
				} else {
					User _user = userService.getLoginUser(u);
					if (_user == null) {
						return "errorpassword";
					} else {
						// 将当前登陆成功用户保存在session中
						session.setAttribute(Constant.SESSION_USER, _user);
						// 修改最后登录时间
						User updateUser = new User();
						updateUser.setId(_user.getId());
						updateUser.setLastLoginTime(new Date());
						userService.modifyUser(updateUser);
						updateUser = null;
						return "success";
					}
				}
			} catch (Exception e) {

				return "failed";
			}
		}
	}
	
	@RequestMapping("/loginout.html")
	public String loginOut(HttpSession session){
		session.removeAttribute(Constant.SESSION_USER);
		session.invalidate();
		setUser(null);
		return "index";
	}
}
