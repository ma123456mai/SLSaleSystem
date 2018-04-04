package org.slsale.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.slsale.common.Constant;
import org.slsale.common.HtmlEcode;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PagesSupport;
import org.slsale.pojo.Affiche;
import org.slsale.pojo.Information;
import org.slsale.pojo.User;
import org.slsale.service.affiche.AfficheService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AfficheController extends BaseController {
	private Logger logger = Logger.getLogger(AfficheController.class);
	
	@Resource
	private AfficheService afficheService;
	
	
	
	@RequestMapping("/informanage/portalafficheList.html")
	public ModelAndView afficheList(HttpSession session,Model model,@RequestParam(value="p",required=false)Integer p){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			affiche(session,model,p,true);
		}
		return new ModelAndView("informanage/portalaffichelist");
	}
	
	
	@RequestMapping(value="/informanage/portalAfficheDetail.html", produces = {"text/html;charset=UTF-8"})
	public ModelAndView viewAffiche(HttpSession session,@RequestParam Integer id,Model model){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			if(null == id || "".equals(id)){
				id = 0;
			}else{
				try {
					Affiche affiche = new Affiche();
					affiche.setId(id);
					affiche = afficheService.getAffiche(affiche);
					if(null != affiche && affiche.getCode() != null){
						model.addAttribute("affiche", affiche);
					}
				} catch (Exception e) {
				}
			}
		}
		model.addAllAttributes(baseModel);
		return new ModelAndView("informanage/portalaffichedetail");
	}
	@RequestMapping(value="/informanage/viewAffiche.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public Object viewAffiche(HttpSession session,@RequestParam Integer id){
		String result = "";
		if(null == id || "".equals(id)){
			result =  "nodata";
		}else{
			try {
				Affiche affiche = new Affiche();
				affiche.setId(id);
				affiche = afficheService.getAffiche(affiche);
				if(null != affiche && affiche.getCode() != null && affiche.getTitle() != null){
					affiche.setTitle(HtmlEcode.HtmlDecode(affiche.getTitle()));
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					result =  JSONObject.fromObject(affiche,jsonConfig).toString();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				result =  "failed";
			}
		}
		return result;
	}
	@RequestMapping("/informanage/delAffiche.html")
	@ResponseBody
	public Object delAffiche(HttpSession session,@RequestParam Integer id){
		
		if(null == id || "".equals(id)){
			return "nodata";
		}else{
			try {
				Affiche affiche = new Affiche();
				affiche.setId(id);
				afficheService.deleteAffiche(affiche);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "failed";
			}
			return "success";
		}
		
	}
	
	
	
	
	@RequestMapping(value="/informanage/addAffiche.html",method=RequestMethod.POST)
	public ModelAndView addAffiche(@ModelAttribute("addAffiche") Affiche affiche,HttpSession session){
		logger.debug("================addAffiche====================");
		
		if(session.getAttribute(Constant.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				User sessionUser =  ((User)session.getAttribute(Constant.SESSION_USER));
				affiche.setPublisher(sessionUser.getLoginCode());
				affiche.setPublishTime(new Date(System.currentTimeMillis()));
				if(null != affiche.getTitle() && !affiche.getTitle().equals("")){
					affiche.setTitle(HtmlEcode.htmlEncode(affiche.getTitle()));
				}
				afficheService.addAffiche(affiche);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/informanage/affiche.html");
	}
	@RequestMapping(value="/informanage/modifyAffiche.html",method=RequestMethod.POST)
	public ModelAndView modifyAffiche(@ModelAttribute("addAffiche") Affiche affiche,HttpSession session){
		if(session.getAttribute(Constant.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				User sessionUser =  ((User)session.getAttribute(Constant.SESSION_USER));
				affiche.setPublisher(sessionUser.getLoginCode());
				affiche.setPublishTime(new Date(System.currentTimeMillis()));
				if(null != affiche.getTitle() && !affiche.getTitle().equals("")){
					affiche.setTitle(HtmlEcode.htmlEncode(affiche.getTitle()));
				}
				afficheService.modifyAffiche(affiche);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/informanage/affiche.html");
	}
	
	
	@RequestMapping("/informanage/affiche.html")
	public ModelAndView affiche(HttpSession session,Model model,
			@RequestParam(value="p",required=false)Integer p,boolean isPortal){
		Map<String,Object> baseModel= (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODEL);
		List<Affiche> afficheList = null;
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			Affiche affiche = new Affiche();
			//pages 
			PagesSupport page = new PagesSupport();
			try{
				if(isPortal){
					page.setTotalCount(afficheService.portalCount());
				}else{
					page.setTotalCount(afficheService.count());
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
				
				affiche.setStartpage((page.getPage() - 1) * page.getPageSize());
				affiche.setPageSize(page.getPageSize());
				
				try {
					if(isPortal){
						afficheList = afficheService.getPortalAfficheList(affiche);
					}else{
						afficheList = afficheService.getAfficheList(affiche);
					}
					
				}catch (Exception e) {
					e.printStackTrace();
					afficheList = null;
					if(page == null){
						page = new PagesSupport();
						page.setItems(null);
					}
				}
				page.setItems(afficheList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
		}
		return new ModelAndView("informanage/affiche");
	}
	
}
