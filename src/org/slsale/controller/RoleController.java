package org.slsale.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.slsale.common.Constant;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.role.RoleService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RoleController extends BaseController {
	private Logger logger = Logger.getLogger(RoleController.class);
	
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;
	
	@RequestMapping("/backend/delRole.html")
	@ResponseBody
	public Object delRole(HttpSession session,@RequestParam String role){
		
		if(null == role || "".equals(role)){
			return "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(role);
			Role roleObjRole =  (Role)JSONObject.toBean(roleObject, Role.class);
			try {
				User u = new User();
				List <User> uList = null;
				u.setRoleId(roleObjRole.getId());
				uList = userService.getUserList(u);
				if(uList == null || uList.size() == 0){
					roleService.deleteRole(roleObjRole);
					return "success";
				}else{
					String flag = "";
					for(int i = 0; i < uList.size(); i++){
						flag += uList.get(i).getLoginCode();
						flag += ","; 
					}
					return flag;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			
		}
		
	}
	@RequestMapping("/backend/modifyRole.html")
	@ResponseBody
	public Object modifyRole(HttpSession session,@RequestParam String role){
		
		if(null == role || "".equals(role)){
			return "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(role);
			Role roleObjRole =  (Role)JSONObject.toBean(roleObject, Role.class);
			roleObjRole.setCreateDate(new Date());
			//roleObjRole.setIsStart(1);
			roleObjRole.setCreatedBy(this.getUser().getLoginCode());
			try {
				roleService.hl_modifyRole(roleObjRole);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			return "success";
		}
		
	}
	@RequestMapping("/backend/addRole.html")
	@ResponseBody
	public Object addRole(HttpSession session,@RequestParam String role){
		
		if(null == role || "".equals(role)){
			return "nodata";
		}else{
			JSONObject roleObject = JSONObject.fromObject(role);
			Role roleObjRole =  (Role)JSONObject.toBean(roleObject, Role.class);
			roleObjRole.setCreateDate(new Date());
			roleObjRole.setIsStart(1);
			roleObjRole.setCreatedBy(((User)session.getAttribute(Constant.SESSION_USER)).getLoginCode());
			try {
				if(roleService.getRoleR(roleObjRole) !=  null){
					return "rename";
				}else{
					roleService.addRole(roleObjRole);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			return "success";
		}
		
	}
	
	
	@RequestMapping("/backend/rolelist.html")
	public ModelAndView roleList(HttpSession session,Model model){
		
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			List<Role> roleList = null;
			Role role = new Role();
			try {
				roleList = roleService.getRoleList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				roleList = null;
			}
			model.addAllAttributes(baseModel);
			model.addAttribute(roleList);
			return new ModelAndView("/backend/rolelist");
		}
	}
}
