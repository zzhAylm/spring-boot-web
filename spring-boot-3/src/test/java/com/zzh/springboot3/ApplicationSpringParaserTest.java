package com.zzh.springboot3;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zzh.springboot3.cache.DoubleCacheMethodInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
public class ApplicationSpringParaserTest {


    @Test
    public void test() {
        long startTime = System.currentTimeMillis();
        log.info("application test start is {}", startTime);

        String elStr = "#id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", "1");
        Order order = new Order();
        order.setId("1024");
        order.setName("zzh");
        params.put("order", order);

        String parser = DoubleCacheMethodInterceptor.parser(elStr, params);
        log.info("id is :{}", parser);

        String orderId = DoubleCacheMethodInterceptor.parser("#order.id", params);
        String orderName = DoubleCacheMethodInterceptor.parser("#order.name", params);
        log.info("order id is :{},order name is :{}", orderId, orderName);

        long endTime = System.currentTimeMillis();
        log.info("application test end time is {},execution time: {} milliseconds", endTime, endTime - startTime);
    }

    @Data
    public static class Order {
        private String id;
        private String name;
    }


    @Test
    public void JsonUtilTest() {

        String json = "{\n" +
                "    \"code\": \"0\",\n" +
                "    \"message\": \"SUCCESS\",\n" +
                "    \"data\": {\n" +
                "        \"id\": 139,\n" +
                "        \"realStartTime\": \"2024/06/11\",\n" +
                "        \"realEndTime\": \"2024/10/16\",\n" +
                "        \"realWorkLoad\": \"128\",\n" +
                "        \"projectName\": \"旅游批发商SAAS平台项目\",\n" +
                "        \"totalManager\": \"谢洪飞|SXF4147\",\n" +
                "        \"no\": \"SXF-LYPFS-202406\",\n" +
                "        \"beginTime\": \"20240611\",\n" +
                "        \"endTime\": \"20240903\",\n" +
                "        \"lesson\": \"1. 成功经验\\n1.1、明确的项目目标与范围：\\n在项目初期，确保所有相关方达成一致的项目目标与范围，避免后的范围蔓延。\\n1.2、有效的沟通与协作：\\n项目团队之间保持定期的沟通与反馈，促进各部门之间的协作，确保信息透明，有助于快速解决问题。\\n1.3、灵活的项目管理：\\n采用灵活的项目管理方法，如敏捷开发，有效应对需求变更，提高团队的响应速度和适应能力。\\n2. 遇到的问题与教训\\n2.1、需求变更管理不足：\\n项目在实施中出现了频繁的需求变更，导致资源浪费和进度滞后。后续需加强需求的前期分析和变更管理流程。\\n2.2、文档与知识传递缺失：\\n缺乏系统的文档记录，导致项目经验难以传承，后续团队在维护和迭代时遇到障碍。未来应建立完善的文档体系，提升知识管理水平。\\n3. 改进方向\\n3.1、加强需求管理：\\n在项目启动时建立严格的需求确认流程，并设置定期审查机制，以确保项目目标的稳定性。\\n3.2、定期团队回顾：\\n每个阶段结束后，开展项目回顾会议，总结经验教训，及时调整策略与方法，提升团队的整体能力。\\n3.3、知识共享与培训\\n鼓励团队成员分享项目经验，通过培训+练习提高整体素质与技能水平，减少项目实施中的盲区。\",\n" +
                "        \"transfer\": \"不涉及，本项目继续由项目组维护；\",\n" +
                "        \"brightenedDot\": \"1、创新\\n项目架构采用模块化管理的单体应用架构\\n创新价值：\\n①、既满足项目初期严格控制成本投入的同时，兼顾了灵活适应业务变化的能力；\\n②、通过清晰的系统结构划分，降低了项目组及协同团队间的管理成本；\\n\\n2、公共组件\\n①、数据变更自动审计插件\\n主要用于记录数据表中的数据变动操作，例如\\\"谁\\\"在\\\"什么时间\\\"修改了数据、修改\\\"前\\\"/\\\"后\\\"的数据内容等。这对于数据的追踪、审计和安全性都能非常有价值。此外，\\\"插件\\\"还可以用于查询数据的历史修改记录，帮助业务用户了解数据的变更历史和趋势。\\n\\n②、自动加解密插件\\n基于自定义注解+Mybatis拦截器实现的自动加解密处理插件；\\n\\n3、项目成员成长\\n①、 技术能力提升\\n在项目实施过程中，团队成员通过实际操作和解决复杂问题，明显提高了他们在相关技术领域（如编程语言、框架、工具等）的专业能力。\\n②、项目管理能力提升\\n部分成员在项目中负责任务分配和进度跟踪，积累了项目管理的经验，提高了对项目流程的理解和执行能力。\\n③、风险管理意识：\\n在面对项目风险时，团队成员能够迅速识别和应对，为项目的顺利推进做出了积极贡献，增强了其危机处理能力。\",\n" +
                "        \"state\": 0,\n" +
                "        \"projectCheck\": {\n" +
                "            \"id\": 125,\n" +
                "            \"projectName\": \"旅游批发商SAAS平台项目\",\n" +
                "            \"totalManager\": \"谢洪飞|SXF4147\",\n" +
                "            \"acceptance\": \"王怡|SXF3083\",\n" +
                "            \"acceptanceTime\": \"2024/10/11\",\n" +
                "            \"acceptanceEndTime\": \"2024/10/16\",\n" +
                "            \"checkResult\": \"项目达到预期目标\",\n" +
                "            \"projectId\": \"857634378439328768\",\n" +
                "            \"isSubmit\": 0,\n" +
                "            \"state\": 1,\n" +
                "            \"projectCheckGoal\": null\n" +
                "        },\n" +
                "        \"dept\": \"网络支付业务部\",\n" +
                "        \"projectId\": \"857634378439328768\",\n" +
                "        \"projectCheckGoal\": [\n" +
                "            {\n" +
                "                \"id\": 806,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"targetName\": \"交付完整可用的旅游批发商SAAS平台\",\n" +
                "                \"successStandard\": null,\n" +
                "                \"completion\": \"100\",\n" +
                "                \"deviation\": null,\n" +
                "                \"weight\": 90,\n" +
                "                \"remark\": \"对产品价值主张和业务诉求充分理解的基础上，不打折扣的完整落地，并以系统形式交付；\",\n" +
                "                \"rank\": 0,\n" +
                "                \"targetId\": \"858397099598414848\",\n" +
                "                \"remarkCheck\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 807,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"targetName\": \"团队成员按需成长\",\n" +
                "                \"successStandard\": null,\n" +
                "                \"completion\": \"105\",\n" +
                "                \"deviation\": null,\n" +
                "                \"weight\": 10,\n" +
                "                \"remark\": \"通过执行科学的项目管理理念，使团队成员从研发技能、业务分析能力、管理能力等维度获得提升；\",\n" +
                "                \"rank\": 1,\n" +
                "                \"targetId\": \"858397099598414849\",\n" +
                "                \"remarkCheck\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 810,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"targetName\": \"交付完整可用的旅游批发商SAAS平台\",\n" +
                "                \"successStandard\": null,\n" +
                "                \"completion\": \"99\",\n" +
                "                \"deviation\": null,\n" +
                "                \"weight\": 90,\n" +
                "                \"remark\": \"对产品价值主张和业务诉求充分理解的基础上，不打折扣的完整落地，并以系统形式交付；\",\n" +
                "                \"rank\": 0,\n" +
                "                \"targetId\": \"858397099598414848\",\n" +
                "                \"remarkCheck\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 811,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"targetName\": \"团队成员按需成长\",\n" +
                "                \"successStandard\": null,\n" +
                "                \"completion\": \"105\",\n" +
                "                \"deviation\": null,\n" +
                "                \"weight\": 10,\n" +
                "                \"remark\": \"通过执行科学的项目管理理念，使团队成员从研发技能、业务分析能力、管理能力等维度获得提升；\",\n" +
                "                \"rank\": 1,\n" +
                "                \"targetId\": \"858397099598414849\",\n" +
                "                \"remarkCheck\": null\n" +
                "            }\n" +
                "        ],\n" +
                "        \"projectMember\": [\n" +
                "            {\n" +
                "                \"id\": 22868,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"谢洪飞|SXF4147\",\n" +
                "                \"position\": \"项目经理\",\n" +
                "                \"realCycle\": \"2024/06/11-2024/10/11\",\n" +
                "                \"days\": \"92\",\n" +
                "                \"remark\": null,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-渠道研发部-金融开发组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/11,2024/06/12,2024/06/13,2024/06/14,2024/06/15,2024/06/16,2024/06/17,2024/06/18,2024/06/19,2024/06/20,2024/06/21,2024/06/22,2024/06/23,2024/06/24,2024/06/25,2024/06/26,2024/06/27,2024/06/28,2024/06/29,2024/06/30,2024/07/01,2024/07/02,2024/07/03,2024/07/04,2024/07/05,2024/07/08,2024/07/09,2024/07/10,2024/07/11,2024/07/12,2024/07/15,2024/07/16,2024/07/17,2024/07/18,2024/07/19,2024/07/22,2024/07/23,2024/07/24,2024/07/25,2024/07/26,2024/07/29,2024/07/30,2024/07/31,2024/08/01,2024/08/02,2024/08/05,2024/08/06,2024/08/07,2024/08/08,2024/08/09,2024/08/10,2024/08/12,2024/08/13,2024/08/14,2024/08/15,2024/08/16,2024/08/17,2024/08/19,2024/08/20,2024/08/21,2024/08/22,2024/08/23,2024/08/24,2024/08/26,2024/08/27,2024/08/28,2024/08/29,2024/08/30,2024/09/02,2024/09/03,2024/09/04,2024/09/05,2024/09/06,2024/09/09,2024/09/10,2024/09/12,2024/09/13,2024/09/14,2024/09/18,2024/09/19,2024/09/20,2024/09/23,2024/09/24,2024/09/25,2024/09/26,2024/09/27,2024/09/29,2024/09/30,2024/10/08,2024/10/09,2024/10/10,2024/10/11\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 59,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22869,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"宋昕雨|SXF4424\",\n" +
                "                \"position\": \"开发工程师\",\n" +
                "                \"realCycle\": \"2024/06/11-2024/10/11\",\n" +
                "                \"days\": \"92\",\n" +
                "                \"remark\": null,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-渠道研发部-金融开发组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/11,2024/06/12,2024/06/13,2024/06/14,2024/06/15,2024/06/16,2024/06/17,2024/06/18,2024/06/19,2024/06/20,2024/06/21,2024/06/22,2024/06/23,2024/06/24,2024/06/25,2024/06/26,2024/06/27,2024/06/28,2024/06/29,2024/06/30,2024/07/01,2024/07/02,2024/07/03,2024/07/04,2024/07/05,2024/07/08,2024/07/09,2024/07/10,2024/07/11,2024/07/12,2024/07/15,2024/07/16,2024/07/17,2024/07/18,2024/07/19,2024/07/22,2024/07/23,2024/07/24,2024/07/25,2024/07/26,2024/07/29,2024/07/30,2024/07/31,2024/08/01,2024/08/02,2024/08/05,2024/08/06,2024/08/07,2024/08/08,2024/08/09,2024/08/10,2024/08/12,2024/08/13,2024/08/14,2024/08/15,2024/08/16,2024/08/17,2024/08/19,2024/08/20,2024/08/21,2024/08/22,2024/08/23,2024/08/24,2024/08/26,2024/08/27,2024/08/28,2024/08/29,2024/08/30,2024/09/02,2024/09/03,2024/09/04,2024/09/05,2024/09/06,2024/09/09,2024/09/10,2024/09/11,2024/09/12,2024/09/13,2024/09/14,2024/09/18,2024/09/19,2024/09/20,2024/09/23,2024/09/24,2024/09/25,2024/09/26,2024/09/27,2024/09/29,2024/09/30,2024/10/08,2024/10/10,2024/10/11\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 61,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22870,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"董校池|SXF4925\",\n" +
                "                \"position\": \"开发工程师\",\n" +
                "                \"realCycle\": \"2024/06/11-2024/10/11\",\n" +
                "                \"days\": \"86\",\n" +
                "                \"remark\": null,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-渠道研发部-用户权益组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/11,2024/06/12,2024/06/13,2024/06/14,2024/06/15,2024/06/16,2024/06/17,2024/06/18,2024/06/19,2024/06/20,2024/06/21,2024/06/22,2024/06/23,2024/06/24,2024/06/25,2024/06/26,2024/06/27,2024/06/28,2024/06/29,2024/06/30,2024/07/01,2024/07/02,2024/07/03,2024/07/04,2024/07/05,2024/07/08,2024/07/09,2024/07/10,2024/07/11,2024/07/12,2024/07/16,2024/07/17,2024/07/18,2024/07/22,2024/07/23,2024/07/24,2024/07/25,2024/07/26,2024/07/29,2024/07/30,2024/07/31,2024/08/01,2024/08/02,2024/08/05,2024/08/06,2024/08/07,2024/08/08,2024/08/09,2024/08/10,2024/08/12,2024/08/13,2024/08/14,2024/08/15,2024/08/16,2024/08/17,2024/08/19,2024/08/20,2024/08/21,2024/08/22,2024/08/23,2024/08/26,2024/08/27,2024/08/28,2024/08/29,2024/08/30,2024/09/02,2024/09/03,2024/09/04,2024/09/05,2024/09/06,2024/09/09,2024/09/10,2024/09/11,2024/09/12,2024/09/13,2024/09/14,2024/09/18,2024/09/19,2024/09/20,2024/09/23,2024/09/24,2024/09/25,2024/09/26,2024/10/09,2024/10/10,2024/10/11\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 53,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22871,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"马强|SXF4966\",\n" +
                "                \"position\": \"开发工程师\",\n" +
                "                \"realCycle\": \"2024/06/11-2024/10/11\",\n" +
                "                \"days\": \"92\",\n" +
                "                \"remark\": null,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-用户应用部-风控应用组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/11,2024/06/12,2024/06/13,2024/06/14,2024/06/15,2024/06/16,2024/06/17,2024/06/18,2024/06/19,2024/06/20,2024/06/21,2024/06/22,2024/06/23,2024/06/24,2024/06/25,2024/06/26,2024/06/27,2024/06/28,2024/06/29,2024/06/30,2024/07/01,2024/07/02,2024/07/03,2024/07/04,2024/07/05,2024/07/08,2024/07/09,2024/07/10,2024/07/11,2024/07/12,2024/07/15,2024/07/16,2024/07/17,2024/07/18,2024/07/19,2024/07/22,2024/07/23,2024/07/24,2024/07/25,2024/07/26,2024/07/29,2024/07/30,2024/07/31,2024/08/01,2024/08/02,2024/08/05,2024/08/06,2024/08/07,2024/08/08,2024/08/09,2024/08/12,2024/08/13,2024/08/14,2024/08/15,2024/08/16,2024/08/17,2024/08/19,2024/08/20,2024/08/21,2024/08/22,2024/08/23,2024/08/24,2024/08/26,2024/08/27,2024/08/28,2024/08/29,2024/08/30,2024/09/02,2024/09/03,2024/09/04,2024/09/05,2024/09/06,2024/09/09,2024/09/10,2024/09/11,2024/09/12,2024/09/13,2024/09/14,2024/09/18,2024/09/19,2024/09/20,2024/09/23,2024/09/24,2024/09/25,2024/09/26,2024/09/27,2024/09/29,2024/09/30,2024/10/08,2024/10/09,2024/10/10,2024/10/11\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 59,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22872,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"陈国栋|SXF6287\",\n" +
                "                \"position\": \"开发工程师\",\n" +
                "                \"realCycle\": \"2024/06/11-2024/10/11\",\n" +
                "                \"days\": \"87\",\n" +
                "                \"remark\": null,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-交易研发部-互联网平台组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/11,2024/06/12,2024/06/13,2024/06/14,2024/06/15,2024/06/16,2024/06/17,2024/06/18,2024/06/19,2024/06/20,2024/06/21,2024/06/22,2024/06/23,2024/06/24,2024/06/25,2024/06/26,2024/06/27,2024/06/28,2024/06/29,2024/06/30,2024/07/01,2024/07/02,2024/07/03,2024/07/04,2024/07/05,2024/07/08,2024/07/09,2024/07/10,2024/07/15,2024/07/16,2024/07/17,2024/07/18,2024/07/19,2024/07/22,2024/07/23,2024/07/24,2024/07/25,2024/07/26,2024/07/29,2024/07/30,2024/07/31,2024/08/01,2024/08/02,2024/08/05,2024/08/06,2024/08/07,2024/08/08,2024/08/09,2024/08/10,2024/08/12,2024/08/13,2024/08/14,2024/08/15,2024/08/16,2024/08/17,2024/08/19,2024/08/20,2024/08/21,2024/08/22,2024/08/23,2024/08/26,2024/08/27,2024/08/28,2024/08/29,2024/08/30,2024/09/02,2024/09/03,2024/09/04,2024/09/05,2024/09/06,2024/09/09,2024/09/10,2024/09/11,2024/09/12,2024/09/13,2024/09/14,2024/09/18,2024/09/19,2024/09/20,2024/09/23,2024/09/24,2024/09/25,2024/09/26,2024/09/27,2024/10/09,2024/10/10,2024/10/11\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 53,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22873,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"张赛|SXF6510\",\n" +
                "                \"position\": \"开发工程师\",\n" +
                "                \"realCycle\": \"2024/06/13-2024/08/28\",\n" +
                "                \"days\": \"61\",\n" +
                "                \"remark\": \"张赛2024年8月29日退出项目组\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-资金研发部-账务组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/13,2024/06/14,2024/06/15,2024/06/16,2024/06/17,2024/06/18,2024/06/19,2024/06/20,2024/06/21,2024/06/22,2024/06/23,2024/06/24,2024/06/25,2024/06/26,2024/06/27,2024/06/28,2024/06/29,2024/06/30,2024/07/01,2024/07/02,2024/07/03,2024/07/04,2024/07/05,2024/07/08,2024/07/09,2024/07/10,2024/07/11,2024/07/12,2024/07/15,2024/07/16,2024/07/17,2024/07/18,2024/07/23,2024/07/24,2024/07/25,2024/07/26,2024/07/29,2024/07/30,2024/07/31,2024/08/01,2024/08/02,2024/08/05,2024/08/06,2024/08/07,2024/08/08,2024/08/09,2024/08/12,2024/08/13,2024/08/14,2024/08/15,2024/08/16,2024/08/17,2024/08/19,2024/08/20,2024/08/21,2024/08/22,2024/08/23,2024/08/24,2024/08/26,2024/08/27,2024/08/28\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 31,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22874,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"陈雪|SXF2929\",\n" +
                "                \"position\": \"开发工程师\",\n" +
                "                \"realCycle\": \"2024/06/11-2024/06/12\",\n" +
                "                \"days\": \"2\",\n" +
                "                \"remark\": \"中途退出\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-资金研发部-结算组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/11,2024/06/12\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 0,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22875,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"谭宝红|SXF5825\",\n" +
                "                \"position\": \"前端开发工程师\",\n" +
                "                \"realCycle\": \"2024/06/26-2024/07/12\",\n" +
                "                \"days\": \"15\",\n" +
                "                \"remark\": null,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-支付研发部-支付开发组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/06/26,2024/06/27,2024/06/28,2024/06/29,2024/06/30,2024/07/01,2024/07/02,2024/07/03,2024/07/04,2024/07/05,2024/07/08,2024/07/09,2024/07/10,2024/07/11,2024/07/12\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 7,\n" +
                "                \"isImport\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 22990,\n" +
                "                \"number\": null,\n" +
                "                \"projectMember\": \"石锐锐|SXF5696\",\n" +
                "                \"position\": \"数据分析师\",\n" +
                "                \"realCycle\": \"2024/08/15-2024/10/11\",\n" +
                "                \"days\": \"39\",\n" +
                "                \"remark\": null,\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"department\": \"研发中心-数据部-数据分析组\",\n" +
                "                \"progressId\": null,\n" +
                "                \"attendDateList\": null,\n" +
                "                \"state\": null,\n" +
                "                \"attendDates\": \"2024/08/15,2024/08/16,2024/08/17,2024/08/19,2024/08/20,2024/08/21,2024/08/22,2024/08/23,2024/08/24,2024/08/26,2024/08/27,2024/08/28,2024/08/29,2024/08/30,2024/09/02,2024/09/03,2024/09/04,2024/09/05,2024/09/06,2024/09/09,2024/09/10,2024/09/11,2024/09/12,2024/09/13,2024/09/14,2024/09/18,2024/09/19,2024/09/20,2024/09/23,2024/09/24,2024/09/25,2024/09/26,2024/09/27,2024/09/29,2024/09/30,2024/10/08,2024/10/09,2024/10/10,2024/10/11\",\n" +
                "                \"performanceStar\": null,\n" +
                "                \"preparationId\": null,\n" +
                "                \"overtimeDays\": 31,\n" +
                "                \"isImport\": 1\n" +
                "            }\n" +
                "        ],\n" +
                "        \"projectOutput\": [\n" +
                "            {\n" +
                "                \"id\": \"858400063897601024\",\n" +
                "                \"typeName\": \"其它\",\n" +
                "                \"attachmentName\": \"【立项评审申请】旅游批发商SAAS平台.pdf\",\n" +
                "                \"attachmentId\": \"974d9cebb7cfae5c49d8f05e7cb4eb9f9f344caa\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1718260715000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": null,\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"866397895799735296\",\n" +
                "                \"typeName\": \"设计文档\",\n" +
                "                \"attachmentName\": \"旅游批发SAAS平台订单流转.png\",\n" +
                "                \"attachmentId\": \"f9de622c9d204d969c294cafd78548ca5fdb0682\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1720167547000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"谢洪飞|SXF4147\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"866398589835412480\",\n" +
                "                \"typeName\": \"设计文档\",\n" +
                "                \"attachmentName\": \"旅游批发SAAS项目业务模型-V6.pdf\",\n" +
                "                \"attachmentId\": \"90d08b11d151a213e9b075ba384f122f29836814\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1720167713000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"谢洪飞|SXF4147\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"866398758794560512\",\n" +
                "                \"typeName\": \"需求文档\",\n" +
                "                \"attachmentName\": \"旅游批发商Saas——最基础的概念和流程.pdf\",\n" +
                "                \"attachmentId\": \"a4003dc4e9dffcc6508f77b71e4f624b951b05dd\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1720167753000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"谢洪飞|SXF4147\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"866398858363143168\",\n" +
                "                \"typeName\": \"设计文档\",\n" +
                "                \"attachmentName\": \"travel-wholesale-saas.bmpr\",\n" +
                "                \"attachmentId\": \"7bc801595336f3c91d37c1f1e06fbb06b78d8ee5\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1720167777000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"谢洪飞|SXF4147\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"871116242764301312\",\n" +
                "                \"typeName\": \"其它\",\n" +
                "                \"attachmentName\": \"【复盘】旅游批发商SAAS平台项目第1阶段.xlsx\",\n" +
                "                \"attachmentId\": \"d21913624306ad3cf79ac3006051fc0138673927\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1721292489000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"谢洪飞|SXF4147\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"902284599718708224\",\n" +
                "                \"typeName\": \"其他\",\n" +
                "                \"attachmentName\": \"研发系人员出勤基础信息表-202406.xlsx\",\n" +
                "                \"attachmentId\": \"d6f23fac413262999808824066525acc5a539d7d\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1728723604000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"姜洪峰|SXF3549\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"902284652663407616\",\n" +
                "                \"typeName\": \"其他\",\n" +
                "                \"attachmentName\": \"研发系人员出勤基础信息表-202407.xlsx\",\n" +
                "                \"attachmentId\": \"3e21c8e0fb9d917fca7cf748610d3f5da9fc23ac\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1728723617000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"姜洪峰|SXF3549\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"902284702294606848\",\n" +
                "                \"typeName\": \"其他\",\n" +
                "                \"attachmentName\": \"研发系人员出勤基础信息表-202408.xlsx\",\n" +
                "                \"attachmentId\": \"4667b4494e692a5bcd34b491ccaa1d26ce7c91ef\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1728723629000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"姜洪峰|SXF3549\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"902284745743401984\",\n" +
                "                \"typeName\": \"其他\",\n" +
                "                \"attachmentName\": \"研发系人员出勤基础信息表-202409.xlsx\",\n" +
                "                \"attachmentId\": \"e1674197e3d128c92f5cbb57657a3bd276f9ff54\",\n" +
                "                \"projectId\": \"857634378439328768\",\n" +
                "                \"uploadTime\": 1728723639000,\n" +
                "                \"typeEnum\": 3,\n" +
                "                \"submitName\": \"姜洪峰|SXF3549\",\n" +
                "                \"progressId\": null,\n" +
                "                \"isShowAdmin\": 1\n" +
                "            }\n" +
                "        ],\n" +
                "        \"productDateFact\": \"2024/10/11\",\n" +
                "        \"beforeProductWorkload\": \"534\",\n" +
                "        \"businessCheckWorkload\": \"10\",\n" +
                "        \"overtimeWorkload\": null,\n" +
                "        \"version\": 14,\n" +
                "        \"projectRiskList\": [\n" +
                "            {\n" +
                "                \"id\": \"858411159576511488\",\n" +
                "                \"projectRegeditId\": \"857634378439328768\",\n" +
                "                \"riskType\": \"质量风险\",\n" +
                "                \"startNum\": 5,\n" +
                "                \"describes\": \"创新型业务，客观存在不确定性高的特点，可能引发研发进度及质量风险。\",\n" +
                "                \"solve\": \"1、尝试敏捷开发模式，合理拆解任务，按功能快速交付、滚动开发；\\n2、加强并提升与产品同事沟通频率与质量；\",\n" +
                "                \"solveDate\": 1721902084000,\n" +
                "                \"isSolved\": 1,\n" +
                "                \"solveFact\": \"1、尝试敏捷开发模式，合理拆解任务，按功能快速交付、滚动开发；\\n2、加强并提升与产品同事沟通频率与质量；\",\n" +
                "                \"solveDateFact\": 1721738934000,\n" +
                "                \"submitModule\": \"筹建管理\",\n" +
                "                \"submitDate\": 1718263361000,\n" +
                "                \"updateDate\": 1722603020000,\n" +
                "                \"updateModule\": \"进度报告-旅游批发商SAAS平台项目周报【第7周】\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";


        JSONObject jsonObject = JSONUtil.parseObj(json);

        JSONObject data = jsonObject.getJSONObject("data");

        JSONArray projectMembers = data.getJSONArray("projectMember");


        List<Project> projects = projectMembers.stream().map((item) -> {
            JSONObject obj = JSONUtil.parseObj(item);
            String projectMember = obj.getStr("projectMember");
            String realCycle = obj.getStr("realCycle");
            String attendDates = obj.getStr("attendDates");
            Project project = new Project();
            project.setProjectName(data.getStr("projectName"));
            project.setName(projectMember);
            project.setRealCycle(realCycle);
            String newAttendDates = Arrays.stream(attendDates.split(",")).filter(str -> str.startsWith("2024/10")).reduce((str1, str2) -> str1 + "," + str2).orElse("");
            project.setAttendDates(newAttendDates);
            return project;
        }).toList();


        try (ExcelWriter writer = ExcelUtil.getWriter("/Users/zzh/Company/projects/spring-boot-web/spring-boot-3/src/main/resources/static/项目考勤1.xlsx")){
            //自定义标题别名
            writer.addHeaderAlias("projectName", "项目名称");
            writer.addHeaderAlias("realCycle", "项目周期");
            writer.addHeaderAlias("name", "姓名");
            writer.addHeaderAlias("attendDates", "参数日期");
            writer.setOnlyAlias(true);
            // 一次性写出内容，使用默认样式，强制输出标题
            writer.write(projects, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Data
    public static class Project implements Serializable {
        private String projectName;
        private String realCycle;
        private String name;
        private String attendDates;
    }


}
