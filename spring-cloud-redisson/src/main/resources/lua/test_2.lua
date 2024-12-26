-- KEYS 是传递给脚本的键名列表
-- ARGV 是传递给脚本的参数列表

-- 获取一个键的值
local value = redis.call("GET", KEYS[1])

-- 判断值是否为空
if value == false then
    redis.call("SET", KEYS[1], "default_value") -- 如果键不存在，设置默认值
end

-- 执行其他操作
redis.call("INCRBY", KEYS[1], 10)  -- 增加键的值

-- 返回结果
return redis.call("GET", KEYS[1])  -- 返回更新后的值
