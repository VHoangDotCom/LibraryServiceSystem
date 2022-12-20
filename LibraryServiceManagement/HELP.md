
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

3. Export User data to Excel
- http://localhost:8080/api/users/export-to-excel
- Get
- Copy đường link lên browser
  ![alt text](https://res.cloudinary.com/fpt-aptech-h-n-i/image/upload/v1671280296/FPT%20-%20Sem4/API%20Final%20Project%20-%20API/api_-_export_user_list_to_excel_xsp8wf.png)