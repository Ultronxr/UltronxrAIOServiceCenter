import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.cookie.ThreadLocalCookieStore;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ultronxr
 * @date 2023/04/14 15:40:17
 * @description Hutool HttpRequest Cookie 测试
 */
@Slf4j
public class HutoolHttpRequestTest {

    public static void main(String[] args) {
        // 单线程内的 cookie 会持续累积，除非调用移除/清空 cookie 的方法（两种清空 cookie 的方法作用相同），或线程销毁
        singleThreadTest(true);
        // 多线程的 cookie 各自累积，线程间不会相互影响；调用移除/清空 cookie 的方法也不会线程间相互影响。
        //multiThreadTest();
    }

    public static void singleThreadTest(boolean isRemoveCookie) {
        HttpResponse response = HttpUtil.createGet("https://www.baidu.com").execute();
        printCookie(response);
        printCookie();
        if(isRemoveCookie) {
            removeThreadLocalCookieStore();
            //removeInMemoryCookieStore();
            System.out.println("after cookie removed ...");
            printCookie(response);
            printCookie();
        }
        System.out.println("==============\n==============");

        HttpResponse response1 = HttpUtil.createGet("https://www.bilibili.com").execute();
        printCookie(response1);
        printCookie();
        if(isRemoveCookie) {
            removeThreadLocalCookieStore();
            //removeInMemoryCookieStore();
            System.out.println("after cookie removed ...");
            printCookie(response1);
            printCookie();
        }
    }

    public static void multiThreadTest() {
        Thread thread1 = new Thread(() -> {
            HttpResponse response = HttpUtil.createGet("https://www.baidu.com").execute();
            printCookie(response);
            printCookie();
        }, "thread-1");

        Thread thread2 = new Thread(() -> {
            HttpResponse response = HttpUtil.createGet("https://www.bilibili.com").execute();
            printCookie(response);
            printCookie();
            removeInMemoryCookieStore();
            printCookie(response);
            printCookie();
        }, "thread-2");

        thread1.start();
        thread2.start();
    }

    public static void printCookie(HttpResponse response) {
        log.info("{}", response.getCookies());
    }

    public static void printCookie() {
        log.info("{}", HttpRequest.getCookieManager().getCookieStore().getCookies());
    }

    /**
     * 下面两种删除 cookie 的方法功能是相同的
     *
     * 第一种是直接删除 ThreadLocal 的内容， ThreadLocal<InMemoryCookieStore>::remove() ，再次获取时重新 new 一个空 CookieStore
     * 第二种是获取到 ThreadLocal 中存储的 InMemoryCookieStore ，再调用其 removeAll() 方法。
     */
    public static void removeThreadLocalCookieStore() {
        ThreadLocalCookieStore cookieStore = (ThreadLocalCookieStore) HttpRequest.getCookieManager().getCookieStore();
        cookieStore.removeCurrent();
    }

    public static void removeInMemoryCookieStore() {
        HttpRequest.getCookieManager().getCookieStore().removeAll();
        // 等价于下面这条语句
        // ((ThreadLocalCookieStore) HttpRequest.getCookieManager().getCookieStore()).getCookieStore().removeAll();
    }

}
