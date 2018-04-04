package org.slsale.service.authority;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.authority.AuthorityMapper;
import org.slsale.dao.function.FunctionMapper;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.springframework.stereotype.Service;
@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Resource
	private AuthorityMapper mapper;
	@Resource
	private FunctionMapper functionMapper;
	
	public List<Authority> getList(Authority authority) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getList(authority);
	}

	public int addAuthority(Authority authority) throws Exception {
		// TODO Auto-generated method stub
		return mapper.addAuthority(authority);
	}

	public int modifyAuthority(Authority authority) throws Exception {
		// TODO Auto-generated method stub
		return mapper.modifyAuthority(authority);
	}

	public int deleteAuthority(Authority authority) throws Exception {
		// TODO Auto-generated method stub
		return mapper.deleteAuthority(authority);
	}

	public boolean hl_delAddAuthority(Authority authority, String checkFuncList)
			throws Exception {
		// TODO Auto-generated method stub
		String[] funcList = null;
		mapper.deleteAuthority(authority);
		if(null != checkFuncList && !checkFuncList.equals("")){
			funcList = checkFuncList.split(",");
			for(int i = 0; i < funcList.length; i++){
				authority.setFunctionId(Integer.valueOf(funcList[i]));
				mapper.addAuthority(authority);
			}
		}
		return true;
	}

	public boolean hl_addAuthority(String[] ids,String createdBy) throws Exception {
		// TODO Auto-generated method stub
		Authority authority = new Authority();
		authority.setRoleId(Integer.parseInt(ids[0]));
		mapper.deleteAuthority(authority);
		String idsSqlString = "";
		for(int i=1;i<ids.length;i++){
			idsSqlString += Integer.parseInt(ids[i])+",";
		}
		if(idsSqlString != null && idsSqlString.contains(",")){
			idsSqlString = idsSqlString.substring(0,idsSqlString.lastIndexOf(","));
			
			List<Function> fList = functionMapper.getFunctionListByIn(idsSqlString);
			
			if(null != fList && fList.size() > 0){
				
				for(Function function : fList){
					authority.setFunctionId(function.getId());
					authority.setCreationTime(new Date());
					authority.setCreatedBy(createdBy);
					mapper.addAuthority(authority);
				}
				
			}
		}
		return true;
	}

	public Authority getAuthority(Authority authority) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAuthority(authority);
	}

}
