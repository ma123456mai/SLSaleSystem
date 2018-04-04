package org.slsale.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constant;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PagesSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.dataDictionary.DataDictionaryService;
import org.slsale.service.role.RoleService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;

@Controller
public class UserController extends BaseController{

	private Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	@RequestMapping("/backend/modifyPwd.html")
	@ResponseBody
	public Object modfiyPwd(@RequestParam String userJson){
		logger.debug("进入修改密码的方法");
		User user = this.getUser();
		if(null == userJson || "".equals(userJson)){
			return "nodata";
		}else {
			JSONObject userObject = JSONObject.fromObject(userJson);
			User _user = (User)JSONObject.toBean(userObject,User.class);
			_user.setId(user.getId());
			try {
				if(user.getPassword().equals(_user.getPassword())){
					//保存密码时_user对象中保存的password是旧密码
					//而password2才是新密码
					_user.setPassword(_user.getPassword2());
					_user.setPassword2(null);
					userService.modifyUser(_user);
					user.setPassword(_user.getPassword());
					this.setUser(user);
					return "success";
				}else {
					return "oldpwdwrong";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "failed";
	}
	
	@RequestMapping("/backend/userlist.html")
	public String userList(HttpSession session,Model model,
							@RequestParam(value="s_loginCode",required=false) String s_loginCode,
							@RequestParam(value="s_referCode",required=false) String s_referCode,
							@RequestParam(value="s_roleId",required=false) String s_roleId,
							@RequestParam(value="s_isStart",required=false) String s_isStart,
							@RequestParam(value="currentpage",required=false) Integer currentpage){
		logger.debug("进入userList方法");
		Map<String, Object> baseModel = (Map<String, Object>) session.getAttribute(Constant.SESSION_BASE_MODEL);
		//获取roleList并传给前台显示
		List<Role> roleList = null;
		if (baseModel != null) {
			try {
				roleList = roleService.getRoleList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return "redirect:/";
		}
		//接收到的前台数据保存在user中
		User user = new User();
		if(null != s_loginCode){
			user.setLoginCode("%" + SQLTools.tarnfer(s_loginCode) + "%");
		}
		if(null != s_referCode){
			user.setReferCode("%" + SQLTools.tarnfer(s_referCode) + "%");
		}
		if(!StringUtils.isNullOrEmpty(s_roleId)){
			user.setRoleId(Integer.valueOf(s_roleId));
		}else{
			user.setRoleId(null);
		}
		if(!StringUtils.isNullOrEmpty(s_isStart)){
			user.setIsStart(Integer.valueOf(s_isStart));
		}else{
			user.setIsStart(null);
		}
		logger.debug(user);
		//处理分页
		PagesSupport page = new PagesSupport();
		try {
			page.setTotalCount(userService.count(user));
		} catch (Exception e) {
			e.printStackTrace();
			page.setTotalCount(0);
		}
		logger.debug("totalCount--------------------" + page.getTotalCount());
		if(page.getTotalCount() > 0){
			if(currentpage != null){
				page.setPage(currentpage);
			}else {
				page.setPage(1);
			}
			if(page.getPage() < 1){
				page.setPage(1);
			}
			if(page.getPage() > page.getPageCount()){
				page.setPage(page.getPageCount());
			}
			
			user.setStartpage((page.getPage() -1) * page.getPageSize());
			user.setPageSize(page.getPageSize());
			
			logger.debug("Startpage---------------" + user.getStartpage());
			logger.debug("PageSize----------------" + user.getPageSize());
			List<User> userList = null;
			try {
				userList = userService.getUserList(user);
			} catch (Exception e) {
				e.printStackTrace();
				userList = null;
			}
			logger.debug("userlist---------------" + userList.size());
			page.setItems(userList);
		}
		
		List<DataDictionary> cardTypeList = null;
		try {
			cardTypeList = null;
			DataDictionary dictionary = new DataDictionary();
			dictionary.setTypeCode("CARD_TYPE");
			cardTypeList = dataDictionaryService.getDataDictionary(dictionary);
			if(cardTypeList != null){
				model.addAttribute("cardTypeList", cardTypeList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		model.addAttribute("s_loginCode", s_loginCode);
		model.addAttribute("s_referCode", s_referCode);
		model.addAttribute("s_roleId", s_roleId);
		model.addAttribute("s_isStart", s_isStart);
		model.addAttribute("roleList", roleList);
		model.addAttribute("cardTypeList", cardTypeList);
		model.addAllAttributes(baseModel);
		
		return "/backend/userlist";
	}
	
	@RequestMapping(value="/backend/adduser.html",method=RequestMethod.POST)
	public String addUser(HttpSession session,@ModelAttribute("addUser") User addUser){
		/*
		 * roleId,userType,loginCode,userName,sex,cardType,idCard,birthday,
		 * country,mobile,email,postCode,bankName,bankAccount,accountHolder
		 * referCode,createTime,isStart,userAddress,idCardPicPath,bankPicPath
		 * 
		 * 没有referId，lastUpdateTime，lastLoginTime，
		 * password，password2
		 * */
		logger.debug("传过来的值======================================" + addUser.getLoginCode());
		if(null == session.getAttribute(Constant.SESSION_USER) ){
			return "redirect:/";
		}else{
			try {
				String idCard = addUser.getIdCard();
				String pwd = idCard.substring(idCard.length() - 6);
				
				addUser.setPassword(pwd);
				addUser.setPassword2(pwd);
				addUser.setCreateTime(new Date());
				addUser.setLastUpdateTime(new Date());
				addUser.setReferId(this.getUser().getId());
				addUser.setReferCode(this.getUser().getLoginCode());
				
				userService.addUser(addUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/backend/userlist.html";
	}
	
	@RequestMapping(value="/backend/loadUserTypeList.html",produces={"text/html;charset=utf-8"})
	@ResponseBody
	public Object loadUserTypeList(@RequestParam(value="s_role",required=false) String s_role){
		String json = "";
		try {
			DataDictionary dictionary = new DataDictionary();
			dictionary.setTypeCode("USER_TYPE");
			List<DataDictionary> userTypeList = dataDictionaryService.getDataDictionary(dictionary);
			JSONArray jo = JSONArray.fromObject(userTypeList);
			json = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value = "/backend/upload.html", produces = {"text/html;charset=UTF-8"})  
	@ResponseBody
    public Object upload(@RequestParam(value = "a_fileInputID", required = false) MultipartFile cardFile, 
    		             @RequestParam(value = "a_fileInputBank", required = false) MultipartFile bankFile, 
    		             @RequestParam(value = "m_fileInputID", required = false) MultipartFile mCardFile, 
    		             @RequestParam(value = "m_fileInputBank", required = false) MultipartFile mBankFile, 
    		             @RequestParam(value = "loginCode", required = false) String loginCode, 
    					 HttpServletRequest request,HttpSession session) {  
  
        logger.debug("开始....");
        //根据服务器的操作系统，自动获取物理路径，自动适应各个操作系统的路径
        String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");  
        logger.debug("hanlu path======== " + path);
        List<DataDictionary> list = null;
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setTypeCode("PERSONALFILE_SIZE");
        try {
			list = dataDictionaryService.getDataDictionary(dataDictionary);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        int filesize = 50000;
        if(null != list){
        	 if(list.size() == 1){
             	filesize = Integer.valueOf(list.get(0).getValueName());
             }
        }
       
        if(cardFile != null){
        	String oldFileName = cardFile.getOriginalFilename();//获取原文件名
            String prefix=FilenameUtils.getExtension(oldFileName);//取文件后缀
            logger.debug("hanlu bankFile prefix======== " + prefix);
            if(cardFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";

            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){
            	//给文件重命名：系统毫秒数+100W以内的随机数
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_IDcard.jpg";  
                logger.debug("hanlu new fileName======== " + cardFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	cardFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{
            	return "2";
            }
        }
        if(bankFile != null){
        	String oldFileName = bankFile.getOriginalFilename();
            logger.debug("hanlu bankFile oldFileName======== " + oldFileName);
            String prefix=FilenameUtils.getExtension(oldFileName);     
            if(bankFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_bank.jpg";  
                logger.debug("hanlu bankFile new fileName======== " + bankFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	bankFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{//上传图片格式不正确
            	return "2";
            }
        }
        if(mCardFile != null){
        	String oldFileName = mCardFile.getOriginalFilename();
            String prefix=FilenameUtils.getExtension(oldFileName);     
            if(mCardFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_IDcard.jpg";  
                logger.debug("hanlu new fileName======== " + mCardFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	mCardFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{
            	return "2";
            }
        }
        if(mBankFile != null){
        	String oldFileName = mBankFile.getOriginalFilename();
            logger.debug("hanlu bankFile oldFileName======== " + oldFileName);
            String prefix=FilenameUtils.getExtension(oldFileName);     
            if(mBankFile.getSize() >  filesize){//上传大小不得超过 50k
            	return "1";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_bank.jpg";  
                logger.debug("hanlu bankFile new fileName======== " + mBankFile.getName());
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	mBankFile.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                return url;  
            }else{
            	return "2";
            }
        }
        return null;
    }  
	
	@RequestMapping(value = "/backend/logincodeisexit.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String loginCodeIsExit(@RequestParam(value="loginCode",required=false) String loginCode,
								  @RequestParam(value="id",required=false) String id){
		logger.debug("hanlu loginCodeIsExit loginCode===================== "+loginCode);
		logger.debug("hanlu loginCodeIsExit id===================== "+id);
		String result = "failed";
		User _user = new User();
		_user.setLoginCode(loginCode);
		if(!id.equals("-1"))
			_user.setId(Integer.valueOf(id));
		try {
			if(userService.loginCodeIsExists(_user) == 0)
				result = "only";
			else 
				result = "repeat";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		return result;
	}
	@RequestMapping(value = "/backend/getuser.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getUser(@RequestParam(value="id",required=false) String id){
		String cjson = "";
		if(null == id || "".equals(id)){
			return "nodata";
		}else{
			try {
				User user = new User();
				user.setId(Integer.valueOf(id));
				user = userService.getUserById(user);
				//user对象里有日期，所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(user,jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
				return cjson;
		}
	}
	
	@RequestMapping(value = "/backend/modifyuser.html",method=RequestMethod.POST)
	public ModelAndView modifyUser(HttpSession session,@ModelAttribute("modifyUser") User modifyUser){
		if(session.getAttribute(Constant.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				modifyUser.setLastUpdateTime(new Date());
				userService.modifyUser(modifyUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/userlist.html");
		}
	}
	
	@RequestMapping(value = "/backend/delpic.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delPic(@RequestParam(value="picpath",required=false) String picpath,
						 @RequestParam(value="id",required=false) String id,
						HttpServletRequest request,HttpSession session){
		String result= "failed" ;
		if(picpath == null || picpath.equals("")){
			result = "success"; 
		}else{
			//picpath：传过来的网络路径，需要解析成物理路径
			String[] paths = picpath.split("/");
			String path = request.getSession().getServletContext().getRealPath(paths[2]+File.separator+paths[3]+File.separator);  
			File file = new File(path);
		    logger.debug("地址是=================" + path);
		    logger.debug("存在么=================" + file.exists());
		    if(file.exists())
		     if(!file.delete()){
		    	 if(id.equals("0")){//添加用户时，删除上传的图片
		    		 result = "success";
		    	 }else{//修改用户时，删除上传的图片
		    		 User _user = new User();
			    	 _user.setId(Integer.valueOf(id));
			    	 if(picpath.indexOf("_IDcard.jpg") != -1)
			    		 _user.setIdCardPicPath(picpath);
			    	 else if(picpath.indexOf("_bank.jpg") != -1)
			    		 _user.setBankPicPath(picpath);
			    	 try {
						if(userService.delUser(_user) > 0){
							logger.debug("hanlu modify----userService.delUserPic======== " );
							result = "success";
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return result;
					}
		    	 }
		    }
		}
		return result;
	}
	
	
	@RequestMapping(value = "/backend/deluser.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delUser(@RequestParam(value="delId",required=false) String delId,
						  @RequestParam(value="delIdCardPicPath",required=false) String delIdCardPicPath,			  
						  @RequestParam(value="delBankPicPath",required=false) String delBankPicPath,			  
						  @RequestParam(value="delUserType",required=false) String delUserType,			  
						  HttpServletRequest request,HttpSession session){
		
		String result= "false" ;
		User delUser = new User();
		delUser.setId(Integer.valueOf(delId));
		try {
			//若被删除的用户为：普通消费会员、VIP会员、加盟店  则不可被删除
			if(delUserType.equals("2") || delUserType.equals("3") || delUserType.equals("4")){
				result = "noallow";
				return result;
			}else{
				if(this.delPic(delIdCardPicPath,delId,request,session).equals("success") && this.delPic(delBankPicPath,delId,request,session).equals("success")){
					if(userService.delUser(delUser) > 0)
						result = "success";
					return result;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
