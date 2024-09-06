package com.zzh.springboot3.shardingsphere;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

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
public class RoleShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    private static final String ROLE_ID = "id";
    private static final String NAME = "name";
    private static final String TABLE_PREFIX = "t_role_";

    private Properties props = new Properties();

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        List<String> res = new ArrayList<>();
        log.info("availableTargetNames is :{}", JSONUtil.toJsonStr(availableTargetNames));
        log.info("shardingValue is :{}", JSONUtil.toJsonStr(shardingValue));
        List<Long> ids = (List<Long>) shardingValue.getColumnNameAndShardingValuesMap().get(ROLE_ID);
        long roleId = ids.stream().findFirst().orElse(0L);
        List<String> names = (List<String>) shardingValue.getColumnNameAndShardingValuesMap().get(NAME);
        String name = names.stream().findFirst().orElse("管理员");
        String target = TABLE_PREFIX + ((name.hashCode() % 2 + Long.hashCode(roleId) % 2) % 2);
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
