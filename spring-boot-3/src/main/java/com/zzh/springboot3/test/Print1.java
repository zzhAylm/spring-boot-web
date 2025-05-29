package com.zzh.springboot3.test;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/4/16 21:00
 */
public class Print1 {


    private static ReentrantLock lock = new ReentrantLock();

    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();


    private volatile static int state = 0; // 0 -> A, 1 -> B, 2 -> C
    private static final char[] str = new char[9];

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSortHelper(arr, 0, arr.length - 1, temp);
    }

    private static void mergeSortHelper(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid, temp);
            mergeSortHelper(arr, mid + 1, right, temp);
            merge(arr, left, mid, right, temp);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        int t = 0;
        
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        
        t = 0;
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }
    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state % 3 != 0) {
                        conditionA.await();
                    }
                    str[state] = 'A';
                    state++;
                    conditionB.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state % 3 != 1) {
                        conditionB.await();
                    }
                    str[state] = 'B';
                    state++;
                    conditionC.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state % 3 != 2) {
                        conditionC.await();
                    }
                    str[state] = 'C';
                    state++;
                    conditionA.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            System.out.println("结果: " + Arrays.toString(str));
        }).start();


    }



    public void testMergeSort(){
        int[] arr = {5, 2, 8, 3, 1, 6, 4, 7};
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
        String a="";

    }

    

    // 生产者消费者队列的大小
    private static final int QUEUE_SIZE = 10;
    private static final Object[] queue = new Object[QUEUE_SIZE];
    private static int count = 0;
    private static int putIndex = 0;
    private static int takeIndex = 0;
    


}
// /**
//  * 获取个人认证授权页面链接
//  * @return 认证授权页面链接信息
//  */
// public Map<String, Object> getPersonAuthUrl() {
//     Map<String, Object> result = new HashMap<>();
    
//     try {
//         // 构建请求参数
//         Map<String, Object> params = new HashMap<>();
        
//         // 个人认证配置
//         Map<String, Object> psnAuthConfig = new HashMap<>();
//         Map<String, Object> psnInfo = new HashMap<>();
//         psnInfo.put("psnName", "张三");
//         psnInfo.put("psnIDCardNum", "330xxxxxxxxxx1234"); 
//         psnInfo.put("psnIDCardType", "CRED_PSN_CH_IDCARD");
//         psnAuthConfig.put("psnInfo", psnInfo);
        
//         // 认证页面配置
//         Map<String, Object> psnAuthPageConfig = new HashMap<>();
//         psnAuthPageConfig.put("psnDefaultAuthMode", "PSN_FACE");
//         List<String> authModes = Arrays.asList("PSN_FACE", "PSN_MOBILE3", "PSN_BANKCARD4");
//         psnAuthPageConfig.put("psnAvailableAuthModes", authModes);
//         psnAuthConfig.put("psnAuthPageConfig", psnAuthPageConfig);
//         params.put("psnAuthConfig", psnAuthConfig);
        
//         // 授权配置
//         Map<String, Object> authorizeConfig = new HashMap<>();
//         List<String> scopes = Arrays.asList("get_psn_identity_info");
//         authorizeConfig.put("authorizedScopes", scopes);
//         params.put("authorizeConfig", authorizeConfig);
        
//         // 回调通知配置
//         params.put("notifyUrl", "https://www.xxx.com/notify");
//         params.put("clientType", "ALL");
        
//         // 重定向配置
//         Map<String, Object> redirectConfig = new HashMap<>();
//         redirectConfig.put("redirectUrl", "https://www.xxx.com/redirect");
//         redirectConfig.put("redirectDelayTime", "3");
//         params.put("redirectConfig", redirectConfig);

//         // TODO: 调用e签宝API获取认证链接
//         // String url = "https://{host}/v3/persons/auth-url";
//         // String response = HttpUtil.post(url, params);
        
//         // 模拟返回结果
//         result.put("code", 0);
//         result.put("message", "成功");
//         Map<String, String> data = new HashMap<>();
//         data.put("authFlowId", "OF-203xxxe080010");
//         data.put("authUrl", "https://openapi.esign.cn/auth/h5/index?authFlowId=OF-xxx&clientType=ALL&appId=xxx");
//         data.put("authShortUrl", "https://openapi.esign.cn/mFHRxxxhUV46");
//         result.put("data", data);
        
//     } catch (Exception e) {
//         result.put("code", -1);
//         result.put("message", "获取认证链接失败:" + e.getMessage());
//     }
    
//     return result;
// }
