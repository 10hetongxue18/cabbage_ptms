package cn.xiaobaicai.cabbage_ptms_backend.model.domain.request;

import lombok.Data;

/**
 * @author hetongxue
 * @date 2022/10/14 - 20:39
 */
@Data
public class UserRegisterRequest {

    private String userAccount;

    private String userPassword;

}
