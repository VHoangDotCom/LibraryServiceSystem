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
            value = "SELECT s.* " +
                    " FROM orders s " +
                    " where s.user_id = :userId " +
                    " and s.order_id = :orderId",
            nativeQuery = true
    )
    Order getOrderDetailByUserID(Long userId, String orderId);

    @Query(
            value = "SELECT p.id, p.name, p.user_name, p.phone_number, p.avatar, p.address, MONTH(c.created_at), " +
                    " count(c.order_id) " +
                    " from user p " +
                    " inner join orders c on p.id = c.user_id " +
                    " where p.id = :userId " +
                    " and (c.status = 'AVAILABLE' or c.status = 'COMPLETED' )" +
                    " and YEAR(c.created_at) = :year " +
                    " group by MONTH(c.created_at) " +
                    " order by count(c.order_id) desc ",
            nativeQuery = true
    )
    List<Tuple> get_Top_Order_Of_User_By_Month(long userId, int year);

    @Query(
            value = " select MONTH(c.created_at), sum(c.total_deposit), sum(c.total_rent), sum(c.total_deposit) + sum(c.total_rent) " +
                    " from user p inner join orders c on p.id = c.user_id " +
                    " where p.id = :userId " +
                    " and (c.status = 'AVAILABLE' or c.status = 'COMPLETED' ) " +
                    " and YEAR(c.created_at) = :year " +
                    " group by MONTH(c.created_at) " +
                    " order by MONTH(c.created_at) ",
            nativeQuery = true
    )
    List<Tuple> get_Report_Order_Of_User_In_Year(long userId, int year);

    @Query(
            value=" select DISTINCT MONTH(c.created_at), sum(c.total_deposit) + sum(c.total_rent) " +
                    " from orders c " +
                    " where YEAR(c.created_at) = :year " +
                    " and (c.status = 'AVAILABLE' or c.status = 'COMPLETED' ) " +
                    " group by MONTH(c.created_at) " +
                    " order by MONTH(c.created_at) ",
            nativeQuery = true
    )
    List<Tuple> get_Total_Order_By_Month( int year);

}
