
/**
 * 返回类，用于返回请求结果和提示信息
 * 参数：
 *      状态码code
 *      消息提示message
 *      返回数据object
 *      请求状态status
 * */
package com.yoa.uitl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;
    private boolean status;


    /**
     * 调用接口成功信息
     * @param message
     * @return
     * */
    public static Result succ(int code, String message, Object data){
        return new Result(code,message,data,true);
    }

    public static Result succ(String message){
        return new Result(200,message,null,true);
    }


    /**
     * 调用成功返回消息和数据
     * @param message
     * @param data
     * @return
     * */
    public static Result succ(String message, Object data){
        return new Result(200,message,data,true);
    }

    /**
     * 调用成功返回数据
     * @param data
     * @return
     * */
    public static Result succ(Object data){
        return new Result(200,"操作成功",data,true);
    }


    /**
     * 调用失败后返回消息，根据失败信息填写状态码
     * @param code
     * @param message
     * @return
     * */
    public static Result fail(int code, String message){
        return new Result(code,message,null,false);
    }

    public static Result fail(String message){
        return new Result(400,message,null,false);
    }


}
