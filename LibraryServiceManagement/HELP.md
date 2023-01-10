
# API route

## Authentication
1. Login
- http://localhost:8080/api/login
- Post
- Body : x - www-form-urlencoded
 Tại cot Key : thêm 2 trường username và password
 Tại cột VALUE : thêm 2 giá trị hoang và hoang (seeding)
 Submit lấy access token và refresh token
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280298/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_login_txqsvs.png)

2. Refresh token
- http://localhost:8080/api/token/refresh
- Get
- Header : Thêm trường Authorization
 Value : Bearer access_token_khi_login
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280298/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_refresh_token_y19gcn.png)

3. Change Password
- http://localhost:8080/api/user/changePassword
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280297/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_change_password_zpc53j.png)
- Body (raw -> json):
  {
  "email": "viethoang2001gun@gmail.com",
  "oldPassword": "hoang",
  "newPassword": "54321"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280298/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_change_password1_bpvagc.png)

4. Add Role to User 
- http://localhost:8080/api/role/addtouser
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280297/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_add_role_to_user_dlsttf.png)

- Body (raw -> json):
  {
  "email": "will@gmaol.com",
  "roleName": "ROLE_MANAGER"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280297/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_add_role_to_user1_xy1gtu.png)

- Trong trường hợp nhập sai tên Role hoặc sai email người dùng => hệ thống báo lỗi
- Trong trường hợp User đã có Role đó mà add lại lần nx => hệ thống báo lỗi

5. Reser password
- Step 1: http://localhost:8080/api/user/resetPassword
- Post
- Body : (confirm your email to reset pass)
  {
  "email" : "hoangnvth2010033@fpt.edu.vn"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280297/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/send_mail_reset_pass_g15iaf.png)

- Step 2: Vào mail vừa gửi xác nhận đường link => paste đường link vào postman
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280297/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/send_mail_reset_pass1_ofvl8x.png)
- http://localhost:8080/api/user/savePassword?token=b47d7c56-c7a1-4646-a4f9-a4ac72417f5a
- Post
- Body:
  {
  "newPassword" : "12345"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280296/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/send_mail_reset_pass2_okamqs.png)


## User
1. Create User (Register)
- http://localhost:8080/api/user/save
- Post
- Body: (có thể update tùy vao trường dữ liệu)
  {
  "name": "Dat Villa",
  "username": "vinacon",
  "password": "1234",
  "email":"vinamilk@gmail.com",
  "avatar":"2.jpg",
  "address":"Day xa hoi",
  "status": 0
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280296/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_register_fwmp09.png)

2. Get User List (chỉ Admin hoặc Librarian mới đc quyền này)
- http://localhost:8080/api/users
- Get
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280296/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_get_user_list_lqejey.png)

3. Get User detail by ID
- http://localhost:8080/api/user/3
- Get
- Hiện tại chưa phân quyền
![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671512367/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_user_by_ID_j6vtqm.png)

4. Get User detail by Username
- http://localhost:8080/api/user/username/jim
- Get
- Hiện tại chưa phân quyền
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671512367/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_user_by_username_ueo2yz.png)

5. Get User detail by Email
- http://localhost:8080/api/user/email/viethoang2001gun@gmail.com
- Get
- Hiện tại chưa phân quyền
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671512367/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_user_by_email_enxvom.png)

6. Export User data to Excel
- http://localhost:8080/api/users/export-to-excel
- Get
- Copy đường link lên browser
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280296/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_export_user_list_to_excel_xsp8wf.png)

7. Get User profile
- http://localhost:8080/api/user/profile
- Get
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token là của User đăng nhập )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671548012/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_User_Profile_nfpew7.png)

8. Update User Detail by ID ( Admin role )
- http://localhost:8080/api/user/update?userId=1
- Put
- Body:
  {
  "name": "Dat Villa",
  "username": "vinacon",
  "avatar": "1.jpg",
  "email":"vina@gmail.com",
  "address":"Day xa hoi",
  "status": "CANCELED",
  "virtualWallet": 70000
  }
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672591722/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Update_Profile_by_Admin_djndbr.png)

