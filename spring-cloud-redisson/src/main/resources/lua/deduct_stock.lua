-- KEYS[1]: 商品库存hash key (seckill:stock)
-- KEYS[2]: 商品ID
-- ARGV[1]: 扣减数量

-- 获取当前商品库存
local stock = redis.call('hget', KEYS[1], KEYS[2])

-- 如果库存key不存在
if not stock then
    return -1
end

-- 转换为数字
local curStock = tonumber(stock)
local deductAmount = tonumber(ARGV[1])

-- 检查库存是否充足
if curStock < deductAmount then
    return -2
end

-- 扣减库存并更新hash
redis.call('hset', KEYS[1], KEYS[2], curStock - deductAmount)
return curStock - deductAmount 