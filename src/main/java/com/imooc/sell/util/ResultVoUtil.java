package com.imooc.sell.util;

import com.imooc.sell.vo.ProductVo;
import com.imooc.sell.vo.ResultVo;
import com.sun.net.httpserver.Authenticator;
import com.sun.org.apache.bcel.internal.generic.ARETURN;

import java.util.List;

/**
 * @ Author     ：wxd.
 * @ Date       ：Created in 21:27 2020/4/12
 * @ Description：result工具
 * @ Modified By：
 * @Version: 1.0$
 */
public class ResultVoUtil {
    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(object);
        return resultVo;
    }
    public static ResultVo success(){
        return success(null);
    }
    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }

}
