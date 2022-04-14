package orders.services.mappers;

import orders.controllers.models.OrderDto;
import orders.repositories.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDto map(Order data) {
        return OrderDto.builder()
                .id(data.getId())
                .userId(data.getUserId())
                .userName(data.getUserName())
                .userCreditCardType(data.getUserCreditCardType())
                .productName(data.getProductName())
                .productColor(data.getProductColor())
                .productPrice(data.getProductPrice())
                .productQuantity(data.getProductQuantity())
                .companyId(data.getCompanyId())
                .companyName(data.getCompanyName())
                .build();
    }
}
