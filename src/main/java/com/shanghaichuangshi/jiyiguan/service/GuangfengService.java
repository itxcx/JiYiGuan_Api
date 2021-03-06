package com.shanghaichuangshi.jiyiguan.service;

import com.shanghaichuangshi.jiyiguan.dao.GuangfengDao;
import com.shanghaichuangshi.jiyiguan.model.Guangfeng;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class GuangfengService extends Service {

    private final GuangfengDao guangfengDao = new GuangfengDao();

    public int count(Guangfeng guangfeng) {
        return guangfengDao.count(guangfeng.getGuangfeng_phone());
    }

    public List<Guangfeng> list(Guangfeng guangfeng, int m, int n) {
        return guangfengDao.list(guangfeng.getGuangfeng_name(), m, n);
    }

    public List<Guangfeng> resultList() {
        return guangfengDao.resultList();
    }

    public Guangfeng find(String guangfeng_id) {
        return guangfengDao.find(guangfeng_id);
    }

    public Guangfeng save(Guangfeng guangfeng, String request_user_id) {
        int count = guangfengDao.count(guangfeng.getGuangfeng_ip());

        if (count == 0) {
            return guangfengDao.save(guangfeng, request_user_id);
        } else {
            throw new RuntimeException("该IP地址已经投过票啦!");
        }
    }

    public boolean update(Guangfeng guangfeng, String request_user_id) {
        return guangfengDao.update(guangfeng, request_user_id);
    }

    public boolean delete(Guangfeng guangfeng, String request_user_id) {
        return guangfengDao.delete(guangfeng.getGuangfeng_id(), request_user_id);
    }

}