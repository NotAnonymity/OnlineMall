"线上商城功能"
---
注: 后端返回格式统一为status, msg, data:
- status: 此次操作的执行情况，成功为success(默认), 失败为fail, 有其他情况可在接口说明
- msg: 若此次操作执行失败，返回失败原因，默认为null
- data: 返回数据，data为一个对象，其内包含返回的数据


一、拉取种类列表
- 描述：拉取商品种类列表
- URL：/mall/getCategoryList
- 前端请求参数: {}
- 后端返回参数: {status, msg, data:{[{id, name}, {...}]}}

二、拉取特定种类的商品
- 描述：用户点击种类列表，前端发送用户点击的种类列表的id号，后端返回特定的种类列表
- URL: /mall/getGoodsList
- 前端请求参数: {categoryId}
- 后端返回参数: {status, msg, data:{[{id, amount, categoryId, cost, imageUrl, name, price, sellCount}, {...}]}}

三、拉取购物车信息
- 描述：前端发送用户Id,拉取用户购物车信息
- URL：/mall/getCartInfo
- 前端请求参数: {userId}
- 后端返回参数: {status, msg, data:{[{id, userId, goodsId, goodsAmount}]}}

四、添加商品到顾客购物车
- 描述：前端发送商品id，添加此商品到用户购物车
- URL:/mall/addGoodsToCart
- 前端请求参数: {userId, goodsId}
- 后端返回参数: {status, msg, data}

五、减少购物车商品数量
- 描述：用户点击减少特定商品的按钮，减少此商品在购物车中的数量
- URL:/mall/reduceCartGoods
- 前端请求参数: {userId, goodsId}
- 后端返回参数：{status, msg, data}

六、清空购物车
- 描述：用户点击清空购物车按钮，清空购物车商品
- URL:/mall/cleanCart
- 前端请求参数: {userId}
- 后端返回参数：{status, msg, data}

七、结算购物车
- 描述：用户点击结算，前端发起结算请求，根据后端返回情况进行支付状况判断
- URL:/mall/payCart
- 前端请求参数：{userId}
- 后端返回参数：{status, msg, data}