9. Update User Profile by access_token ( Logged In account role )
- http://localhost:8080/api/user/update-profile
- Put
- Body:
  {
  "name": "Dat Villa",
  "username": "vinacon",
  "avatar": "1.jpg",
  "email":"vina@gmail.com",
  "address":"Day xa hoi",
  "status": "CANCELED",
  "virtualWallet": 70000
  }
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token là của User đăng nhập )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672591722/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Update_Profile_by_Logged_In_falgqk.png)




## Category
1. Create new Category
- http://localhost:8080/api/categories/add
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671522553/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Create_Category1_ohxjcv.png)

- Body:
  {
  "name": "Math 12"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671522553/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Create_Category_uglehc.png)

2. Get List Categories
- http://localhost:8080/api/categories
- Get
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671522554/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_List_category_ilnrar.png)

3. Get Category By ID
- http://localhost:8080/api/category/1
- Get
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671522553/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_Category_by_ID_elpzjc.png)

4. Update Category By ID
- http://localhost:8080/api/categories/save/1
- Put
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671522553/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Update_Category_by_ID1_kkrvqs.png)

- Body:
  {
  "name": "Math 12"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671522553/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Update_Category_by_ID_mkzujr.png)

5. Delete Category By ID
- http://localhost:8080/api/categories/delete/1
- Delete
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671522553/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Delete_Category_by_ID_vcp1g0.png)


## Book (Thao tác như Category )
1. Get List Books
- http://localhost:8080/api/books
- Get

2. Get Book by ID
- http://localhost:8080/api/book/1
- Get

3. Create new Book by CategoryID
- http://localhost:8080/api/books/add?categoryId=1
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text]()


- Body:
  {
  "title": "How to rob a bank",
  "subject": "Science",
  "publisher": "Kim Dong",
  "language": "english",
  "thumbnail": "1.jpg",
  "detail": "This is a nice book",
  "author": "Hoang anh",
  "price": 300,
  "amount": 30,
  "borrowPrice": 50,
  "publishedAt": "2022-12-24T04:34:24.783+00:00"
  }
  ![alt text]()

4. Update Book by ID and CategoryID
- http://localhost:8080/api/books/save/1?categoryId=2
- Put
- Header : Thêm trường Authorization
    Value : Bearer access_token_khi_login  ( access token phải là của Admin )
    ![alt text]()

- Body:
  {
  "title": "How to rob a bank",
  "subject": "Science",
  "publisher": "Kim Dong",
  "language": "english",
  "thumbnail": "1.jpg",
  "detail": "This is a nice book",
  "author": "Hoang anh",
  "price": 300,
  "amount": 30,
  "borrowPrice": 50,
  "publishedAt": "2022-12-24T04:34:24.783+00:00"
  }
  ![alt text]()

5. Delete Book by ID
- http://localhost:8080/api/books/delete/1
- Delete
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text]()

6. Get List Book by CategoryID
- http://localhost:8080/api/books/category/1
- Get


## Order (Thao tác như Category )
1. Get List Orders - Role Admin
- http://localhost:8080/api/orders
- Get
- Header : Thêm trường Authorization
    Value : Bearer access_token_khi_login  ( access token phải là của Admin )
    ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218667/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_list_order_admin_vvtcxj.png)

2. Get Order by ID - Role Admin
- http://localhost:8080/api/order/tqrs9id7Y0
- Get
- Header : Thêm trường Authorization
    Value : Bearer access_token_khi_login  ( access token phải là của Admin )
    ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218667/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_Order_by_ID_lnzlbg.png)


3. Create new Order by UserID
- http://localhost:8080/api/orders/add?userId=4
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Member tro len; userID phai lay cua member khi da dang nhap vao )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218667/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Create_order_by_UserID1_p6ve6j.png)

