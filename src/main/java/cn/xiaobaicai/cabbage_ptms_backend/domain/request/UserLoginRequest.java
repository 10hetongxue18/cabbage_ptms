package cn.xiaobaicai.cabbage_ptms_backend.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hetongxue
 * @date 2022/10/14 - 20:39
 */
@Data
public class UserLoginRequest implements Serializable {

   private String userAccount;

   private String userPassword;

}
