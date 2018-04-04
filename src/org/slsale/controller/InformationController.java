package org.slsale.controller;

import java.io.File;
import java.util.ArrayList;
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
import org.slsale.common.HtmlEcode;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PagesSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.Function;
import org.slsale.pojo.Information;
import org.slsale.pojo.RoleFunctions;
import org.slsale.pojo.UploadTemp;
import org.slsale.pojo.User;
import org.slsale.service.dataDictionary.DataDictionaryService;
import org.slsale.service.information.InformationService;
import org.slsale.service.uploadtemp.UploadTempService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InformationController extends BaseController {
	private Logger logger = Logger.getLogger(InformationController.class);
	@Resource
	private InformationService informationService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private UploadTempService uploadTempService;
	
	
	@RequestMapping(value="/informanage/portalInfoDetail.html", produces = {"text/html;charset=UTF-8"})
	public ModelAndView viewInfo(HttpSession session,@RequestParam Integer id,Model model){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			if(null == id || "".equals(id)){
				id = 0;
			}else{
				try {
					Information information = new Information();
					information.setId(id);
					information = informationService.getInformation(information);
					if(null != information && information.getTitle() != null){
						model.addAttribute("information", information);
					}
				} catch (Exception e) {
				}
			}
		}
		model.addAllAttributes(baseModel);
		return new ModelAndView("informanage/portalinfodetail");
	}
	
	
	@RequestMapping(value="/informanage/viewInfo.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object viewInfo(HttpSession session,@RequestParam Integer id){
		String result = "";
		if(null == id || "".equals(id)){
			result =  "nodata";
		}else{
			try {
				Information information = new Information();
				information.setId(id);
				information = informationService.getInformation(information);
				if(null != information && information.getTitle() != null){
					information.setTitle(HtmlEcode.HtmlDecode(information.getTitle()));
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					result =  JSONObject.fromObject(information,jsonConfig).toString();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				result =  "failed";
			}
		}
		return result;
	}
	@RequestMapping("/informanage/delInfo.html")
	@ResponseBody
	public Object delInfo( HttpServletRequest request,HttpSession session,@RequestParam Integer id){
		
		if(null == id || "".equals(id)){
			return "nodata";
		}else{
			try {
				Information information = new Information();
				information.setId(id);
				Information _information = new Information();
				_information = informationService.getInformation(information);
				if(null != _information){
					String path = request.getSession().getServletContext().getRealPath("/");  
					_information.setFilePath(_information.getFilePath().replace("/", File.separator+File.separator));
					File file = new File(path + _information.getFilePath());
					if(file.exists()){
						file.delete();
					}
					informationService.deleteInformation(information);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			return "success";
		}
		
	}
	
	
	@RequestMapping("/informanage/downloadcenter.html")
	public ModelAndView downloadInfoList(HttpSession session,Model model,@RequestParam(value="p",required=false)Integer p,@RequestParam(value="k",required=false)String k){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		List<Information> informationList = null;
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			Information information = new Information();
			information.setState(1);
			//pages 
			PagesSupport page = new PagesSupport();
			try{
				if(null == k || "".equals(k)){
					page.setTotalCount(informationService.count(information));
				}else{
					information.setTitle("%"+SQLTools.tarnfer(k)+"%");
					page.setTotalCount(informationService.count(information));
				}
			}catch (Exception e1) {
				page.setTotalCount(0);
			}
			if(page.getTotalCount() > 0){
				if(p != null)
					page.setPage(p);
				if(page.getPage() <= 0)
					page.setPage(1);
				if(page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				
				information.setStartpage((page.getPage() - 1) * page.getPageSize());
				information.setPageSize(page.getPageSize());

				try {
					
					if(null == k || "".equals(k)){
						informationList = informationService.getInformationList(information);
					}else{
						information.setTitle("%"+SQLTools.tarnfer(k)+"%");
						informationList = informationService.getInformationList(information);
					}
				}catch (Exception e) {
					e.printStackTrace();
					informationList = null;
					if(page == null){
						page = new PagesSupport();
						page.setItems(null);
					}
				}
				page.setItems(informationList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("k", k);
		}
		return new ModelAndView("informanage/downloadcenter");
	}
	
	@RequestMapping("/informanage/portalinfoList.html")
	public ModelAndView infoList(HttpSession session,Model model,@RequestParam(value="p",required=false)Integer p,@RequestParam(value="k",required=false)String k){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			download(session,model,p,null);
		}
		return new ModelAndView("informanage/portalinfolist");
	}
	
	@RequestMapping("/informanage/download.html")
	public ModelAndView download(HttpSession session,Model model,@RequestParam(value="p",required=false)Integer p,@RequestParam(value="k",required=false)String k){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		List<Information> informationList = null;
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			Information information = new Information();
			information.setState(1);
			//pages 
			PagesSupport page = new PagesSupport();
			try{
				if(null == k || "".equals(k)){
					page.setTotalCount(informationService.count(information));
				}else{
					information.setFileName("%"+SQLTools.tarnfer(k)+"%");
					page.setTotalCount(informationService.count(information));
				}
			}catch (Exception e1) {
				page.setTotalCount(0);
			}
			if(page.getTotalCount() > 0){
				if(p != null)
					page.setPage(p);
				if(page.getPage() <= 0)
					page.setPage(1);
				if(page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				
				
				information.setStartpage((page.getPage() - 1) * page.getPageSize());
				information.setPageSize(page.getPageSize());

				try {
					
					if(null == k || "".equals(k)){
						informationList = informationService.getInformationList(information);
					}else{
						information.setFileName("%"+SQLTools.tarnfer(k)+"%");
						informationList = informationService.getInformationList(information);
					}
				}catch (Exception e) {
					e.printStackTrace();
					informationList = null;
					if(page == null){
						page = new PagesSupport();
						page.setItems(null);
					}
				}
				page.setItems(informationList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("k", k);
		}
		return new ModelAndView("informanage/download");
	}
	
	@RequestMapping("/informanage/information.html")
	public ModelAndView information(HttpSession session,Model model,@RequestParam(value="p",required=false)Integer p){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		List<Information> informationList = null;
		List<DataDictionary> dicList = null;
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("INFO_TYPE");
		
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			Information information = new Information();
			//pages 
			PagesSupport page = new PagesSupport();
			try{
				dicList = dataDictionaryService.getDataDictionary(dataDictionary);
				page.setTotalCount(informationService.count(information));
			}catch (Exception e1) {
				page.setTotalCount(0);
			}
			logger.debug("+++++++++++++++totalcount++++++++++++++:" + page.getTotalCount());
			if(page.getTotalCount() > 0){
				if(p != null)
					page.setPage(p);
				if(page.getPage() <= 0)
					page.setPage(1);
				if(page.getPage() > page.getPageCount())
					page.setPage(page.getPageCount());
				
				
				information.setStartpage((page.getPage() - 1) * page.getPageSize());
				information.setPageSize(page.getPageSize());
				
				try {
					information.setState(null);
					informationList = informationService.getInformationList(information);
				}catch (Exception e) {
					e.printStackTrace();
					informationList = null;
					if(page == null){
						page = new PagesSupport();
						page.setItems(null);
					}
				}
				page.setItems(informationList);
			}else{
				page.setItems(null);
			}
//			logger.debug("+++++++++++++++++++++++++++++:" + afficheList.size());
			
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("dicList", dicList);
		}
		return new ModelAndView("informanage/information");
		
	}
	
	
	@RequestMapping("/informanage/delInfoFile.html")
	@ResponseBody
	public Object delInfoFile( HttpServletRequest request,HttpSession session,@RequestParam String filePath){
		
		if(null == filePath || "".equals(filePath)){
			return "nodata";
		}else{
			try {
				String path = request.getSession().getServletContext().getRealPath("/");  

				File file = new File(path + filePath);
				
				if(file.exists()){
					file.delete();
				}
				
				Information information = new Information();
				information.setTypeName(filePath);
				information.setFileName("");
				information.setFilePath("#");
				information.setFileSize(0d);
				information.setUploadTime(new Date());
				informationService.modifyInformationFileInfo(information);
				
				UploadTemp uploadTemp = new UploadTemp();
				filePath = filePath.replaceAll("/", File.separator+File.separator);
				uploadTemp.setUploadFilePath(filePath);
				uploadTempService.delete(uploadTemp);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
		
	}
	
	public static void main(String[] args) {
		String string = "/statics/infofiles/1394419869337_info.dll";
		String ssString = File.separator;
		System.out.println("111:" + ssString);
		string = string.replaceAll("/", File.separator+File.separator);
		System.out.println(string);
	}
	
	
	@RequestMapping(value = "/informanage/upload.html", produces = {"text/html;charset=UTF-8"})  
	@ResponseBody
    public Object upload(@RequestParam(value = "uploadInformationFile", required = false) MultipartFile uploadInformationFile, 
    		@RequestParam(value = "uploadInformationFile", required = false) MultipartFile uploadInformationFileM, 
    					 HttpServletRequest request,HttpSession session) {  
  
        String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"infofiles");  
        
        if(uploadInformationFile == null && uploadInformationFileM != null)
        	uploadInformationFile = uploadInformationFileM;
        
        if(uploadInformationFile != null){
        	String oldFileName = uploadInformationFile.getOriginalFilename();
            String prefix=FilenameUtils.getExtension(oldFileName);  
            List<DataDictionary> list = null;
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setTypeCode("INFOFILE_SIZE");
            try {
				list = dataDictionaryService.getDataDictionary(dataDictionary);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            int filesize = 500000000;
            if(null != list){
	            if(list.size() == 1){
	            	filesize = Integer.valueOf(list.get(0).getValueName());
	            }
            }
            if(uploadInformationFile.getSize() >  filesize){//上传大小不得超过 500M
            	return "1";
            }else{//上传图片格式不正确
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_info."+prefix;  
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	uploadInformationFile.transferTo(targetFile);  
                	//add file info to uploadtemp
                	User sessionUser =  ((User)session.getAttribute(Constant.SESSION_USER));
                	UploadTemp uploadTemp = new UploadTemp();
                	uploadTemp.setUploader(sessionUser.getLoginCode());
                	uploadTemp.setUploadType("info");
                	uploadTemp.setUploadFilePath(File.separator + "statics" + File.separator + "infofiles" + File.separator + fileName );
                	uploadTempService.add(uploadTemp);
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                String url = oldFileName + "[[[]]]" + request.getContextPath()+"/statics/infofiles/"+fileName + "size:"+uploadInformationFile.getSize();
                return url;  
            }
        }
        return null;
    }  
	
	
	
	@RequestMapping(value="/informanage/addInformation.html",method=RequestMethod.POST)
	public ModelAndView addInformation(@ModelAttribute("addInformation") Information information,HttpSession session){
		if(session.getAttribute(Constant.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				User sessionUser =  ((User)session.getAttribute(Constant.SESSION_USER));
				information.setPublisher(sessionUser.getLoginCode());
				information.setPublishTime(new Date(System.currentTimeMillis()));
				information.setState(1);
				information.setUploadTime(information.getPublishTime());
				logger.debug("=======information.getTitle());      ================" + information.getTitle());
				if(null != information.getTitle() && !information.getTitle().equals("")){
					logger.debug("======= addInformation HtmlEncode.htmlEncode(information.getTitle())================" + HtmlEcode.htmlEncode(information.getTitle()));
					information.setTitle(HtmlEcode.htmlEncode(information.getTitle()));
				}
				
				UploadTemp uploadTemp = new UploadTemp();
            	uploadTemp.setUploader(sessionUser.getLoginCode());
            	uploadTemp.setUploadType("info");
            	uploadTemp.setUploadFilePath(information.getFilePath().replaceAll("/", File.separator+File.separator));
            	uploadTempService.delete(uploadTemp);
				informationService.addInformation(information);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/informanage/information.html");
	}
	
	
	@RequestMapping(value="/informanage/modifyinformation.html",method=RequestMethod.POST)
	public ModelAndView modifyInformation(@ModelAttribute("modifyInformation") Information information,HttpSession session){
		if(session.getAttribute(Constant.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				User sessionUser =  ((User)session.getAttribute(Constant.SESSION_USER));
				information.setPublisher(sessionUser.getLoginCode());
				information.setPublishTime(new Date(System.currentTimeMillis()));
				//information.setState(1);
				information.setUploadTime(information.getPublishTime());
				if(null != information.getTitle() && !information.getTitle().equals("")){
					logger.debug("======= modifyInformation HtmlEncode.htmlEncode(information.getTitle())================" + HtmlEcode.htmlEncode(information.getTitle()));
					information.setTitle(HtmlEcode.htmlEncode(information.getTitle()));
				}
				informationService.modifyInformation(information);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/informanage/information.html");
	}
	
	@RequestMapping("/informanage/modifyInfoState.html")
	@ResponseBody
	public Object modifyRole(HttpSession session,@RequestParam String inforState){
		
		if(null == inforState || "".equals(inforState)){
			return "nodata";
		}else{
			JSONObject informationObject = JSONObject.fromObject(inforState);
			Information information =  (Information)JSONObject.toBean(informationObject, Information.class);
			information.setUploadTime(new Date());
			information.setPublisher(((User)session.getAttribute(Constant.SESSION_USER)).getLoginCode());
			try {
				informationService.modifyInformation(information);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			return "success";
		}
		
	}
	
}
