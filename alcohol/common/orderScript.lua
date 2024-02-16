local itemId = ARGV[1]
local itemKey = "SalesItem:" .. itemId
local stock = tonumber(redis.call('HGET', itemKey, 'stock'))

if stock <= 0 then
    return redis.error_reply("NO_SUCH_ORDER")
end

stock = stock - 1
redis.call('HSET', itemKey, 'stock', tostring(stock))

return stock