- Body:
  {
  "fullName": "Viet Hoang",
  "email": "viethoang2001gun@gmail.com",
  "phongNumber": "053434343",
  "address": "23 Hanoi",
  "totalDeposit": 340000,
  "totalRent": 34000
  }
  ![alt text]()

4. Update Order by ID (Quyền Admin Update - chỉ nên update trường status hay các trường ko ảnh hưởng thông tin Khách hàng)
- http://localhost:8080/api/orders/save?orderID=tqrs9id7Y0
- Put
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text]()

- Body: ( chú ý update phương thức thanh toán theo lựa chọn Khách hàng )
  {
  "orderId": "eqVzjzIO1k",
  "fullName": "Viet Hoang-sama",
  "email": "viethoang2001gun@gmail.com",
  "phoneNumber": "05343434",
  "address": "23 Hanoi",
  "status": "PROCESSING",
  "type":"PAYPAL",
  "totalDeposit": 340000,
  "totalRent": 34000
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218668/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Update_Order_by_ID_hqmccr.png)

5. Delete Order by ID
- http://localhost:8080/api/orders/delete/QIJfM2wyBA
- Delete
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218668/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Delete_Order_by_ID_khjvap.png)

6. Get List Orders by UserID
- http://localhost:8080/api/orders/user?userId=2
- Get
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text]()

7. Get List Orders when Logged In (Danh sách Order của tài khoản đăng nhập vào - ko cần truyền ID vào header, chỉ lấy access token)
- http://localhost:8080/api/orders/user-account
- Get
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Tài khoản đăng nhập vào )
  ![alt text]()

8. Checkout success (Tài khoản khi submit Checkout thành công)
- http://localhost:8080/api/orders/checkout-success?orderID=vOWELCFpiw
- Get
- Header : Thêm trường Authorization
   Value : Bearer access_token_khi_login  ( access token phải là của Tài khoản đăng nhập vào )
   ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218674/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Checkout_Success_ajtxmv.png)

- Step 2: Access to email to receive confirmation
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218674/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Checkout_Success1_jqlt5v.png)

9. Export Orders data to Excel
- http://localhost:8080/api/orders/export-to-excel
- Get

10. Export Account's Order data to Excel
- http://localhost:8080/api/orders/export-to-excel-single?userId=3
- Get
- Chay tren trinh duyet

(*Note: Order trong luồng mua CRUD và xử lý tương tự Order luồng thuê. Chỉ khác trường totalBorrow - Order luồng mua ko dùng đến.)

11. Checkout when Buying success (Tài khoản khi submit Checkout mua hàng thành công)
- http://localhost:8080/api/orders/checkout-buying-success?orderID=64me98LAil
- Get
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Tài khoản đăng nhập vào )
  ![alt text]()

- Step 2: Access to email to receive confirmation
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672218674/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Checkout_Success1_jqlt5v.png)


## OrderItem (Thao tác như Order )
1. Get List OrderItem - Role Admin
- http://localhost:8080/api/order_items
- Get
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672125709/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_All_OrderItem_Admin_a7je9r.png)


2. Create new Order Item by OrderID + BookID
- Sau khi add Order Item thành công, hệ thống sẽ tự động trường Amount của Book theo Quantity của OrderItem
- Moi OrderItem khi thue chi toi da 10 quyen (fix cứng)
- Khi Borrow quá số sách tồn kho => Thông báo ko đủ sách
- http://localhost:8080/api/order_items/add?orderId=B4dKTpfwjr&bookId=1
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Member tro len; userID phai lay cua member khi da dang nhap vao )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672125709/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Create_OrderItem_by_OrderID_BookID_jbrglp.png)


- Body:
  {
  "quantity": 23,
  "borrowedAt":"2022-08-01",
  "returnedAt" : "2022-12-01"
  }
  ![alt text]()

