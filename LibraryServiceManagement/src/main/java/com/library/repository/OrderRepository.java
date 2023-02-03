package com.library.repository;

import com.library.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(
            value = "SELECT * FROM orders s where s.user_id = :userId",
            nativeQuery = true
    )
    List<Order> getAllOrderByUserID(Long userId);

    @Query(
            value = "SELECT p.id, p.name, p.user_name, p.phone_number, p.avatar, p.address, MONTH(c.created_at), " +
                    " count(c.order_id) " +
                    " from user p " +
                    " inner join orders c on p.id = c.user_id " +
                    " where p.id = :userId " +
                    " and c.status = 'AVAILABLE' " +
                    " and YEAR(c.created_at) = :year " +
                    " group by MONTH(c.created_at) " +
                    " order by count(c.order_id) desc ",
            nativeQuery = true
    )
    List<Tuple> get_Top_Order_Of_User_By_Month(long userId, int year);
}
