package com.ando.exam.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
/**
 * @Descrip: Controller 基类
 * @ClassName:  BaseController 
 * @author: ando
 * @date:   2019�?1�?31�? 下午9:40:42    
 */
public class BaseController {
	/**
	 * @Title: init   
	 * @Description: TODO
	 * @param: @param request
	 * @param: @param binder      
	 * @return: void      
	 * @throws
	 */
	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));/* TimeZone时区，解决差8小时的问�? */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
    /**
     * 错误标识
     */
    private boolean success = true;

    /**
     * 异常标识. 业务异常该标识为false，只有在exceptionInterceptor中出现的异常才会是true
     */
    private boolean isException = false;

    /**
     * 用于返回执行的结果提示信�?
     */
    private String message;

    /**
     * request 对象
     */
    protected HttpServletRequest request;
    /**
     * response 对像
     */
    protected HttpServletResponse response;

    public static final String PROTOCOL_SUCCESS = "success";

    public static final String PROTOCOL_EXCEPTION = "exception";

    public static final String PROTOCOL_MESSAGE = "message";

    /**
     * 线程安全变量
     */
    private ThreadLocal<Map<String, Object>> mapStore = new ThreadLocal<Map<String, Object>>();

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> m = mapStore.get();
        if (m == null) {
            m = new HashMap<String, Object>();
            mapStore.set(m);
        }
        return m;
    }

    /**
     * 默认Success方法
     *
     * @return map
     */
    protected Map<String, Object> returnSuccess() {
        return returnSuccess(null);
    }

    /**
     * 通过传入消息类型和格式化参数，得到国际化后的消息，并返回"success"
     *
     * @param message 格式化参�?
     * @return map
     */
    protected Map<String, Object> returnSuccess(String message) {
        // Success类型报文消息头部
        this.setSuccess(true);
        this.setException(false);
        this.setMessage(message);
        return inflateAndResetInnerMap(this.success, this.isException, this.message);
    }

    /**
     * 业务异常报文
     *
     * @param e exception
     * @return map
     */
    protected Map<String, Object> returnError(Exception e) {
        this.setSuccess(false);
        this.setException(true);
        this.setMessage(e.getMessage());
        return inflateAndResetInnerMap(this.success, this.isException, this.message);
    }

    /**
     * 默认的异常方�?
     *
     * @return map
     */
    protected Map<String, Object> returnError() {
        return this.returnError("error");
    }

    /**
     * 通过传入消息类型和格式化参数，得到国际化后的消息，并返回"error"
     *
     * @param message 消息类型
     * @return map
     */
    protected Map<String, Object> returnError(String message) {
        this.setSuccess(false);
        this.setException(true);
        this.setMessage(message);
        return inflateAndResetInnerMap(this.success, this.isException, this.message);
    }


    /**
     * 设置返回报文信息并重置ThreadLocal变量
     *
     * @param success     success
     * @param isException exception
     * @param msg         message
     * @return map
     */
    private Map<String, Object> inflateAndResetInnerMap(boolean success, boolean isException, String msg) {
        Map<String, Object> map = this.getMap();
        map.put(PROTOCOL_SUCCESS, success);
        map.put(PROTOCOL_EXCEPTION, isException);
        map.put(PROTOCOL_MESSAGE, msg);
        this.mapStore.remove();
        return map;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean isException) {
        this.isException = isException;
    }

}