-- KEYS 是传递给脚本的键名列表
-- ARGV 是传递给脚本的参数列表

local args1 = tonumber(ARGV[1])

local args2 = tonumber(ARGV[2])

if not args1 or not args2 then
    return "Invalid number arguments"
end

if redis.call("EXISTS", KEYS[1]) == false then
    redis.call("SET", KEYS[1], 0)
end

redis.call("INCRBY", KEYS[1], args1)  -- 增加键的值
redis.call("INCRBY", KEYS[1], args2)  -- 增加键的值

-- 返回结果
return redis.call("GET", KEYS[1])  -- 返回更新后的值
