package com.shanghaichuangshi.jiyiguan.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.jiyiguan.model.Guangfeng;
import com.shanghaichuangshi.util.DateUtil;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class GuangfengDao extends Dao {

    public int count(String guangfeng_ip) {
        JMap map = JMap.create();
        map.put(Guangfeng.GUANGFENG_IP, guangfeng_ip);
        map.put("start_time", DateUtil.getStartDateTime());
        map.put("end_time", DateUtil.getEndDateTime());
        SqlPara sqlPara = Db.getSqlPara("guangfeng.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Guangfeng> list(String guangfeng_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Guangfeng.GUANGFENG_NAME, guangfeng_name);
        map.put(Guangfeng.M, m);
        map.put(Guangfeng.N, n);
        SqlPara sqlPara = Db.getSqlPara("guangfeng.list", map);

        return new Guangfeng().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Guangfeng> resultList() {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("guangfeng.resultList", map);

        return new Guangfeng().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Guangfeng find(String guangfeng_id) {
        JMap map = JMap.create();
        map.put(Guangfeng.GUANGFENG_ID, guangfeng_id);
        SqlPara sqlPara = Db.getSqlPara("guangfeng.find", map);

        List<Guangfeng> guangfengList = new Guangfeng().find(sqlPara.getSql(), sqlPara.getPara());
        if (guangfengList.size() == 0) {
            return null;
        } else {
            return guangfengList.get(0);
        }
    }

    public Guangfeng save(Guangfeng guangfeng, String request_user_id) {
        guangfeng.setGuangfeng_id(Util.getRandomUUID());
        guangfeng.setSystem_create_user_id(request_user_id);
        guangfeng.setSystem_create_time(new Date());
        guangfeng.setSystem_update_user_id(request_user_id);
        guangfeng.setSystem_update_time(new Date());
        guangfeng.setSystem_status(true);

        guangfeng.save();

        return guangfeng;
    }

    public boolean update(Guangfeng guangfeng, String request_user_id) {
        guangfeng.remove(Guangfeng.SYSTEM_CREATE_USER_ID);
        guangfeng.remove(Guangfeng.SYSTEM_CREATE_TIME);
        guangfeng.setSystem_update_user_id(request_user_id);
        guangfeng.setSystem_update_time(new Date());
        guangfeng.remove(Guangfeng.SYSTEM_STATUS);

        return guangfeng.update();
    }

    public boolean delete(String guangfeng_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Guangfeng.GUANGFENG_ID, guangfeng_id);
        map.put(Guangfeng.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Guangfeng.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("guangfeng.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}