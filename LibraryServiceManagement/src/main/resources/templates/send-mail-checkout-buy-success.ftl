

<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<style>
    a:hover {text-decoration: underline !important;}
</style>

<body marginheight="0" topmargin="0" marginwidth="0" style="margin: 0px; background-color: #f2f3f8;" leftmargin="0">
<table cellspacing="0" border="0" cellpadding="0" width="100%" bgcolor="#f2f3f8"
       style="@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;">
    <tr>
        <td>
            <table style="background-color: #f2f3f8; max-width:670px; margin:0 auto;" width="100%" border="0"
                   align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td style="height:80px;">&nbsp;</td>
                </tr>
                <!-- Logo -->
                <tr>
                    <td style="text-align:center;">
                        <a href="https://rakeshmandal.com" title="logo" target="_blank">
                            <img width="60" src="https://i.ibb.co/hL4XZp2/android-chrome-192x192.png" title="logo" alt="logo">
                        </a>
                    </td>
                </tr>
                <tr>
                    <td style="height:20px;">&nbsp;</td>
                </tr>
                <!-- Email Content -->
                <tr>
                    <td>
                        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"
                               style="max-width:670px; background:#fff; border-radius:3px;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);padding:0 40px;">
                            <tr>
                                <td style="height:40px;">&nbsp;</td>
                            </tr>
                            <!-- Title -->
                            <tr>
                                <td style="padding:0 15px; text-align:center;">
                                    <h1 style="color:#1e1e2d; font-weight:400; margin:0;font-size:32px;font-family:'Rubik',sans-serif;">Xin chào ${Name}!</h1>
                                    <span style="display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece;
                                        width:100px;"></span>
                                    <h2 style="color:#1e1e2d; font-weight:400; margin:0;font-size:25px;font-family:'Rubik',sans-serif;">
                                        Bạn đã đặt mua sách bên thư viện chúng tôi <b>thành công !</b> <br>
                                    </h2>
                                    <br>
                                    <h3>Thông tin đơn hàng</h3>
                                </td>
                            </tr>
                            <!-- Details Table -->
                            <tr>
                                <td>
                                    <table cellpadding="0" cellspacing="0"
                                           style="width: 100%; border: 1px solid #ededed">
                                        <tbody>
                                        <tr>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; border-right: 1px solid #ededed; width: 35%; font-weight:500; color:rgba(0,0,0,.64)">
                                                Mã đơn hàng:</td>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; color: #455056;">
                                                ${SKU}</td>
                                        </tr>
                                        <tr>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; border-right: 1px solid #ededed; width: 35%; font-weight:500; color:rgba(0,0,0,.64)">
                                                Tên khách hàng:</td>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; color: #455056;">
                                                ${Name}</td>
                                        </tr>
                                        <tr>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; border-right: 1px solid #ededed; width: 35%; font-weight:500; color:rgba(0,0,0,.64)">
                                                Tổng tiền thanh toán:</td>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; color: #455056;">
                                                ${totalDeposit}</td>
                                        </tr>
                                        <tr>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; border-right: 1px solid #ededed; width: 35%; font-weight:500; color:rgba(0,0,0,.64)">
                                                Phương thức thanh toán:</td>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; color: #455056;">
                                                ${orderType}</td>
                                        </tr>
                                        <tr>
                                            <td
                                                    style="padding: 10px;  border-bottom: 1px solid #ededed; border-right: 1px solid #ededed; width: 35%;font-weight:500; color:rgba(0,0,0,.64)">
                                                Số dư ví ảo:</td>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; color: #455056;">
                                                ${virtualWallet} ( Nếu bạn chọn thanh toán bằng Ví Ảo, hệ thống sẽ tự động trừ số tiền thanh toán
                                                vào Ví Ảo của bạn ).</td>
                                        </tr>
                                        <tr>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; border-right: 1px solid #ededed; width: 35%;font-weight:500; color:rgba(0,0,0,.64)">
                                                Ngày đặt:</td>
                                            <td
                                                    style="padding: 10px; border-bottom: 1px solid #ededed; color: #455056; ">
                                                ${orderDate}</td>
                                        </tr>
                                        <tr>
                                            <td
                                                    style="padding: 10px; border-right: 1px solid #ededed; width: 35%;font-weight:500; color:rgba(0,0,0,.64)">
                                                Lời nhắc:</td>
                                            <td style="padding: 10px; color: #455056;">
                                                Bạn có thể xem chi tiết danh sách các đơn hàng của mình:   <a href="${orderDetail}"> Xem tại đây!</a>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td style="height:40px;">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="height:20px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="text-align:center;">
                        <p style="font-size:14px; color:#455056bd; line-height:18px; margin:0 0 0;">&copy; <strong>
                                <b>Chủ tịch <del>Trịnh</del> Nguyễn Văn Quyết</b>
                                <br>${location}<br>
                            </strong></p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

</body>
</html>
