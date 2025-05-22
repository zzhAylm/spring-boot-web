//package com.zzh.ai.test;
//
//import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SentinelExceptionHandler implements BlockExceptionHandler {
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
//        String msg = "未知异常";
//        int status = HttpStatus.TOO_MANY_REQUESTS.value();
//        if (e instanceof FlowException) {
//            msg = "请求被限流了";
//        } else if (e instanceof ParamFlowException) {
//            msg = "请求被热点参数限流";
//        } else if (e instanceof DegradeException) {
//            msg = "请求被降级了";
//        } else if (e instanceof AuthorityException) {
//            msg = "没有权限访问";
//            status = HttpStatus.UNAUTHORIZED.value();
//        }
//        response.setContentType("application/json;charset=utf-8");
//        response.setStatus(status);
//        response.getWriter().println("{\"msg\": " + msg + ", \"code\": " + status + "}");
//    }
//}
