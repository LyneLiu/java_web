### Apache Commons-pool2

> ##### ObjectPool：
实现对对象存取和状态管理的pool，如：线程池、数据库连接池；
> ##### PooledObject：
池化对象，是需要放到ObjectPool中的一个包装类，添加了一些附加的信息，比如说状态信息，创建时间，激活时间，关闭时间等；
> ##### PooledObjectFactory：
工厂类，负责具体对象的创建、初始化，对象状态的销毁和验证。