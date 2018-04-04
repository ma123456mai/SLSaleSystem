package org.slsale.dao.dataDictionary;

import java.util.List;

import org.slsale.pojo.DataDictionary;

public interface DataDictionaryMapper {
	/**
	 * 查询数据字典（证件类型，用户类型，......）（用typeCode来查询）
	 * */
	public List<DataDictionary> getDataDictionary(DataDictionary dataDictionary) throws Exception;
}
