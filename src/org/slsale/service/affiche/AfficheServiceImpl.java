package org.slsale.service.affiche;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.affiche.AfficheMapper;
import org.slsale.pojo.Affiche;
import org.springframework.stereotype.Service;


@Service
public class AfficheServiceImpl implements AfficheService {
	@Resource
	private AfficheMapper mapper;
	public List<Affiche> getList(Affiche affiche) throws Exception {
		return mapper.getList(affiche);
	}

	public Affiche getAffiche(Affiche affiche) throws Exception {
		return mapper.getAffiche(affiche);
	}

	public int addAffiche(Affiche affiche) throws Exception {
		return mapper.addAffiche(affiche);
	}

	public int modifyAffiche(Affiche affiche) throws Exception {
		return mapper.modifyAffiche(affiche);
	}

	public int deleteAffiche(Affiche affiche) throws Exception {
		return mapper.deleteAffiche(affiche);
	}

	public int count() throws Exception {
		// TODO Auto-generated method stub
		return mapper.count();
	}

	public List<Affiche> getAfficheList(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAfficheList(affiche);
	}

	public int portalCount() throws Exception {
		// TODO Auto-generated method stub
		return mapper.portalCount();
	}

	public List<Affiche> getPortalAfficheList(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getPortalAfficheList(affiche);
	}

}
