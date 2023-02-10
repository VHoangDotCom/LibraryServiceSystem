package com.library.repository;

import com.library.entity.Book;
import com.library.entity.dto.BookTopSellerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(
            value = "SELECT * FROM book s where s.category_id = :cateId",
            nativeQuery = true
    )
    List<Book> getAllBookByCategoryID(Long cateId);

    @Query(
            value = "SELECT * from book b " +
                    "where b.author like %:keyword% " +
                    " or b.detail like %:keyword% " +
                    " or b.publisher like %:keyword% " +
                    " or b.subject like %:keyword% " +
                    " or b.title like %:keyword% " +
                    " or b.amount like %:keyword% " +
                    " or b.borrow_price like %:keyword% ",
            nativeQuery = true
    )
    List<Book> getAllBooksByKeyword(String keyword);

    @Query(
            value = "SELECT p.id, p.title, p.detail, p.author, p.borrow_price, p.price, p.language, p.publisher, " +
                    " p.subject, p.thumbnail , sum(c.quantity) " +
                    " from book p inner join order_item c on p.id = c.book_id " +
                    " inner join orders o on c.order_id = o.order_id " +
                    " where o.status = 'AVAILABLE' or o.status = 'COMPLETED' " +
                    " group by p.title " +
                    " order by sum(c.quantity) desc " +
                    " limit 0, :topNumber",
            nativeQuery = true
    )
    List<Tuple> getTop_Number_Book_Best_Seller(int topNumber);

    @Query(
            value = "select p.* from book p " +
                    " inner join order_item c on p.id = c.book_id " +
                    " inner join orders o on c.order_id = o.order_id " +
                    " where o.order_id = :orderId " +
                    " group by p.id " +
                    " order by p.id ",
            nativeQuery = true
    )
    List<Book> getListBook_InOrder(String orderId);

}
