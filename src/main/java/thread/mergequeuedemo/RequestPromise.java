package thread.mergequeuedemo;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class RequestPromise {
    private Request request;
    private Response response;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public RequestPromise(Request request) {
        this.request = request;
    }

    public RequestPromise(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @SneakyThrows
    public void await() {
        countDownLatch.await();
    }

    public void signal() {
        countDownLatch.countDown();
    }
}
