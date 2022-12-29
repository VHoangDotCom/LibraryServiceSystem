<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Library System Mail</title>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top" bgcolor="#838383"
            style="background-color: #838383;"><br> <br>
            <table width="600" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center" valign="top" bgcolor="#d3be6c"
                        style="background-color: #d3be6c; font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; padding: 0px 15px 10px 15px;">

                        <div style="font-size: 48px; color:blue;">
                            <b>Hello ${Name}!</b>
                        </div>

                        <div style="font-size: 24px; color: #555100;">
                            <br> Bạn đã đặt thuê sách bên thư viện chúng tôi <b>thành công ! <br>
                        </div>

                        <div>
                            <br>Xin chào, ${Name}!  <br>
                            <br> Chúng tôi xin gửi lời cảm ơn chân thành khi bạn đã lựa chọn dịch vụ sản phẩm bên thư viện chúng tôi.<br>
                            <br>Tổng số tiền thuê sách của bạn là ${totalRent} và tổng số tiền cọc của bạn là ${totalDeposit}<br> <br>
                            <br>Hệ thống sẽ tự động trừ tiền cọc vào Ví ảo của bạn ( số dư: ${virtualWallet} ). Ví ảo của bạn sẽ được hoàn lại
                            số tiền cọc khi bạn hoàn trả sách đúng hạn!
                            <br> Đây là thông tin danh sách các đơn hàng của bạn đã được hệ thống lưu lại:
                            <a href="${orderDetail}"> Xem tại đây!</a>
                            <br>Thân ái! Xin chân thành cảm ơn - Quyết Chủ tịch
                            <br> <br> <b> <del>Trịnh</del> Nguyễn Văn Quyết</b><br>${location}<br>
                            <br>
                        </div>
                    </td>
                </tr>
            </table> <br> <br></td>
    </tr>
</table>
</body>
</html>