3. Update Order Item by ID (Quyền Admin Update - chỉ nên update trường status hay các trường ko ảnh hưởng thông tin Khách hàng)
- Đọc thêm phần comment hàm OrderItemController/updateOrderItem() để hiểu rõ luồng nghiệp vụ
- http://localhost:8080/api/order_items/save?order_itemID=1
- Put
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672125709/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Update_OrderItem_By_ID_hr7yen.png)

- Body:
  {
  "quantity": 55,
  "borrowedAt":"2022-08-01",
  "returnedAt" : "2022-12-01"
  }
  ![alt text]()

4. Delete Order Item by ID ( Member Logged In role )
- http://localhost:8080/api/order_items/delete/1
- Delete
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672125709/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Delete_OrderItem_by_ID_tqpxqy.png)

5. Get List Order Items by OrderID ( role la Member dang nhap vao )
- http://localhost:8080/api/order_items/order?orderID=B4dKTpfwjr
- Get
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Member tro len )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1672125709/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_All_OrderItem_by_Account_s_Order_th2cfd.png)

6. Create new Order Item when Buying ( theo OrderId và BookID ) - tương tự Create OrderItem (khác: bỏ trương BorrowAt + ReturnAt)
- Lưu ý: Cách thực thi ko khác Tạo mới OrderItem khi thuê, tuy nhiên hàm thực thi phía server sẽ khác, nên lưu ý trường hợp khi dùng API này
- http://localhost:8080/api/order_items/add-buy?orderId=64me98LAil&bookId=1
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Member tro len; userID phai lay cua member khi da dang nhap vao )
  ![alt text]()

- Body:
  {
  "quantity": 3
  }
  ![alt text]()

7. Update Order Item by ID - when Buying (Quyền Admin Update - chỉ nên update trường status hay các trường ko ảnh hưởng thông tin Khách hàng)
- Lưu ý: Cách thực thi ko khác Cập nhật OrderItem khi thuê, tuy nhiên hàm thực thi phía server sẽ khác, nên lưu ý trường hợp khi dùng API này
- Đọc thêm phần comment hàm OrderItemController/updateOrderItem() để hiểu rõ luồng nghiệp vụ
- http://localhost:8080/api/order_items/save-buy?order_itemID=1
- Put
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text]()

- Body:
  {
  "quantity": 55
  }
  ![alt text]()


8. Delete Order Item by ID - when Buying ( Member Logged In role )
- Lưu ý: Cách thực thi ko khác Xóa OrderItem khi thuê, tuy nhiên hàm thực thi phía server sẽ khác, nên lưu ý trường hợp khi dùng API này
- http://localhost:8080/api/order_items/delete-buy/1
- Delete
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text]()



## Notification (Thao tác như Book )
1. Get List Notifications
- http://localhost:8080/api/notifications
- Get
- ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1673333531/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_All_Notifications_iocqrg.png)

2. Get Notification by ID
- http://localhost:8080/api/notification/1
- Get
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1673333531/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_Notification_by_ID_lijybf.png)

3. Create new Notification by UserID
- http://localhost:8080/api/notifications/add?userId=1
- Post
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )

- Body:
  {
  "content": " Qua han tra sach hehe"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1673333531/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Add_Notification_by_UserID_chzosx.png)

4. Update Notification by ID 
- http://localhost:8080/api/notifications/save/1
- Put
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của Admin )

- Body:
  {
  "content": " Qua han tra sach hehe"
  }
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1673333531/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Update_Notification_by_ID_b7mcap.png)

5. Delete Notification by ID
- http://localhost:8080/api/notifications/delete/2
- Delete
- Header : Thêm trường Authorization
  Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1673333531/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Delete_Notification_by_ID_rrezua.png)

6. Get List Notification by UserID
- http://localhost:8080/api/notifications/user/1
- Get
- Header : Thêm trường Authorization
    Value : Bearer access_token_khi_login  ( access token phải là của MEMBER tro len )
    ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1673333531/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_Get_Notifications_List_by_UserID_dhc9vy.png)
