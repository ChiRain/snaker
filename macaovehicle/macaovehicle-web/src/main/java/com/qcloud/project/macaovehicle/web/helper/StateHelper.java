package com.qcloud.project.macaovehicle.web.helper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.qcloud.project.macaovehicle.model.DriverVehicle;
import com.qcloud.project.macaovehicle.model.HistoryUserRecords;
import com.qcloud.project.macaovehicle.model.key.TypeEnum.ProgressType;
import com.qcloud.project.macaovehicle.model.query.DriverVehicleQuery;
import com.qcloud.project.macaovehicle.service.DriverVehicleService;
import com.qcloud.project.macaovehicle.service.HistoryUserRecordsService;
import com.qcloud.project.macaovehicle.web.vo.ProfilesSuccessVO.TypeInterface;

@Component
public class StateHelper {

    @Autowired
    private HistoryUserRecordsService historyUserRecordsService;

    @Autowired
    private DriverVehicleService      driverVehicleService;

    /**
     * 门户网入境车辆当前办理业务
     * @param vehicleId
     * @param typeList          当前办理业务类型
     * @param availTypeList     剩下可办理业务
     */
    public void handlerToBusiness(Long vehicleId, List<TypeInterface> typeList, List<Integer> availTypeList) {

        // ==========================筛选当前办理业务，按钮状态显示============================================
        List<HistoryUserRecords> hurList = historyUserRecordsService.listByVehicleId(vehicleId);
        // 显示的按钮,未办理的业务
        for (final ProgressType progressType : ProgressType.values()) {
            boolean doFlag = false;
            for (HistoryUserRecords historyUserRecords : hurList) {
                // 业务完成的不需要显示
                if (historyUserRecords.getFinishTime() != null) {
                    continue;
                }
                // 当前办理业务
                if (progressType.getKey() == historyUserRecords.getType()) {
                    typeList.add(new TypeInterface() {

                        @Override
                        public String getTypeName() {

                            return progressType.getName();
                        }

                        @Override
                        public int getType() {

                            return progressType.getKey();
                        }
                    });
                    doFlag = true;
                    break;
                }
            }
            // 未办理的业务
            if (!doFlag) {
                DriverVehicleQuery query = new DriverVehicleQuery();
                query.setVehicleId(vehicleId);
                query.setGroupByStr("driverId");
                List<DriverVehicle> driverVehicles = driverVehicleService.listByQuery(query);
                // 添加驾驶员：车辆绑定的驾驶员数量小于5,才显示按钮
                if (driverVehicles.size() >= 5 && progressType.getKey() == ProgressType.TJJSY.getKey()) {
                    continue;
                }
                availTypeList.add(progressType.getKey());
            }
        }
        // 注销车辆：如果对应存在续期申请、补办电子车卡、补办临时号牌、添加驾驶员申请，那么不显示该按钮
        if (availTypeList.contains(ProgressType.TJJSY.getKey()) || availTypeList.contains(ProgressType.BBDZCK.getKey()) || availTypeList.contains(ProgressType.BBLSHP.getKey()) || availTypeList.contains(ProgressType.XQSQ.getKey())) {
            if (availTypeList.contains(ProgressType.ZXCL.getKey())) {
                availTypeList.remove(ProgressType.ZXCL.getKey());
            }
        }
    }

    public boolean checkAvailType(Long vehicleId, ProgressType pt) {

        List<HistoryUserRecords> hurList = historyUserRecordsService.listByVehicleId(vehicleId);
        // 显示的按钮,未办理的业务
        List<Integer> availTypeList = new ArrayList<Integer>();
        for (final ProgressType progressType : ProgressType.values()) {
            boolean doFlag = false;
            for (HistoryUserRecords historyUserRecords : hurList) {
                // 业务完成的不需要显示
                if (historyUserRecords.getFinishTime() != null) {
                    continue;
                }
                // 当前办理业务
                if (progressType.getKey() == historyUserRecords.getType()) {
                    doFlag = true;
                    break;
                }
            }
            // 未办理的业务
            if (!doFlag) {
                DriverVehicleQuery query = new DriverVehicleQuery();
                query.setVehicleId(vehicleId);
                query.setGroupByStr("driverId");
                List<DriverVehicle> driverVehicles = driverVehicleService.listByQuery(query);
                // 添加驾驶员：车辆绑定的驾驶员数量小于5,才显示按钮
                if (driverVehicles.size() >= 5 && progressType.getKey() == ProgressType.TJJSY.getKey()) {
                    continue;
                }
                availTypeList.add(progressType.getKey());
            }
        }
        // 注销车辆：如果对应存在续期申请、补办电子车卡、补办临时号牌、添加驾驶员申请，那么不显示该按钮
        if (availTypeList.contains(ProgressType.TJJSY.getKey()) || availTypeList.contains(ProgressType.BBDZCK.getKey()) || availTypeList.contains(ProgressType.BBLSHP.getKey()) || availTypeList.contains(ProgressType.XQSQ.getKey())) {
            if (availTypeList.contains(ProgressType.ZXCL.getKey())) {
                availTypeList.remove((Object)ProgressType.ZXCL.getKey());
            }
        }
        return availTypeList.contains(pt.getKey());
    }
    
    public static void main(String[] args) {

        int i = 1;
        List<Integer> list = new ArrayList<Integer>();
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));
        System.out.println(list.remove(i));
    }
}