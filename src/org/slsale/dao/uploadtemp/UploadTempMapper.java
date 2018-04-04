package org.slsale.dao.uploadtemp;

import java.util.List;

import org.slsale.pojo.UploadTemp;

/**
 * UploadTempMapper
 * @author bdqn_hl
 * @date 2014-3-7
 */
public interface UploadTempMapper {
	/**
	 * getList
	 * @param uploadTemp
	 * @return
	 * @throws Exception
	 */
	public List<UploadTemp> getList(UploadTemp uploadTemp) throws Exception;
	/**
	 * add
	 * @param uploadTemp
	 * @return
	 * @throws Exception
	 */
	public int add(UploadTemp uploadTemp) throws Exception;
	/**
	 * delete
	 * @param uploadTemp
	 * @return
	 * @throws Exception
	 */
	public int delete(UploadTemp uploadTemp) throws Exception;
}
