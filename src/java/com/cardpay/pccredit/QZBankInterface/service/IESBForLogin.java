package com.cardpay.pccredit.QZBankInterface.service;

/**
 * 核算接口
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.service.QuotaFreezeOrThawService;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.dc.eai.data.Array;
import com.dc.eai.data.CompositeData;
import com.dc.eai.data.Field;
import com.dc.eai.data.FieldAttr;
import com.dc.eai.data.FieldType;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class IESBForLogin {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private QuotaFreezeOrThawService quotaFreezeOrThawService;
    /**
     * 组装CompositeData报文
     * @return
     */
    public CompositeData createRequest(String loginId,String userName,String password){
    	SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
    	SimpleDateFormat formatter8 = new SimpleDateFormat("yyyyMMdd");
    	SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");
    	
        CompositeData cd = new CompositeData();

        //SYS_HEAD
        CompositeData syaHead_struct = new CompositeData();
        //在syaHead_struct中加SERVICECODE
        Field serviceCodeField = new Field(new FieldAttr(FieldType.FIELD_STRING, 11));
        serviceCodeField.setValue("11002000002");//
        syaHead_struct.addField("SERVICE_CODE", serviceCodeField);

        //在syaHead_struct中加SERVICESCENE
        Field serviceSceneField = new Field(new FieldAttr(FieldType.FIELD_STRING, 2));
        serviceSceneField.setValue("10"); //核算对应服务场景01
        syaHead_struct.addField("SERVICE_SCENE", serviceSceneField);
        
       //在syaHead_struct中加TRAN_DATE
        Field tran_datefield = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        tran_datefield.setValue(formatter8.format(new Date())); //交易日期
        syaHead_struct.addField("TRAN_DATE", tran_datefield);
        
        //在syaHead_struct中加CONSUMER_ID
        Field consumer_idField = new Field(new FieldAttr(FieldType.FIELD_STRING, 6));
        consumer_idField.setValue("300025"); //消费系统编号
        syaHead_struct.addField("CONSUMER_ID", consumer_idField);
        
        cd.addStruct("SYS_HEAD", syaHead_struct);

        //BODY
        CompositeData body_struct = new CompositeData();

        //客户号
        Field USER_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 50));
        USER_NO.setValue(loginId);
        body_struct.addField("USER_NO", USER_NO);
        
        Field PASSWORD=new Field(new FieldAttr(FieldType.FIELD_STRING, 150));
        PASSWORD.setValue(MD5Util.encode(loginId + password));
        body_struct.addField("PASSWORD", PASSWORD);
        
        Field USER_NAME=new Field(new FieldAttr(FieldType.FIELD_STRING, 50));
        USER_NAME.setValue(userName);
        body_struct.addField("USER_NAME", USER_NAME);
        
        cd.addStruct("BODY",body_struct);

        return cd;
    }
    
    /**
     * 返回成功与否
     * @param resp
     * @return
     * @throws Exception 
     */
	public String parseCCHResponse(CompositeData resp) throws Exception {
		if(resp == null){
			return "统一平台登录接口调用失败";//接口调用失败
		}
		CompositeData SYS_HEAD = resp.getStruct("SYS_HEAD");

        //根据数组名称去获取数组
        Array RET = SYS_HEAD.getArray("RET");
        
        String RET_CODE = "";
        String RET_MSG = "";
        if(null != RET && RET.size() > 0){
            int m = RET.size();
            CompositeData array_element = null;
            for (int i = 0; i < m; i++) {
                //数组中的元素也是CompositeData，这是固定的写法。根据游标就可以获取到数组中的所有元素
                array_element = RET.getStruct(i);

                RET_CODE = array_element.getField("RET_CODE").strValue();
                RET_MSG = array_element.getField("RET_MSG").strValue();
            }
        }
        //存在前一笔授信协议
        if(IpadConstant.RET_CODE_SUCCESS.equals(RET_CODE)){
        	return IpadConstant.RET_CODE_SUCCESS;
        }else{
        	return RET_MSG;
        }
	}
	
}
