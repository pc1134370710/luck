# 付费知识与抽奖平台


分为四个服务：用户服务、付费知识服务、订单服务和抽奖服务。主要功能是管理员发布免费或付费的文章、项目demo文档等知识内容，在此基础上提供抽奖功能。管理员可以发布抽奖活动，设定奖项，奖品包含一定数量的付费文章或项目代码等。


 技术栈：SpringcloudAlibaba+mybatis-plus +RocketMQ +redis

对接支付宝支付功能, 当用户对付费知识付款成功后，通过支付宝回调将支付成功消息投递到 MQ 中，确保支付回调不会阻塞，再从 MQ 中消费（一条一条顺序消费），确保将商品查看权限添加到用户查看权限范围中。出现异常或者发现重复付款时再次投递到MQ退款队列中异步退款并告警。

 使用RocketMQ 对抽奖接口进行流量削峰，对奖品库存进行缓存预热，消费MQ抽奖消息使用redis+lua脚本进行扣减库存，脚本保证扣减库存或回滚库存的原子性，解决并发问题。同时数据库插入不可见抽奖记录并且发送扣减数据库库存消息到指定MQ队列中中进行扣减库存。最后将中奖情况存入redis 中，由前端定时回查中奖结果。

部分截图

![输入图片说明](https://foruda.gitee.com/images/1680346861062045692/373034af_6569495.png "屏幕截图")

![输入图片说明](https://foruda.gitee.com/images/1680346881385629796/38e81b7e_6569495.png "屏幕截图")
![输入图片说明](https://foruda.gitee.com/images/1680346928009996687/a0729f62_6569495.png "屏幕截图")

![输入图片说明](https://foruda.gitee.com/images/1680347142747845136/726c61e9_6569495.png "屏幕截图")

![输入图片说明](https://foruda.gitee.com/images/1680347158624629203/1c038d26_6569495.png "屏幕截图")

感兴趣的老哥，可以请喝杯咖啡

![输入图片说明](https://foruda.gitee.com/images/1680347243343103733/4be8512c_6569495.jpeg "3a5cd8b9bca7f83247d05a9b117c219.jpg")
![输入图片说明](https://foruda.gitee.com/images/1680347195384697473/f87f420a_6569495.jpeg "91363be09d5dc0f46ffc61e0d3bb597.jpg")