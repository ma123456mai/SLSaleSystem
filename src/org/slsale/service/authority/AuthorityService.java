package org.slsale.service.authority;

import java.util.List;

import org.slsale.pojo.Authority;

public interface AuthorityService {
	/**
	 * getList
	 * @return
	 */
	public List<Authority> getList(Authority authority) throws Exception;
	/**
	 * getAuthority
	 * @param authority
	 * @return
	 * @throws Exception
	 */
	public Authority getAuthority(Authority authority) throws Exception;
	/**
	 * addPremission
	 * @param premission
	 * @return
	 */
	public int addAuthority(Authority authority) throws Exception;
	/**
	 * hl_addAuthority
	 * @param ids
	 * @param createdBy
	 * @return
	 * @throws Exception
	 */
	public boolean hl_addAuthority(String[] ids,String createdBy) throws Exception;
	/**
	 * modifyPremission
	 * @param premission
	 * @return
	 */
	public int modifyAuthority(Authority authority) throws Exception;
	/**
	 * deletePremission
	 * @param premission
	 * @return
	 */
	public int deleteAuthority(Authority authority) throws Exception;
	/**
	 * hl_delAddAuthority
	 * @param authority
	 * @return
	 * @throws Exception
	 */
	public boolean hl_delAddAuthority(Authority authority,String checkFuncList) throws Exception;
}
