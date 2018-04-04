package org.slsale.service.information;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.information.InformationMapper;
import org.slsale.pojo.Information;
import org.springframework.stereotype.Service;
/**
 * InformationServiceImpl
 * @author bdqn_hl
 * @date 2014-2-27
 */
@Service
public class InformationServiceImpl implements InformationService {

	@Resource
	private InformationMapper mapper;
	
	public List<Information> getList(Information information) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getList(information);
	}

	public List<Information> getInformationList(Information information)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.getInformationList(information);
	}

	public Information getInformation(Information information) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getInformation(information);
	}

	public int addInformation(Information information) throws Exception {
		// TODO Auto-generated method stub
		return mapper.addInformation(information);
	}

	public int modifyInformation(Information information) throws Exception {
		// TODO Auto-generated method stub
		return mapper.modifyInformation(information);
	}

	public int deleteInformation(Information information) throws Exception {
		// TODO Auto-generated method stub
		return mapper.deleteInformation(information);
	}

	public int count(Information information) throws Exception {
		// TODO Auto-generated method stub
		return mapper.count(information);
	}

	public int modifyInformationFileInfo(Information information)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.modifyInformationFileInfo(information);
	}
}
