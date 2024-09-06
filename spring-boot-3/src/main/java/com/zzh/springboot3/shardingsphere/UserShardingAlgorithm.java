package com.zzh.springboot3.shardingsphere;

import cn.hutool.json.JSONUtil;
import com.mchange.v2.util.CollectionUtils;
import com.zzh.springboot3.domain.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/6 11:50
 */
@Slf4j
@Data
public class UserShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    private static final String USER_ID = "id";
    private static final String ROLE_ID = "role_id";
    private static final String TABLE_PREFIX = "t_user_";

    private Properties props = new Properties();

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {
        log.info("availableTargetNames is :{}", JSONUtil.toJsonStr(availableTargetNames));
        log.info("shardingValue is :{}", JSONUtil.toJsonStr(shardingValue));
        Long userId = shardingValue.getColumnNameAndShardingValuesMap().get(USER_ID).stream().findFirst().orElse(0L);
        Long roleId = shardingValue.getColumnNameAndShardingValuesMap().get(ROLE_ID).stream().findFirst().orElse(0L);
        String target = TABLE_PREFIX + ((userId.hashCode() % 2 + roleId.hashCode() % 2) % 2);
        List<String> res = new ArrayList<>();
        res.add(target);
        return res;
    }

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }


}
