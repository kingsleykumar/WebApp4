package com.sb.repositories;

import java.util.Map;

/**
 * Created by Kingsley Kumar on 20/03/2018 at 23:58.
 */
public interface CustomUserRepository {

    boolean updateFields(String userId, Map<String, Object> fieldValueMap);
}
