package thread.mergequeuedemo;

// 用户下单的请求
public class Request {
    private Long orderId;
    private Long skuId;
    private Integer num;

    public Request(Long orderId, Long skuId, Integer num) {
        this.orderId = orderId;
        this.skuId = skuId;
        this.num = num;
    }

    @Override
    public String toString() {
        return "Request{" +
                "orderId=" + orderId +
                ", skuId=" + skuId +
                ", num=" + num +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
