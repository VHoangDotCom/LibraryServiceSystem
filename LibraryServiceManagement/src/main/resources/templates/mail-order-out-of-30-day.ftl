<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>CodePen - Notifications Email Template</title>


</head>
<body>
<!-- partial:index.partial.html -->
<!doctype html>
<html lang="en-US">

<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <title>Notifications Email Template</title>
    <meta name="description" content="Notifications Email Template">
    <style type="text/css">
        a:hover {text-decoration: none !important;}
        :focus {outline: none;border: 0;}
    </style>
</head>

<body marginheight="0" topmargin="0" marginwidth="0" style="margin: 0px; background-color: #f2f3f8;" bgcolor="#eaeeef"
      leftmargin="0">
<!--100% body table-->
<table cellspacing="0" border="0" cellpadding="0" width="100%" bgcolor="#f2f3f8"
       style="@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;">
    <tr>
        <td>
            <table style="background-color: #f2f3f8; max-width:670px; margin:0 auto;" width="100%" border="0" align="center"
                   cellpadding="0" cellspacing="0">
                <tr>
                    <td style="height:80px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="text-align:center;">
                        <a href="https://rakeshmandal.com" title="logo" target="_blank">
                            <img width="60" src="https://i.ibb.co/hL4XZp2/android-chrome-192x192.png" title="logo" alt="logo">
                        </a>
                    </td>
                </tr>
                <tr>
                    <td height="20px;">&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"
                               style="max-width:600px; background:#fff; border-radius:3px; text-align:left;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);">
                            <tr>
                                <td style="padding:40px;">
                                    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td>
                                                <h1 style="color: #1e1e2d; font-weight: 500; margin: 0; font-size: 32px;font-family:'Rubik',sans-serif;">Xin chào ${username}!</h1>
                                                <p style="font-size:15px; color:#455056; line-height:24px; margin:8px 0 30px;">Có vẻ như cuốn sách bạn đã quá hạn mức cho phép (30 ngày)!</p>
                                                <br />
                                                <p style="font-size:15px; color:#455056; line-height:24px; margin:8px 0 30px;">Chúng tôi thông báo số tiền cọc của bạn sẽ bị mất và bạn sẽ bị liệt vào
                                                danh sách đen (Black List).
                                                </p>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                                                    <tr
                                                            style="border-bottom:1px solid #ebebeb; margin-bottom:26px; padding-bottom:29px; display:block;">
                                                        <td width="84">
                                                            <a style="height: 64px; width: 64px; text-align:center; display:block;">
                                                                <img src="${bookImage}" alt="Profile Picture" style="border-radius:50%;">
                                                            </a>
                                                        </td>
                                                        <td style="vertical-align:top;">
                                                            <h3 style="color: #4d4d4d; font-size: 20px; font-weight: 400; line-height: 30px; margin-bottom: 3px; margin-top:0;">
                                                                <strong>${bookName}</strong> mượn từ ngày ${borrowDate}.</h3>
                                                            <span style="color:#737373; font-size:20px;">Số lượng: ${amount}</span>
                                                        </td>
                                                    </tr>

                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="height:25px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="text-align:center;">
                        <p style="font-size:14px; color:#455056bd; line-height:18px; margin:0 0 0;">&copy; <strong>
                                <b>Chủ tịch <del>Trịnh</del> Nguyễn Văn Quyết</b>
                                <br>2023, Hanoi<br>
                            </strong></p>
                    </td>
                </tr>
                <tr>
                    <td style="height:80px;">&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!--/100% body table-->
</body>

</html>
<!-- partial -->

</body>
</html>
