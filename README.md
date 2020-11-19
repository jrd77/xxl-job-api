# xxl-job-api
xxl-job-api xxl-job api调用工具

目前 已经测试过的接口 获取token,创建任务,启动任务,停止任务
 版本适配 xxl-job 2.2.0,2.2.3
 必须使用 HttpKit.init 初始化
 目前属于snapshot 版本,主要是token有效期问题可以手动修改
 

        final String init = HttpKit.init("xxl-job.setting");
        System.out.println(init);
        final ReturnT result = JobInfoApi.triggerJob(3L, null, null);
        System.out.println(result);