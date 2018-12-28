package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.OrganizationDao;
import com.xinshai.xinshai.model.Organization;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationServices {

    @Resource
    private OrganizationDao organizationDao;

    public List<Map<String,Object>> getAllOrganization() {
        return organizationDao.getAllOrganization();
    }

    public void createSon(Organization or) {
        organizationDao.createSon(or);
    }

    public void editName(String id, String name) {
        organizationDao.editName(id,name);
    }

    public void removeNode(String id) {
        organizationDao.removeNode(id);
    }


    public List<Organization> getOrganization() {
        return organizationDao.getOrganization();
    }

    public List<Organization> getDeleted() {
        return organizationDao.getDeleted();
    }

    public void reduction(String id) {
        organizationDao.reduction(id);
    }

    public Organization getHospitalLevel(String hospital) {
        return organizationDao.getHospitalLevel(hospital);
    }

    public List<Map<String,Object>> getByOwn(String organizationId) {
        return organizationDao.getByOwn(organizationId);
    }
}
