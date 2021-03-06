package com.qcloud.project.macaovehicle.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.qcloud.component.autoid.AutoIdGenerator;
import com.qcloud.pirates.data.Page;
import com.qcloud.pirates.util.AssertUtil;
import com.qcloud.project.macaovehicle.dao.DriverDao;
import com.qcloud.project.macaovehicle.dao.DriverVehicleDao;
import com.qcloud.project.macaovehicle.model.Driver;
import com.qcloud.project.macaovehicle.model.DriverVehicle;
import com.qcloud.project.macaovehicle.service.DriverVehicleService;
import com.qcloud.project.macaovehicle.model.key.TypeEnum.DriverState;
import com.qcloud.project.macaovehicle.model.key.TypeEnum.ProgressType;
import com.qcloud.project.macaovehicle.model.query.DriverVehicleQuery;

@Service
public class DriverVehicleServiceImpl implements DriverVehicleService {

    @Autowired
    private DriverVehicleDao    driverVehicleDao;

    @Autowired
    private AutoIdGenerator     autoIdGenerator;

    @Autowired
    private DriverDao           driverDao;

    private static final String ID_KEY = "macaovehicle_driver_vehicle";

    @Override
    public boolean add(DriverVehicle driverVehicle) {

        long id = autoIdGenerator.get(ID_KEY);
        Driver driver = driverDao.get(driverVehicle.getDriverId());
        AssertUtil.assertNotNull(driver, "驾驶员不存在." + driverVehicle.getDriverId());
        if (driver.getState() != DriverState.PASS.getKey()) {
            driver.setState(DriverState.APPLYING.getKey());
            driverDao.update(driver);
        }
        driverVehicle.setId(id);
        return driverVehicleDao.add(driverVehicle);
    }

    @Override
    public DriverVehicle get(Long id) {

        return driverVehicleDao.get(id);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {

        DriverVehicle driverVehicle = driverVehicleDao.get(id);
        List<DriverVehicle> list = driverVehicleDao.getListByDriverId(driverVehicle.getDriverId(), ProgressType.APPLY);
        if (list.size() <= 1) {
            Driver driver = driverDao.get(driverVehicle.getDriverId());
            driver.setState(DriverState.NONAPPLY.getKey());
            driverDao.update(driver);
        }
        return driverVehicleDao.delete(id);
    }

    @Override
    public boolean update(DriverVehicle driverVehicle) {

        return driverVehicleDao.update(driverVehicle);
    }

    @Override
    public Page<DriverVehicle> page(DriverVehicleQuery query, int start, int count) {

        return driverVehicleDao.page(query, start, count);
    }

    public List<DriverVehicle> listAll() {

        return driverVehicleDao.listAll();
    }

    @Override
    public List<DriverVehicle> getListByFormInstCode(String formInstCode) {

        return driverVehicleDao.getListByFormInstCode(formInstCode);
    }

    @Override
    public List<DriverVehicle> listByQuery(DriverVehicleQuery query) {

        return driverVehicleDao.listByQuery(query);
    }

    @Override
    public List<DriverVehicle> getListByDriverId(Long driverId, ProgressType progressType) {

        return driverVehicleDao.getListByDriverId(driverId, progressType);
    }

    @Override
    public List<DriverVehicle> getListByFormInstanceId(Long formInstanceId) {

        return driverVehicleDao.getListByFormInstanceId(formInstanceId);
    }

    @Override
    public List<DriverVehicle> getListByVehicleId(Long vehicleId, ProgressType progressType) {

        return driverVehicleDao.getListByVehicleId(vehicleId, progressType);
    }

    @Override
    public List<DriverVehicle> getListByDriverIc(String driverCardId, ProgressType progressType) {

        return driverVehicleDao.getListByDriverIc(driverCardId, progressType);
    }

    @Override
    public int countAllVehicle(Long carOwnerId) {

        return driverVehicleDao.countAllVehicle(carOwnerId);
    }
}
