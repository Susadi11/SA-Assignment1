package om.petfood.order.publisher;

import com.petfood.order.util.OrderRecord;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final List<OrderRecord> orders = new ArrayList<>();

    @Override
    public List<OrderRecord> getOrders() {
        return orders;
    }

    @Override
    public void addOrder(OrderRecord order) {
        orders.add(order);
    }
}