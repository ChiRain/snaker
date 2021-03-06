package com.qcloud.project.macaovehicle.web.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilesSuccessVO {

    public interface TypeInterface {

        public int getType();

        public String getTypeName();
    }

    private long                id;

    // 表单实例id
    private long                formInstanceId;

    // 申请人id
    private long                carOwnerId;

    // 车辆id
    private long                vehicleId;

    // 驾驶员id
    private long                driverId;

    // 创建时间
    private Date                createDate;

    private Map<String, Object> returnMap     = new HashMap<String, Object>();

    // 当前办理业务类型
    private List<TypeInterface> typeList      = new ArrayList<TypeInterface>();

    // 显示的按钮,可办理的业务类型
    private List<Integer>       availTypeList = new ArrayList<Integer>();

    public ProfilesSuccessVO() {

    }

    public ProfilesSuccessVO(long id, long carOwnerId, long vehicleId, long driverId, Date createDate) {

        this.id = id;
        this.carOwnerId = carOwnerId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.createDate = createDate;
    }

    public void setId(long id) {

        this.id = id;
    }

    public long getId() {

        return id;
    }

    public void setCarOwnerId(long carOwnerId) {

        this.carOwnerId = carOwnerId;
    }

    public long getCarOwnerId() {

        return carOwnerId;
    }

    public void setVehicleId(long vehicleId) {

        this.vehicleId = vehicleId;
    }

    public long getVehicleId() {

        return vehicleId;
    }

    public void setDriverId(long driverId) {

        this.driverId = driverId;
    }

    public long getDriverId() {

        return driverId;
    }

    public void setCreateDate(Date createDate) {

        this.createDate = createDate;
    }

    public Date getCreateDate() {

        return createDate;
    }

    public Map<String, Object> getReturnMap() {

        return returnMap;
    }

    public void setReturnMap(Map<String, Object> returnMap) {

        this.returnMap = returnMap;
    }

    public long getFormInstanceId() {

        return formInstanceId;
    }

    public void setFormInstanceId(long formInstanceId) {

        this.formInstanceId = formInstanceId;
    }

    public List<TypeInterface> getTypeList() {

        return typeList;
    }

    public void setTypeList(List<TypeInterface> typeList) {

        this.typeList = typeList;
    }

    public List<Integer> getAvailTypeList() {

        return availTypeList;
    }

    public void setAvailTypeList(List<Integer> availTypeList) {

        this.availTypeList = availTypeList;
    }
}
