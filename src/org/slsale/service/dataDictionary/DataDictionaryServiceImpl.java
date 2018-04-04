package org.slsale.service.dataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.dataDictionary.DataDictionaryMapper;
import org.slsale.pojo.DataDictionary;
import org.springframework.stereotype.Service;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	
	
	public List<DataDictionary> getDataDictionary(DataDictionary dataDictionary) throws Exception{
		return dataDictionaryMapper.getDataDictionary(dataDictionary);
	}

}
