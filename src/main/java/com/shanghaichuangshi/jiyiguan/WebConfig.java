package com.shanghaichuangshi.jiyiguan;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.shanghaichuangshi.constant.Jdbc;
import com.shanghaichuangshi.controller.*;
import com.shanghaichuangshi.interceptor.GlobalActionInterceptor;
import com.shanghaichuangshi.jiyiguan.controller.FuteController;
import com.shanghaichuangshi.jiyiguan.controller.GuangfengController;
import com.shanghaichuangshi.jiyiguan.controller.LandwindController;
import com.shanghaichuangshi.model.*;
import com.shanghaichuangshi.model.File;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.shop.controller.*;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.jiyiguan.model.Fute;
import com.shanghaichuangshi.jiyiguan.model.Guangfeng;
import com.shanghaichuangshi.jiyiguan.model.Landwind;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WebConfig extends JFinalConfig {

    public void configConstant(Constants constants) {
        constants.setDevMode(false);
        constants.setViewType(ViewType.JSP);
        constants.setError404View("/error.jsp");

        try {
            System.out.println(URLEncoder.encode("http://api.jiyiguan.nowui.com/wechat/api/auth?url=home", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


//        List<Map<String, String>> provinceList = new ArrayList<Map<String, String>>();
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//
//        try {
//            String encoding = "UTF-8";
//            java.io.File file = new java.io.File(PathKit.getWebRootPath() + "/WEB-INF/classes/distributor.txt");
//            if (file.isFile() && file.exists()) {
//                InputStreamReader read = new InputStreamReader(
//                        new FileInputStream(file), encoding);
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                while ((lineTxt = bufferedReader.readLine()) != null) {
////                    list.add(lineTxt);
////                    Map<String, String> map = new HashMap<String, String>();
////                    map.put("province", lineTxt.split("\\|")[0]);
////                    map.put("city", lineTxt.split("\\|")[1]);
////                    map.put("distributor", lineTxt.split("\\|")[2]);
////                    list.add(map);
//
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("label", lineTxt.split("\\|")[0]);
//                    map.put("value", lineTxt.split("\\|")[0]);
//
//                    if (! provinceList.contains(map)) {
//                        provinceList.add(map);
//                    }
//                }
//                read.close();
//            } else {
//                System.out.println("找不到指定的文件");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//
//        System.out.println(JSON.toJSONString(provinceList));
    }

    public void configRoute(Routes routes) {
        routes.add("/admin", AdminController.class);
        routes.add("/authorization", AuthorizationController.class);
        routes.add("/log", LogController.class);
        routes.add("/category", CategoryController.class);
        routes.add("/code", CodeController.class);
        routes.add("/attribute", AttributeController.class);
        routes.add("/resource", ResourceController.class);
        routes.add("/role", RoleController.class);
        routes.add("/upload", UploadController.class);
        routes.add("/file", FileController.class);

        routes.add("/member", MemberController.class);
        routes.add("/brand", BrandController.class);
        routes.add("/product", ProductController.class);
        routes.add("/delivery", DeliveryController.class);
        routes.add("/order", OrderController.class);
        routes.add("/wechat/message", com.shanghaichuangshi.jiyiguan.controller.WeChatMsgController.class);
        routes.add("/wechat/api", WeChatApiController.class);
        routes.add("/distributor", DistributorController.class);
        routes.add("/supplier", SupplierController.class);
        routes.add("/scene", SceneController.class);

        routes.add("/landwind", LandwindController.class);
        routes.add("/guangfeng", GuangfengController.class);
        routes.add("/fute", FuteController.class);
        routes.add("/bill", BillController.class);
    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = new DruidPlugin(Jdbc.jdbc_url, Jdbc.user, Jdbc.password);
        druidPlugin.set(Jdbc.initial_size, Jdbc.min_idle, Jdbc.max_activee);
        druidPlugin.setFilters("stat,wall");
        plugins.add(druidPlugin);

        Slf4jLogFilter sql_log_filter = new Slf4jLogFilter();
        sql_log_filter.setConnectionLogEnabled(false);
        sql_log_filter.setStatementLogEnabled(false);
        sql_log_filter.setStatementExecutableSqlLogEnable(true);
        sql_log_filter.setResultSetLogEnabled(false);
        druidPlugin.addFilter(sql_log_filter);

        plugins.add(new EhCachePlugin());

        String baseSqlTemplatePath = PathKit.getWebRootPath() + "/WEB-INF/sql/";

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
        activeRecordPlugin.setBaseSqlTemplatePath(baseSqlTemplatePath);

        java.io.File[] files = new java.io.File(baseSqlTemplatePath).listFiles();
        for (java.io.File file : files) {
            if (file.isFile() || file.getName().endsWith(".sql")) {
                activeRecordPlugin.addSqlTemplate(file.getName());
            }
        }

        activeRecordPlugin.addMapping("table_admin", "admin_id", Admin.class);
        activeRecordPlugin.addMapping("table_authorization", "authorization_id", Authorization.class);
        activeRecordPlugin.addMapping("table_category", "category_id", Category.class);
        activeRecordPlugin.addMapping("table_log", "log_id", Log.class);
        activeRecordPlugin.addMapping("table_user", "user_id", User.class);
        activeRecordPlugin.addMapping("table_file", "file_id", File.class);
        activeRecordPlugin.addMapping("table_attribute", "attribute_id", Attribute.class);
        activeRecordPlugin.addMapping("table_resource", "resource_id", Resource.class);
        activeRecordPlugin.addMapping("table_role", "role_id", Role.class);

        activeRecordPlugin.addMapping("table_member", "member_id", Member.class);
        activeRecordPlugin.addMapping("table_member_level", "member_level_id", MemberLevel.class);
        activeRecordPlugin.addMapping("table_brand", "brand_id", Brand.class);
        activeRecordPlugin.addMapping("table_product", "product_id", Product.class);
        activeRecordPlugin.addMapping("table_delivery", "delivery_id", Delivery.class);
        activeRecordPlugin.addMapping("table_sku", "sku_id", Sku.class);
        activeRecordPlugin.addMapping("table_order", "order_id", Order.class);
        activeRecordPlugin.addMapping("table_order_product", "order_product_id", OrderProduct.class);
        activeRecordPlugin.addMapping("table_distributor", "distributor_id", Distributor.class);
        activeRecordPlugin.addMapping("table_supplier", "supplier_id", Supplier.class);
        activeRecordPlugin.addMapping("table_scene", "scene_id", Scene.class);
        activeRecordPlugin.addMapping("table_commission", "commission_id", Commission.class);
        activeRecordPlugin.addMapping("table_bill", "bill_id", Bill.class);

        activeRecordPlugin.addMapping("table_landwind", "landwind_id", Landwind.class);
        activeRecordPlugin.addMapping("table_guangfeng", "guangfeng_id", Guangfeng.class);
        activeRecordPlugin.addMapping("table_fute", "fute_id", Fute.class);

        plugins.add(activeRecordPlugin);
    }

    public void configInterceptor(Interceptors interceptors) {
        List<String> uncheckUrlList = new ArrayList<String>();
        uncheckUrlList.add(Url.WECHAT_API_MENU);
        uncheckUrlList.add(Url.WECHAT_API_AUTH);
        uncheckUrlList.add(Url.WECHAT_API_LOGIN);
        uncheckUrlList.add(Url.WECHAT_API_ORCODE);
        uncheckUrlList.add(Url.WECHAT_API_NOTIFY);
        uncheckUrlList.add(Url.WECHAT_API_OPENID);
        uncheckUrlList.add(Url.WECHAT_SHARE);
        uncheckUrlList.add(Url.WECHAT_MESSAGE);
        uncheckUrlList.add(com.shanghaichuangshi.jiyiguan.constant.Url.LANDWIND_EXPORT);
        uncheckUrlList.add(com.shanghaichuangshi.jiyiguan.constant.Url.FUTE_EXPORT);

        List<String> uncheckTokenUrlList = new ArrayList<String>();
        uncheckTokenUrlList.add(Url.PRODUCT_LIST);
        uncheckTokenUrlList.add(Url.PRODUCT_ALL_LIST);
        uncheckTokenUrlList.add(Url.PRODUCT_HOT_LIST);
        uncheckTokenUrlList.add(Url.MEMBER_WECHAT_LOGIN);
        uncheckTokenUrlList.add(com.shanghaichuangshi.jiyiguan.constant.Url.LANDWIND_SAVE);
        uncheckTokenUrlList.add(com.shanghaichuangshi.jiyiguan.constant.Url.GUANGFENG_RESULT_LIST);
        uncheckTokenUrlList.add(com.shanghaichuangshi.jiyiguan.constant.Url.GUANGFENG_SAVE);
        uncheckTokenUrlList.add(com.shanghaichuangshi.jiyiguan.constant.Url.FUTE_SAVE);

        List<String> uncheckRequestUserIdUrlList = new ArrayList<String>();
        uncheckRequestUserIdUrlList.add(Url.PRODUCT_FIND);
        uncheckRequestUserIdUrlList.add(Url.MEMBER_LOGIN);

        List<String> uncheckParameterUrlList = new ArrayList<String>();

        List<String> uncheckHeaderUrlList = new ArrayList<String>();

        interceptors.addGlobalActionInterceptor(new GlobalActionInterceptor(uncheckUrlList, uncheckTokenUrlList, uncheckRequestUserIdUrlList, uncheckParameterUrlList, uncheckHeaderUrlList));
    }

    public void configHandler(Handlers handlers) {

    }

}